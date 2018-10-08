package nl.han.ica.yaeger.engine.collisions;

import javafx.scene.Node;
import nl.han.ica.yaeger.engine.entities.entity.Entity;
import nl.han.ica.yaeger.engine.entities.entity.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Set;

import static org.mockito.Mockito.mock;

class CollisionDelegateTest {

    private CollisionDelegate collisionDelegate;

    @BeforeEach
    void setup() {
        collisionDelegate = new CollisionDelegate();
    }

    @Test
    void onlyCollidedGetsCollisionCheck() {
        // Setup
        Collided collided = mock(Collided.class);
        Collider collider = mock(Collider.class);

        collisionDelegate.register(collided);
        collisionDelegate.register(collider);

        ArgumentCaptor<Set> argument = ArgumentCaptor.forClass(Set.class);

        // Test
        collisionDelegate.checkCollisions();

        // Verify
        Mockito.verify(collided).checkForCollisions(argument.capture());
        Mockito.verify(collided).checkForCollisions(argument.capture());
        Assertions.assertEquals(1, argument.getValue().size());
    }

    @Test
    void collidableGetsCheckedIncludingItself() {
        // Setup
        Collidable collidable = mock(Collidable.class);
        Collider collider = mock(Collider.class);
        collisionDelegate.register(collidable);
        collisionDelegate.register(collider);

        ArgumentCaptor<Set> argument = ArgumentCaptor.forClass(Set.class);

        // Test
        collisionDelegate.checkCollisions();

        // Verify
        Mockito.verify(collidable).checkForCollisions(argument.capture());
        Assertions.assertEquals(2, argument.getValue().size());
    }

    @Test
    void entitiesGetCorrectlyAdded() {
        // Setup
        Entity collidedEntity = mock(CollidedTestEntity.class);
        Entity colliderEntity = mock(ColliderTestEntity.class);

        collisionDelegate.register(collidedEntity);
        collisionDelegate.register(colliderEntity);

        ArgumentCaptor<Set> argument = ArgumentCaptor.forClass(Set.class);

        // Test
        collisionDelegate.checkCollisions();

        // Verify
        Mockito.verify((Collided) collidedEntity).checkForCollisions(argument.capture());
        Assertions.assertEquals(1, argument.getValue().size());
    }

    private class CollidedTestEntity implements Entity, Collided {

        @Override
        public void onCollision(Collider collidingObject, CollisionSide collisionSide) {

        }

        @Override
        public void remove() {

        }

        @Override
        public Node getGameNode() {
            return null;
        }

        @Override
        public Position getPosition() {
            return null;
        }
    }

    private class ColliderTestEntity implements Entity, Collider {

        @Override
        public void remove() {

        }

        @Override
        public Node getGameNode() {
            return null;
        }

        @Override
        public Position getPosition() {
            return null;
        }
    }
}


