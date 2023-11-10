package items;

import characters.Player;

public class Sword extends Item {
    private int attackBonus;

    public Sword(String name, int price, int attackBonus) {
        super(name, price);
        this.attackBonus = attackBonus;
    }

    @Override
    public void use(Player player) {
        player.setAttack(player.getAttack() + attackBonus);
    }

    public int getAttackBonus() {
        return attackBonus;
    }
}
