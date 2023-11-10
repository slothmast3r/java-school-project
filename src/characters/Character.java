package characters;

public class Character {
    private String name;
    private int health;
    private int maxHealth;
    private int strength;
    private int defense;
    private int attack;

    public Character(String name, int health, int maxHealth, int strength) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.strength = strength;
    }

    public void showStats() {
        System.out.println("Name: " + name);
        System.out.println("Health: " + health + "/" + maxHealth);
        System.out.println("Strength: " + strength);
        System.out.println("Attack: " + getAttack());
        System.out.println("Defence: " + getDefense());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public int getDefense() {
        return defense;
    } public int getAttack() {
        return attack;
    }


    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public void increaseHealth(int addHealth){
        this.health += addHealth;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

}
