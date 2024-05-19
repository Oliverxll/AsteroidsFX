package dk.sdu.mmmi.cbse.enemy.system;

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
        int toSpawn = 2;

        for (int i = 0; i < toSpawn; i++) {
            world.addEntity(createEnemy(gameData));
        }
    }

    public Entity createEnemy(GameData gameData) {
        enemy = new Enemy();
        Random rand = new Random();
        // Most is copied from player.
        enemy.setPolygonCoordinates(-5,-5,10,0,-5,5);
        // Spawn random somewhere within game window.
        enemy.setX(rand.nextInt(gameData.getDisplayWidth()));
        enemy.setY(rand.nextInt(gameData.getDisplayWidth()));
        enemy.setRadius(8);
        enemy.setRotation(rand.nextInt(360));
        enemy.setHealth(2);
        enemy.setColor(Color.RED);
        enemy.setMoveSpeed(150);
        enemy.setTurnSpeed(100);
        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}
