package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {

    private int turnSpeed = 100;
    private int moveSpeed = 150;

    private Random rand = new Random();


    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Enemy.class)) {
            Enemy enemy = (Enemy) entity;

            if (enemy.getHealth() <= 0) {
                world.removeEntity(enemy);
            }

            enemy.setFireCooldown(enemy.getFireCooldown() + gameData.getDeltaTime());

            double random = Math.random();

            // Calculate the threshold for shooting, which decreases over time.
            // Makes shooting more likely the longer the enemy has not shot while still keeping it somewhat random.
            double threshold = Math.max(0.01, 1 - enemy.getFireCooldown());

            // If the random number is less than the threshold, shoot
            if (random < threshold) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {world.addEntity(spi.createBullet(enemy, gameData));}
                );

                // Reset the cooldown
                enemy.setFireCooldown(0);
            }

            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));

            enemy.setRotation(enemy.getRotation() + rand.nextInt(-2, 3) * turnSpeed * gameData.getDeltaTime());
            rand.setSeed(System.currentTimeMillis());

            enemy.setX(enemy.getX() + changeX * moveSpeed * gameData.getDeltaTime());
            enemy.setY(enemy.getY() + changeY * moveSpeed * gameData.getDeltaTime());

            if (enemy.getX() < 0) {
                enemy.setX(enemy.getX() + gameData.getDisplayWidth());
            }

            if (enemy.getX() > gameData.getDisplayWidth()) {
                enemy.setX(enemy.getX() % gameData.getDisplayWidth());
            }

            if (enemy.getY() < 0) {
                enemy.setY(enemy.getY() + gameData.getDisplayHeight());
            }

            if (enemy.getY() > gameData.getDisplayHeight()) {
                enemy.setY(enemy.getY() % gameData.getDisplayHeight());
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
