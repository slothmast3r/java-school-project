package characters;

import items.Armor;
import items.HealthPotion;
import items.Item;
import items.Sword;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Player extends Character {
    private int gold;
    int victories =0;
    int defeats =0;
    private ArrayList<Item> inventory;
    public Player(String name, int health, int maxHealth, int strength, int gold) {
        super(name, health, maxHealth, strength);
        this.gold = gold;
        this.inventory = new ArrayList<>();

    }

    public void saveStatsToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(getName() + "\n");
            writer.write(getHealth() + "\n");
            writer.write(getMaxHealth() + "\n");
            writer.write(getStrength() + "\n");
            writer.write(getGold() + "\n");
            writer.write(victories + "\n");
            writer.write(defeats + "\n");

            // Save inventory items
            for (Item item : inventory) {
                writer.write(item.getName() + ",");
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Player loadStatsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String name = reader.readLine();
            int health = Integer.parseInt(reader.readLine());
            int maxHealth = Integer.parseInt(reader.readLine());
            int strength = Integer.parseInt(reader.readLine());
            int gold = Integer.parseInt(reader.readLine());
            int victories = Integer.parseInt(reader.readLine());
            int defeats = Integer.parseInt(reader.readLine());

            Player player = new Player(name, health, maxHealth, strength, gold);
            player.victories = victories;
            player.defeats = defeats;

            // Load inventory items
            String[] items = reader.readLine().split(",");
            for (String itemName : items) {
                if (!itemName.isEmpty()) {
                    // Assuming a method to create items based on name
                    Item item = createItemByName(itemName);
                    player.addItemToInventory(item);
                }
            }

            return player;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private Item createItemByName(String name) {
        return switch (name) {
            case "Basic Sword" -> new Sword("Basic Sword", 100, 10);
            case "Basic Sandals" -> new Armor("Basic Sandals", 150, 5);
            case "Health Potion" -> new HealthPotion("Health Potion", 10, 10);
            case "Rubber Chicken Sword" -> new Sword("Rubber Chicken Sword", 200, 5);
            case "Not Sandals" -> new Armor("Not Sandals", 300, 0);
            case "Tickling Feather" -> new Sword("Tickling Feather", 50, -5);
            case "Cursed Coin" -> new Item("Cursed Coin", 200);
            case "Portable Black Hole" -> new Sword("Portable Black Hole", 500, 100);
            case "Golden Sandals" -> new Armor("Golden Sandals", 350, 50);
            default -> null;
        };
    }


    public void addItemToInventory(Item item) {
        inventory.add(item);
    }




    public void incrementVictories() {
        victories++;
    }public void incrementDefeats() {
        defeats++;
    }

    public int getVictories() {
        return victories;
    }public int getDefeats() {
        return defeats;
    }
    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public void showStats() {
        super.showStats();
        System.out.println("Gold: " + gold);

    }

    public void purchaseItem(Item selectedItem) {
        if(getGold() >= selectedItem.getPrice()) {
            addItemToInventory(selectedItem);
            setGold(getGold() - selectedItem.getPrice());
        }
        else{
            System.out.println("Not enough money!");
        }

    }

    public ArrayList<Item> getInventory() {
        return new ArrayList<>(inventory);
    }
    public void useItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (item instanceof Sword) {
                    ((Sword) item).use(this);
                    System.out.println(itemName + " used. Attack power increased!");
                }
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }



}