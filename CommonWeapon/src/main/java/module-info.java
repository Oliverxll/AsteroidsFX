module CommonWeapon {
    requires Common;
    requires CommonBullet;
    exports dk.sdu.mmmi.cbse.common.weapon;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;

}