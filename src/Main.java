import characters.Opponent;
import characters.Player;
import items.Armor;
import items.Item;
import items.Sword;
import locations.Arena;
import locations.Shop;
import locations.TrainingCamp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Main {
    private static Scanner scanner;
    private boolean quit;
    private Player player;    private Arena arena; // Store the arena instance
    private Set<Item> usedItems = new HashSet<>(); // Set to keep track of used items


    private int turn = 0;

    public Main() {
        scanner = new Scanner(System.in);
        quit = false;
        initializeGame();
        startGame();
        scanner.close();
    }

    private void initializeGame() {
        System.out.println("Welcome to Swords and Sandals!");
        System.out.println("1. Start New Game\n2. Load Game");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 2) {
            player = Player.loadStatsFromFile("playerStats.txt");
            if (player == null) {
                System.out.println("Failed to load game. Starting new game...");
                player = createNewPlayer();
            }
        } else {
            player = createNewPlayer();
        }
    }

    private void startGame() {
        while (!quit) {
            System.out.println("_______________________");
            System.out.println(player.getName() + "! What do you want to do?");
            System.out.println("Day: " + turn);

            System.out.println("1. Go to Arena\n2. Go to Shop\n3. Go to Training Camp \n4. Rest\n5. Show stats\n6. Show Inventory\n7. Save game\n8. Quit");
            int choice = scanner.nextInt();
            handlePlayerChoice(choice);
        }
    }

    private Player createNewPlayer() {
        System.out.println("Welcome to poor version of Swords and Sandals!");
        System.out.print("Enter your character's name: ");
        String name = scanner.nextLine();
        return new Player(name, 100, 100, 1, 100);
    }

    private void handlePlayerChoice(int choice) {
        switch (choice) {
            case 1:
                goToArena();
                break;
            case 2:
                Shop shop = new Shop();
                shop.visitShop(player);
                break;
            case 3:
                TrainingCamp trainingCamp = new TrainingCamp();
                trainingCamp.visitTrainingCamp(player);
                break;
            case 4:
                rest(player);
                break;
            case 5:
                showPlayerStats(player);
                break;
            case 6:
                showAllItems(player);
                showTotalValueOfItems(player);
                break;
            case 7:
                player.saveStatsToFile("playerStats.txt");
                break;
            case 8:
                quit = true;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void goToArena() {
        if (arena == null) {
            Opponent opponent = new Opponent("Sandalless Man", 100, 100, 1);
            arena = new Arena(opponent);
        }
        arena.enterArena(player);
    }
    private void showPlayerStats(Player player) {
        player.showStats();
        int choice;
        do {
            System.out.println("_______________________");
            System.out.println("1. Go back");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
            }

            choice = scanner.nextInt();

        } while (choice != 1);
    }

    private void showAllItems(Player player) {
        ArrayList<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }

        System.out.println("Inventory Items:");
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            String itemName = item.getName() + (usedItems.contains(item) ? " (Used)" : "");
            System.out.println((i + 1) + ". " + itemName);
        }

        System.out.println("Enter the number of the item to use, or 0 to go back:");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (choice > 0 && choice <= inventory.size()) {
            Item selectedItem = inventory.get(choice - 1);
            if (usedItems.contains(selectedItem)) {
                System.out.println("This item has already been used.");
                return;
            }

            if (selectedItem instanceof Sword || selectedItem instanceof Armor) {
                selectedItem.use(player);
                usedItems.add(selectedItem); // Mark the item as used
                System.out.println(selectedItem.getName() + " used.");
            } else {
                System.out.println("This item cannot be used.");
            }
        } else if (choice != 0) {
            System.out.println("Invalid choice.");
        }
    }


    private void showTotalValueOfItems(Player player) {
        int totalValue = player.getInventory().stream()
                .mapToInt(Item::getPrice)
                .sum();
        System.out.println("Total value of items: " + totalValue);
    }

    private void rest(Player player) {
        player.setHealth(player.getMaxHealth());
        turn++;
        System.out.println("_______________________");
        System.out.println("You rested well.");

        int choice;
        do {
            System.out.println("1. Wake up.");
            choice = scanner.nextInt();
        } while(choice != 1);
    }

    public static void main(String[] args) {
        new Main();
    }
}