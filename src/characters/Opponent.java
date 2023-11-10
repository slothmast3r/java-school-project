package characters;

public class Opponent extends Character{
    public Opponent(String name, int health, int maxHealth, int strength) {
        super(name, health, maxHealth, strength);
    }

    @Override
    public void showStats() {
        System.out.println("Opponent's Name: " + super.getName());
        System.out.println("Health: " + super.getHealth()+"/"+super.getMaxHealth());
        System.out.println("Strength: " + super.getStrength());
    }
}
