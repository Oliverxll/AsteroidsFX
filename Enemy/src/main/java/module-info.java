import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.enemy.system.EnemyControlSystem;
import dk.sdu.mmmi.cbse.enemy.system.EnemyPlugin;

module Enemy {
    requires javafx.graphics;
    requires Common;
    requires CommonEnemy;
    requires CommonBullet;

    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;

    provides IGamePluginService with EnemyPlugin;
    provides IEntityProcessingService with EnemyControlSystem;
}