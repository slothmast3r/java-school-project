package items;

import characters.Player;

public class HealthPotion extends Item {
    private int healthRestore;

    public HealthPotion(String name, int price, int healthRestore) {
        super(name, price);
        this.healthRestore = healthRestore;
    }

    @Override
    public void use(Player player) {
        player.setHealth(Math.min(player.getHealth() + healthRestore, player.getMaxHealth()));
    }

    public int getHealAmount() {
        return healthRestore;
    }
}
