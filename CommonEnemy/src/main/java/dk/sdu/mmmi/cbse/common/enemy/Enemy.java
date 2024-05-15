package dk.sdu.mmmi.cbse.common.enemy;


import dk.sdu.mmmi.cbse.common.data.Entity;

public class Enemy extends Entity {
    private double fireCooldown = 0;

    public double getFireCooldown() {
        return fireCooldown;
    }

    public void setFireCooldown(double fireCooldown) {
        this.fireCooldown = fireCooldown;
    }
}
