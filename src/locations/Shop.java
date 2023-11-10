package locations;

import characters.Player;
import items.Armor;
import items.HealthPotion;
import items.Item;
import items.Sword;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shop {
    private List<Item> itemsForSale;
    private Scanner scanner;

    public Shop() {
        this.scanner = new Scanner(System.in);
        this.itemsForSale = new ArrayList<>();
        populateShopWithItems();
    }

    private void populateShopWithItems() {
        itemsForSale.add(new Sword("Basic Sword", 100, 10));
        itemsForSale.add(new Armor("Basic Sandals", 150, 5));
        itemsForSale.add(new HealthPotion("Health Potion", 10, 10));
        itemsForSale.add(new Sword("Rubber Chicken Sword", 200, 5));
        itemsForSale.add(new Armor("Not Sandals", 300, 0));
        itemsForSale.add(new Sword("Tickling Feather", 50,-5));
        itemsForSale.add(new Item("Cursed Coin", 200));
        itemsForSale.add(new Sword("Portable Black Hole", 500,100));
        itemsForSale.add(new Armor("Golden Sandals", 350, 50));
    }

    public void visitShop(Player player) {
        boolean leaveShop = false;

        while (!leaveShop) {
            System.out.println("_______________________");
            System.out.println("Welcome to the Shop! Choose an option:");
            System.out.println("1. Buy Items\n2. Sell Items\n3. Leave Shop");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    buyItems(player);
                    break;
                case 2:
                    sellItems(player);
                    break;
                case 3:
                    leaveShop = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void sellItems(Player player) {
        ArrayList<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }

        System.out.println("Select an item to sell:");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ". " + inventory.get(i).getName() + " - Price: " + calculateSellPrice(inventory.get(i)));
        }
        System.out.println((inventory.size() + 1) + ". Cancel");

        int choice = scanner.nextInt() - 1;

        if (choice >= 0 && choice < inventory.size()) {
            Item selectedItem = inventory.get(choice);
            int sellPrice = calculateSellPrice(selectedItem);
            player.setGold(player.getGold() + sellPrice);
            inventory.remove(selectedItem);
            System.out.println("Sold " + selectedItem.getName() + " for " + sellPrice + " gold.");
        } else if (choice != inventory.size()) {
            System.out.println("Invalid choice.");
        }
    }
    private int calculateSellPrice(Item item) {
        return item.getPrice() / 2;
    }
    private void buyItems(Player player) {
        System.out.println("Available items for sale:");
        for (int i = 0; i < itemsForSale.size(); i++) {
            Item item = itemsForSale.get(i);
            String itemDetails = (i + 1) + ". " + item.getName() + " - " + item.getPrice() + " Gold";

            if (item instanceof Sword) {
                Sword sword = (Sword) item;
                itemDetails += " (Sword, Attack: " + sword.getAttackBonus() + ")";
            } else if (item instanceof Armor) {
                Armor armor = (Armor) item;
                itemDetails += " (Armor, Defense: " + armor.getDefenseBonus() + ")";
            } else if (item instanceof HealthPotion) {
                HealthPotion potion = (HealthPotion) item;
                itemDetails += " (Health Potion, Heal: " + potion.getHealAmount() + ")";
            } // Add more else if blocks for other item types if needed

            System.out.println(itemDetails);
        }

        System.out.println("Choose an item to buy (or 0 to go back):");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= itemsForSale.size()) {
            Item selectedItem = itemsForSale.get(choice - 1);
            if (player.getGold() >= selectedItem.getPrice()) {
                player.purchaseItem(selectedItem);
                System.out.println("You have purchased: " + selectedItem.getName());
            } else {
                System.out.println("You don't have enough gold!");
            }
        }
    }

}