import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;

module Enemy {
    requires Common;
    requires CommonEnemy;
    requires javafx.graphics;

    provides IGamePluginService with EnemyPlugin;
}