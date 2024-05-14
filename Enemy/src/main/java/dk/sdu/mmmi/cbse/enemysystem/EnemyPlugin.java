package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;
import javafx.scene.paint.Color;

public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        int toSpawn = 1;

        for (int i = 0; i < toSpawn; i++) {
            world.addEntity(createEnemy(gameData));
        }
    }

    private Entity createEnemy(GameData gameData) {
        enemy = new Enemy();
        Random rand = new Random();
        // Most is copied from player.
        enemy.setPolygonCoordinates(-5,-5,10,0,-5,5);
        // Spawn random somewhere within game window.
        enemy.setX(rand.nextInt(gameData.getDisplayWidth()));
        enemy.setY(rand.nextInt(gameData.getDisplayWidth()));
        enemy.setRadius(8);
        enemy.setHealth(3);
        enemy.setColor(Color.RED);
        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}
