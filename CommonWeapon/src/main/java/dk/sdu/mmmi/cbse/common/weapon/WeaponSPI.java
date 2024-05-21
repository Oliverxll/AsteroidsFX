package dk.sdu.mmmi.cbse.common.weapon;

import dk.sdu.mmmi.cbse.common.data.Entity;

public interface WeaponSPI {
    Weapon createWeapon(Entity owner);
}
