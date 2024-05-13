package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidProcessor implements IEntityProcessingService {

    private IAsteroidSplitter asteroidSplitter = new AsteroidSplitterImpl();

    private int moveSpeed = 100;

    // TODO: Spawn asteroids as the game keeps running.
//    private double spawnRate = 2.5;
//    private double spawnTimer = 0.0;

    @Override
    public void process(GameData gameData, World world) {

//        spawnTimer += gameData.getTpf();
//
//        if (spawnTimer >= spawnRate) {
//            // This takes excess milliseconds into account.
//            spawnTimer -= spawnRate;
//
//            world.addEntity(new Asteroid or some shit)
//        }

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            // Remove if dead.
            if (asteroid.getHealth() <= 0) {
                world.removeEntity(asteroid);
            }

            double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));

            asteroid.setX(asteroid.getX() + changeX * moveSpeed * gameData.getDeltaTime());
            asteroid.setY(asteroid.getY() + changeY * moveSpeed * gameData.getDeltaTime());

            if (asteroid.getX() < 0) {
                asteroid.setX(asteroid.getX() - gameData.getDisplayWidth());
            }

            if (asteroid.getX() > gameData.getDisplayWidth()) {
                asteroid.setX(asteroid.getX() % gameData.getDisplayWidth());
            }

            if (asteroid.getY() < 0) {
                asteroid.setY(asteroid.getY() - gameData.getDisplayHeight());
            }

            if (asteroid.getY() > gameData.getDisplayHeight()) {
                asteroid.setY(asteroid.getY() % gameData.getDisplayHeight());
            }

        }

    }

    /**
     * Dependency Injection using OSGi Declarative Services
     */
    public void setAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = asteroidSplitter;
    }

    public void removeAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = null;
    }


}
