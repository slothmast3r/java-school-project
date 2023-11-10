package locations;

import characters.Player;
import characters.Opponent;
import items.HealthPotion;
import items.Item;

import java.util.List;
import java.util.Scanner;

public class Arena {
    private Opponent opponent;
    Scanner scanner;
    public Arena(Opponent initialOpponent) {
        this.opponent = initialOpponent;
        scanner = new Scanner(System.in);
    }

    public void enterArena(Player player) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You have entered the arena!");

        // Show opponent's stats
        opponent.showStats();

        // Decision to fight or leave
        System.out.println("Do you want to fight or leave? (1: Fight, 2: Leave)");
        int choice = scanner.nextInt();

        if (choice == 1) {
            fight(player);
        } else {
            System.out.println("You have left the arena.");
        }
    }

    private void fight(Player player) {
        System.out.println("Fight started!");
        boolean autoCombatEnabled = false;

        // Combat loop
        while (player.getHealth() > 0 && opponent.getHealth() > 0) {
            if (!autoCombatEnabled) {
                System.out.println("Your turn: \n1. Attack, \n2. Use Health Potion, \n3. Enable Auto-Combat, \n4. Flee");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        playerAttack(player, opponent);
                        break;
                    case 2:
                        chooseAndUseHealthPotion(player);
                        break;
                    case 3:
                        autoCombatEnabled = true;
                        break;
                    case 4:
                        if (attemptToFlee(player)) {
                            return; // Ends the fight if the player successfully flees
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Lost your turn!");
                        break;
                }
            }

            if (autoCombatEnabled) {
                playerAttack(player, opponent);
            }

            // Check if opponent is defeated
            if (opponent.getHealth() <= 0) {
                System.out.println("You have defeated the opponent!");
                awardVictoryReward(player);
                break;
            }

            // Opponent's turn
            opponentAttack(player, opponent);

            // Check if player is defeated
            if (player.getHealth() <= 0) {
                System.out.println("You have been defeated!");
                lossAmount(player);
                break;
            }
        }
    }
    private boolean attemptToFlee(Player player) {
        if (player.getGold() > opponent.getStrength()) {
            int fleeCost = calculateFleeCost(player);
            player.setGold(player.getGold() - fleeCost);
            System.out.println("You have fled the arena but you lost dignity and " + fleeCost + " gold.");
            return true;
        } else {
            System.out.println("You are too poor to flee!");
            return false;
        }
    }

    private int calculateFleeCost(Player player) {
        return player.getGold() /10 + opponent.getStrength();
    }
    private void awardVictoryReward(Player player) {
        int reward = calculateGoldAmount(opponent, 10);
        player.setGold(player.getGold() + reward);
        System.out.println("You have been awarded " + reward + " gold for your victory!");
        player.incrementVictories();
        updateOpponentStats(player, true);
    }
    private int calculateGoldAmount(Opponent opponent, int multiplier) {
        return opponent.getStrength() * multiplier;
    }
    private void playerAttack(Player player, Opponent opponent) {
        int damage = player.getStrength()+ player.getAttack();
        opponent.setHealth(opponent.getHealth() - damage);
        System.out.println("You dealt " + damage + " damage to the opponent.");
    }
    private void updateOpponentStats(Player player,boolean isVictory) {
        int multiplier = isVictory? player.getVictories(): -1;
        int increasedHealth = opponent.getMaxHealth() + (multiplier * 10);
        int increasedStrength = opponent.getStrength() + (multiplier * 5);

        opponent.setMaxHealth(increasedHealth);
        opponent.setHealth(increasedHealth);
        opponent.setStrength(increasedStrength);

        System.out.println("A new enemy has entered the arena!");
    }

    private void lossAmount(Player player){
        int loss = calculateGoldAmount(opponent,5);
        player.setGold(player.getGold() - loss);
        System.out.println("You have lost " + loss + " gold from defeat!");
        updateOpponentStats(player, false);
    }
    private void chooseAndUseHealthPotion(Player player) {
        List<Item> healthPotions = player.getInventory().stream()
                .filter(item -> item instanceof HealthPotion)
                .toList();

        if (healthPotions.isEmpty()) {
            System.out.println("No health potions available.");
            return;
        }

        System.out.println("Choose a health potion to use:");
        for (int i = 0; i < healthPotions.size(); i++) {
            System.out.println((i + 1) + ". " + healthPotions.get(i));
        }

        int choice = scanner.nextInt() - 1;
        if (choice >= 0 && choice < healthPotions.size()) {
            HealthPotion selectedPotion = (HealthPotion) healthPotions.get(choice);
            selectedPotion.use(player);
            player.getInventory().remove(selectedPotion);
            System.out.println("Used " + selectedPotion.getName());
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private void opponentAttack(Player player, Opponent opponent) {
        int damage = opponent.getStrength() - player.getDefense();
        player.setHealth(player.getHealth() - damage);
        System.out.println("Opponent dealt " + damage + " damage to you.");
    }
}
