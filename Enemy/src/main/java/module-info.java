import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.enemy.system.HelloController;
import dk.sdu.mmmi.cbse.enemy.system.HelloPlugin;

module Enemy {
    requires javafx.graphics;
    requires Common;
    requires CommonEnemy;
    requires Weapon;
    requires CommonWeapon;

    uses dk.sdu.mmmi.cbse.weapon.WeaponSPI;

    provides IGamePluginService with HelloPlugin;
    provides IEntityProcessingService with HelloController;
}