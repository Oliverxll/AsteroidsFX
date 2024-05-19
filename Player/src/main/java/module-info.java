
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.playersystem.HelloController;
import dk.sdu.mmmi.cbse.playersystem.HelloPlugin;

module Player {
    requires Common;
    requires CommonBullet;
    requires CommonPlayer;
    requires CommonWeapon;
    requires java.desktop;
    requires javafx.graphics;
    requires Weapon;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    uses dk.sdu.mmmi.cbse.weapon.WeaponSPI;
    provides IGamePluginService with HelloPlugin;
    provides IEntityProcessingService with HelloController;
    
}
