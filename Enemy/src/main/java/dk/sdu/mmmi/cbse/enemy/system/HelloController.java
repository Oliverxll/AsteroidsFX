package dk.sdu.mmmi.cbse.enemy.system;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.weapon.WeaponSPI;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class HelloController implements IEntityProcessingService {

    private Random rand = new Random();

    // Static because spawn rate is the same for all enemies.
    private static double spawnRate = 10;
    private static double spawnTimer = 0.0;

    @Override
    public void process(GameData gameData, World world) {

        spawnTimer += gameData.getDeltaTime();

        if (spawnTimer >= spawnRate) {
            // This takes excess milliseconds into account.
            spawnTimer -= spawnRate;
            System.out.println("Spawning enemy");

            world.addEntity(new HelloPlugin().createEnemy(gameData));
        }

        for (Entity entity : world.getEntities(Enemy.class)) {
            Enemy enemy = (Enemy) entity;

            if (enemy.getHealth() <= 0) {
                world.removeEntity(enemy);
            }

            if (enemy.getWeapon() != null) {
                enemy.getWeapon().setIsShooting(true);
            }
            else {
                getWeaponSPIs().stream().findFirst().ifPresent(
                        spi -> {
                            System.out.println("Creating weapon.");
                            enemy.setWeapon(spi.createWeapon(enemy));
                            enemy.getWeapon().setFireRate(1);
                            world.addEntity(enemy.getWeapon());
                        }
                );
            }

            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));

            enemy.setRotation(enemy.getRotation() + rand.nextInt(-2, 3) * enemy.getRotation() * gameData.getDeltaTime());
            rand.setSeed(System.currentTimeMillis());

            enemy.setX(enemy.getX() + changeX * enemy.getMoveSpeed() * gameData.getDeltaTime());
            enemy.setY(enemy.getY() + changeY * enemy.getMoveSpeed() * gameData.getDeltaTime());

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

    private Collection<? extends WeaponSPI> getWeaponSPIs() {
        return ServiceLoader.load(WeaponSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
