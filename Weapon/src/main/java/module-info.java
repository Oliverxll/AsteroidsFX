import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.weapon.WeaponControlSystem;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;

module Weapon {
    exports dk.sdu.mmmi.cbse.weapon;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    requires Common;
    requires CommonWeapon;
    requires CommonBullet;

    provides WeaponSPI with WeaponControlSystem;
    provides IEntityProcessingService with WeaponControlSystem;
    provides IGamePluginService with dk.sdu.mmmi.cbse.weapon.WeaponPlugin;
}