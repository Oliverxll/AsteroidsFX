import dk.sdu.mmmi.cbse.HelloFrom;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.HelloController;
import dk.sdu.mmmi.cbse.HelloPlugin;

module Enemy {
    requires javafx.graphics;
    requires Common;
    requires CommonEnemy;
    requires Weapon;
    requires CommonWeapon;

    uses dk.sdu.mmmi.cbse.weapon.WeaponSPI;

    provides IGamePluginService with HelloPlugin, HelloFrom;
    provides IEntityProcessingService with HelloController;
}