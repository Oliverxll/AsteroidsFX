package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.weapon.Weapon;

public interface WeaponSPI {
    public Weapon createWeapon(Entity owner);
}
