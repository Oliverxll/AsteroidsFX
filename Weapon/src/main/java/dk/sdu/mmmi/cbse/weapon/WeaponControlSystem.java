package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.weapon.Weapon;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class WeaponControlSystem implements IEntityProcessingService, WeaponSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Weapon.class)) {
            Weapon weapon = (Weapon) entity;

            weapon.setFireCooldown(weapon.getFireCooldown() + gameData.getDeltaTime());

            if (weapon.canShoot()) {
                if (weapon.getIsShooting()) {
                    getBulletSPIs().stream().findFirst().ifPresent(
                            spi -> {
                                world.addEntity(spi.createBullet(weapon.getOwner(), gameData));
                            }
                    );
                    weapon.setFireCooldown(0);
                }
            }

            weapon.setIsShooting(false);
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    @Override
    public Weapon createWeapon(Entity owner) {
        return new Weapon(owner);
    }
}
