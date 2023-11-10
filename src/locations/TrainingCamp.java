package locations;

import characters.Player;

import java.util.Scanner;

public class TrainingCamp {
    private Scanner scanner;
    private final int strengthTrainingCost = 50;
    private final int healthTrainingCost = 75;

    public TrainingCamp() {
        this.scanner = new Scanner(System.in);
    }

    public void visitTrainingCamp(Player player) {
        boolean isTraining = true;

        while (isTraining) {
            System.out.println("_______________________");
            System.out.println("Welcome to the Training Camp! Choose an option:");
            System.out.println("1. Train Strength\n2. Train Health\n3. Leave");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    trainStrength(player);
                    break;
                case 2:
                    trainHealth(player);
                    break;
                case 3:
                    System.out.println("Leaving the Training Camp.");
                    isTraining = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void trainStrength(Player player) {
        if (player.getGold() >= strengthTrainingCost) {
            System.out.println("Training strength...");
            int strengthIncrease = 5;
            player.setStrength(player.getStrength() + strengthIncrease);
            player.setGold(player.getGold() - strengthTrainingCost);
            System.out.println("Your strength has increased! New strength: " + player.getStrength());
            System.out.println("Gold remaining: " + player.getGold());
        } else {
            System.out.println("Not enough gold to train strength.");
        }
    }

    private void trainHealth(Player player) {
        if (player.getGold() >= healthTrainingCost) {
            System.out.println("Training durability...");
            int healthIncrease = 10;
            player.setMaxHealth(player.getMaxHealth() + healthIncrease);
            player.setHealth(player.getHealth() + healthIncrease);
            player.setGold(player.getGold() - healthTrainingCost);
            System.out.println("Your durability has increased! New max health: " + player.getMaxHealth());
            System.out.println("Gold remaining: " + player.getGold());
        } else {
            System.out.println("Not enough gold to train durability.");
        }
    }
}
