package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.player.Player;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.weapon.Weapon;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class PlayerControlSystem implements IEntityProcessingService {



    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Player.class)) {
            Player player = (Player) entity;

            if (player.getHealth() <= 0) {
                world.removeEntity(player);
                continue;
            }

            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - player.getTurnSpeed() * gameData.getDeltaTime());
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + player.getTurnSpeed() * gameData.getDeltaTime());
            }
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() + changeX * gameData.getDeltaTime() * player.getMoveSpeed());
                player.setY(player.getY() + changeY * gameData.getDeltaTime() * player.getMoveSpeed());
            }
            if(gameData.getKeys().isDown(GameKeys.SPACE)) {
                if (player.getWeapon() != null) {
                    player.getWeapon().setIsShooting(true);
                } else {
                    getWeaponSPIs().stream().findFirst().ifPresent(
                            spi -> {
                                System.out.println("Creating weapon.");
                                Weapon weapon = spi.createWeapon(player);
                                weapon.setFireRate(0.25);
                                world.addEntity(weapon);
                                player.setWeapon(weapon);
                                player.getWeapon().setFireCooldown(1);
                                player.getWeapon().setIsShooting(true);
                            }
                    );
                }
            }
            
            if (player.getX() < 0) {
                player.setX(1);
            }

            if (player.getX() > gameData.getDisplayWidth()) {
                player.setX(gameData.getDisplayWidth()-1);
            }

            if (player.getY() < 0) {
                player.setY(1);
            }

            if (player.getY() > gameData.getDisplayHeight()) {
                player.setY(gameData.getDisplayHeight()-1);
            }

                                        
        }
    }


    private Collection<? extends WeaponSPI> getWeaponSPIs() {
        return ServiceLoader.load(WeaponSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
