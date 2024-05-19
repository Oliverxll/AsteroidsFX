package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.player.Player;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CollisionDetector implements IPostEntityProcessingService {

    HttpClient client = HttpClient.newHttpClient();

    @Override
    public void process(GameData gameData, World world) {

        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // if the two entities are identical, skip the iteration
                if (entity1.getID().equals(entity2.getID())) {
                    continue;                    
                }

                // CollisionDetection
                if (collides(entity1, entity2)) {

                    // As per Lab requirement, remove player if asteroid collides with player.
                    // Asteroid collisions.
                    if (entity1 instanceof Asteroid) {
                        if (entity2 instanceof Player) {
                            entity2.setHealth(0); // Player removed in PlayerControlSystem.
                            return; // Exit loop, player is dead and game should end.
                        }
                    }

                    // Enemy collisions.
                    if (entity1 instanceof Enemy) {
                        if (entity2 instanceof Player) {
                            entity2.setHealth(0); // Player removed in PlayerControlSystem.
                            return; // Exit loop, player is dead and game should end.
                        }
                    }

                    // Bullet collisions.
                    if (entity1 instanceof Bullet) {
                        if (((Bullet) entity1).getShooter() instanceof Player) {
                            if (entity2 instanceof Asteroid) {
                                entity2.setHealth(entity2.getHealth() - 1); // Asteroid handled in AsteroidControlSystem.
                                ((Asteroid) entity2).setHit(true);

                                incrementScore(1);
                            }

                            if (entity2 instanceof Enemy) {
                                entity2.setHealth(entity2.getHealth() - 1); // Enemy handled in EnemyControlSystem.
                            }
                        }

                        if (((Bullet) entity1).getShooter() instanceof Enemy) {
                            if (entity2 instanceof Player) {
                                entity2.setHealth(entity2.getHealth() - 1); // Player handled in PlayerControlSystem.
                            }
                        }

                        world.removeEntity(entity1);
                    }
                }
            }
        }

    }

    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    private void incrementScore(int i) {
        HttpRequest requestAddToScore = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/score/add/" + i))
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build();

        try {
            client.send(requestAddToScore, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
