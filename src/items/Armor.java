package items;

import characters.Player;

public class Armor extends Item {
    private int defenseBonus;

    public Armor(String name, int price, int defenseBonus) {
        super(name, price);
        this.defenseBonus = defenseBonus;
    }


    @Override
    public void use(Player player) {
        player.setDefense(player.getDefense() + defenseBonus);
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }
}
