package my.practice.monsters.cli;

import my.practice.monsters.model.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;

public class CliGame implements Runnable {
  Game game;
  private final MonsterFabric monsterFabric = new MonsterFabric();
  private final AtomicReference<String> currentCommand;
  private CliGameState currentState;
  private Monster monster1;
  private Monster monster2;
  private Monster monsterForFeeding;

  public CliGame() {
    this.currentCommand = new AtomicReference<>();
    this.currentState = CliGameState.Start;
    this.game = new Game();
  }

  public AtomicReference<String> getCurrentCommand() {
    return currentCommand;
  }

  public void setCurrentCommand(String currentCommand) {
    this.currentCommand.set(currentCommand);
  }

  @Override
  public void run() {
    game.startGame();
    //System.out.println("Please, enter your name:");
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
    while (!Thread.currentThread().isInterrupted()) {
      //handling user's input, not waiting for it
      //updating the game
      //sleep with many options
      try {
        input = currentCommand.get();
        if (input != null) {
          handleInput(input);
          currentCommand.set(null);
        }
        game.update();
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("Thread was interrupted");
        Thread.currentThread().interrupt();
      } catch (IOException e) {
        System.out.println("Incorrect input");
        throw new RuntimeException(e);
      }
    }
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

  private void handleInput(String s) throws IOException, InterruptedException {
    switch (currentState) {
      case Start:
        switch (s) {
          case "1":
            switchState(CliGameState.Store);
            printStoreProducts();
            break;
          case "2":
            System.out.println("Here are your monsters:");
            printMonsters();
            printNavigation();
            break;
          case "3":
            if (game.getBreeder().getMonster() != null) {
              System.out.println("You are already breeding a monster!");
              break;
            }
            System.out.println("Choose monsters for breeding");
            printMonsters();
            System.out.println("Choose monster #1:");
            switchState(CliGameState.ChoosingMonster1);
            break;
          case "4":
            System.out.println("Choose a monster for feeding:");
            printMonsters();
            switchState(CliGameState.ChoosingMonsterForFeeding);
            break;
          case "0":
            Thread.currentThread().interrupt();
        }
        break;
      case Store:
        //TODO implement the other eggs buying
        if (s.equals("0")) {
          switchState(CliGameState.Start);
          printNavigation();
        } else {
          switch (game.getStore().storeProducts.get(Integer.parseInt(s) - 1)) {
            case FoodBowl:
              System.out.println("How many Food Bowls would you like to buy?");
              switchState(CliGameState.ChoosingFoodAmountForBuying);
              break;
            case BubbleEgg:
              if (Product.BubbleEgg.price > game.getPlayer().getGold()) {
                System.out.println("You don't have enough gold!");
              } else {
                System.out.println("You've bought " + Product.BubbleEgg.name + "!");
                game.getPlayer().addMonster(monsterFabric.createMonster(new HashSet<>() {{
                  add(Monster.Element.AIR);
                }}));
                game.getPlayer().setGold(game.getPlayer().getGold() - Product.BubbleEgg.price);
                System.out.println("Your gold: " + game.getPlayer().getGold());
              }
              switchState(CliGameState.Store);
              printStoreProducts();
              break;
            case SparkEgg:
              if (Product.SparkEgg.price > game.getPlayer().getGold()) {
                System.out.println("You don't have enough gold!");
              } else {
                System.out.println("You've bought " + Product.SparkEgg.name + "!");
                game.getPlayer().addMonster(monsterFabric.createMonster(new HashSet<>() {{
                  add(Monster.Element.FIRE);
                }}));
                game.getPlayer().setGold(game.getPlayer().getGold() - Product.SparkEgg.price);
                System.out.println("Your gold: " + game.getPlayer().getGold());
              }
              switchState(CliGameState.Store);
              printStoreProducts();
              break;
            case SplashEgg:
              if (Product.BubbleEgg.price > game.getPlayer().getGold()) {
                System.out.println("You don't have enough gold!");
              } else {
                System.out.println("You've bought " + Product.SplashEgg.name + "!");
                game.getPlayer().addMonster(monsterFabric.createMonster(new HashSet<>() {{
                  add(Monster.Element.WATER);
                }}));
                game.getPlayer().setGold(game.getPlayer().getGold() - Product.SplashEgg.price);
                System.out.println("Your gold: " + game.getPlayer().getGold());
              }
              switchState(CliGameState.Store);
              printStoreProducts();
              break;
          }
        }
        break;
      case ChoosingFoodAmountForBuying:
        if (s.equals("0")) {
          switchState(CliGameState.Start);
          printNavigation();
        } else {
          final var foodAmount = Integer.parseInt(s);
          final var price = foodAmount * Product.FoodBowl.price;
          if (game.getPlayer().getGold() >= price) {
            game.buyFood(foodAmount);
            System.out.println("You've bought " + foodAmount + " Food Bowl(s). Now you have " +
                game.getPlayer().getFoodBowls() + " Food Bowl(s).");
            System.out.println("Your gold: " + game.getPlayer().getGold());
          } else {
            System.out.println("You don't have enough gold! Choose another amount or " +
                "press 0 to go back to the Monster Store:");
          }
        }
        switchState(CliGameState.Store);
        printStoreProducts();
        break;
      case ChoosingMonster1:
        try {
          this.monster1 = game.getPlayer().getMonsters().get(Integer.parseInt(s) - 1);
          System.out.println("Choose monster #2:");
          switchState(CliGameState.ChoosingMonster2);
        } catch (IndexOutOfBoundsException e) {
          System.out.println("There is no monster with this number." +
              "Choose again or press 0 to go to the Main Menu:");
        }
        break;
      case ChoosingMonster2:
        try {
          this.monster2 = game.getPlayer().getMonsters().get(Integer.parseInt(s) - 1);
          breedingChoice(monster1, monster2);
          System.out.println("You are breeding " + monster1.getType() + " and " + monster2.getType()
              + ". Breeding time: " + game.getBreeder().getTimeToBreed());
          switchState(CliGameState.Start);
          printNavigation();
        } catch (IndexOutOfBoundsException e) {
          System.out.println("There is no monster with this number. " +
              "Choose again or press 0 to go to the Main Menu:");
        }
        break;
      case ChoosingMonsterForFeeding:
        try {
          this.monsterForFeeding = game.getPlayer().getMonsters().get(Integer.parseInt(s) - 1);
          System.out.println("How many bowls would you like to feed it? " +
              "(Press 0 to go the Main Menu)");
          switchState(CliGameState.ChoosingFoodForFeeding);
        } catch (IndexOutOfBoundsException e) {
          System.out.println("There is no monster with this number. " +
              "Choose again or press 0 to go to the Main Menu:");
        }
        break;
      case ChoosingFoodForFeeding:
        if (s.equals("0")) {
          printNavigation();
          switchState(CliGameState.Start);
        } else {
          if (Integer.parseInt(s) > game.getPlayer().getFoodBowls()) {
            System.out.println("You don't have enough food bowls! Choose another amount or " +
                "press 0 to go back to the Main Menu:");
          } else {
            game.getPlayer().removeFoodBowls(Integer.parseInt(s));
            //TODO add setters for monsterForFeeding level and gold.
          }
        }
        break;
      case ChoosingAnEgg: //wth
        printNavigation();
        switchState(CliGameState.Start);
        break;
    }
  }

  private void printMonsters() {
    int j = 1;
    for (Monster m : game.getPlayer().getMonsters()) {
      System.out.println(j + ". " + m.getType());
      j++;
    }
  }

  public void switchState(CliGameState newState) {
    this.currentState = newState;
  }

  public void breedingChoice(Monster monster1, Monster monster2) {
    Monster monster = monsterFabric.createMonster(game.breeding(monster1, monster2, monsterFabric).
        getElements());
    game.getBreeder().setMonster(monster);
    game.getBreeder().setTimeToBreed(); //Вот это questionable, но если инициализировать вместе с
    //монстром - потом ругается на null в монстре при завершении работы потока, пусть пока тут лежит
  }

  public void printStoreProducts() {
    System.out.println("Choose one of the following products " +
        "or press 0 to exit the Monster Store:");
    for (var i = 1; i < game.getStore().storeProducts.size() + 1; i++) {
      System.out.println(i + ". " + game.getStore().getStoreProducts().get(i - 1).name);
    }
    System.out.println("0. Exit the Monster Store");
  }
}
