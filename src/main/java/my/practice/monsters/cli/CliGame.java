package my.practice.monsters.cli;

import my.practice.monsters.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;

public class CliGame implements Runnable {
  BufferedReader bufferedReader;
  Game game;
  MonsterFabric monsterFabric = new MonsterFabric();
  AtomicReference<String> currentCommand;
  CliGameState currentState;
  Monster monster1;
  Monster monster2;

  public CliGame(AtomicReference<String> currentCommand) {
    this.currentCommand = currentCommand;
    this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    this.currentState = CliGameState.Start;
    this.game = new Game();
  }

  private void handleInput(String s) throws IOException {
    switch (currentState) {
      case Start:
        //TODO implement the other eggs buying
        switch (s) {
          case "1":
            System.out.println("What would you like to buy?");
            printStoreProducts();
            switchState(CliGameState.BuyingEggsOrFood);
            break;
          case "2":
            System.out.println("Here are your monsters:");
            int i = 1;
            for (Monster m : game.getPlayer().getMonsters()) {
              System.out.println(i + ". " + m.getType());
              i++;
            }
            break;
          case "3":
            if (game.getBreeder().getMonster() != null) {
              System.out.println("You are already breeding a monster!");
              break;
            }
            System.out.println("Choose monsters for breeding");
            int j = 1;
            for (Monster m : game.getPlayer().getMonsters()) {
              System.out.println(j + ". " + m.getType());
              j++;
            }
            System.out.println("Choose monster #1:");
            switchState(CliGameState.ChoosingMonster1);
            break;
          case "4":
            break;
          case "0":
            System.exit(0);
        }
        break;
      case ChoosingMonster1:
        if (s != null) {
          this.monster1 = game.getPlayer().getMonsters().get(Integer.parseInt(s) - 1);
          System.out.println("Choose monster #2:");
          switchState(CliGameState.ChoosingMonster2);
        }
        break;
      case ChoosingMonster2:
        if (s != null) {
          this.monster2 = game.getPlayer().getMonsters().get(Integer.parseInt(s) - 1);
          breedingChoice(monster1, monster2);
          System.out.println("You are breeding " + monster1.getType() + " and " + monster2.getType()
              + ". Breeding time: " + game.getBreeder().getTimeToBreed());
          switchState(CliGameState.Start);
          printNavigation();
        }
        break;
      case ChoosingMonsterForFeeding:
        break;
      case BuyingEggsOrFood:
        if (s.equals("1")) {
          System.out.println("How many Food Bowls would you like to buy?");
          switchState(CliGameState.ChoosingFoodAmount);
        } else {
          switchState(CliGameState.ChoosingAnEgg);
        }
        break;
      case ChoosingFoodAmount:
        if (Integer.parseInt(s) * Product.FoodBowl.price > game.getPlayer().getGold()) {
          System.out.println("You don't have enough gold! Choose another amount:");
        } else {
          game.getPlayer().addFoodBowls(Integer.parseInt(s));
          System.out.println("You've bought " + Integer.parseInt(s) + " Food Bowl(s). Now you have " +
              game.getPlayer().getFoodBowls() + " Food Bowl(s).");
          switchState(CliGameState.Start);
          printNavigation();
        }
        break;
      case ChoosingAnEgg:
        break;
    }
  }

  public void switchState(CliGameState newState) {
    this.currentState = newState;
  }

  public void breedingChoice(Monster monster1, Monster monster2) throws IOException {
    Monster monster = monsterFabric.createMonster(game.breeding(monster1, monster2, monsterFabric).
        getElements());
    game.getBreeder().setMonster(monster);
    game.getBreeder().setTimeToBreed(); //Вот это questionable, но если инициализировать вместе с
    //монстром - потом ругается на null в монстре при завершении работы потока, пусть пока тут лежит
  }

  public void printNavigation() {
    System.out.println("""
        What would you like to do?
        1. Go to the Monster Store
        2. Get the list of my monsters
        3. Breed a monster
        4. Feed a monster
        0. Exit game""");
  }

  public void printStoreProducts() {
    for (var i = 1; i < game.getStore().storeProducts.size() + 1; i++) {
      System.out.println(i + ". " + game.getStore().getStoreProducts().get(i - 1).name);
    }
  }

  @Override
  public void run() {
    game.startGame();

    String input = "Username"; // Потом поменять
    //input = this.currentCommand.get();
    game.setPlayer(new Player(input));

    //TODO Set for tests, don't forget to remove
    HashSet<Monster.Element> elements1 = new HashSet<>();
    elements1.add(Monster.Element.WATER);
    game.getPlayer().addMonster(monsterFabric.createMonster(elements1));
    HashSet<Monster.Element> elements2 = new HashSet<>();
    elements2.add(Monster.Element.FIRE);
    game.getPlayer().addMonster(monsterFabric.createMonster(elements2));
    System.out.println("""
        Welcome to MonsterPractice - the game, where you'll breed
        and raise monsters to wake the MonsterBoss up!
        """);
    System.out.printf("Have a nice game, %s! Go to the Store and buy your first Monster Egg!%n",
        game.getPlayer().getName());
    //TODO implement buying of the first egg
    printNavigation();
    while (true) {
      //handling user's input, not waiting for it
      //updating the game
      //sleep with many options
      try {
        input = this.currentCommand.get();
        if (input != null) {
          handleInput(input);
          this.currentCommand.set(null);
        }
        game.update();
        Thread.sleep(1000);

      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}