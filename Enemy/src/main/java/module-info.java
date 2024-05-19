import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.enemy.system.EnemyControlSystem;
import dk.sdu.mmmi.cbse.enemy.system.EnemyPlugin;

module Enemy {
    requires javafx.graphics;
    requires Common;
    requires CommonEnemy;
    requires CommonWeapon;

    uses dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;

    provides IGamePluginService with EnemyPlugin;
    provides IEntityProcessingService with EnemyControlSystem;
}