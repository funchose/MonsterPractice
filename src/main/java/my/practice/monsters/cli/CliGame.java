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
    this.currentState = CliGameState.Greeting;
    this.game = new Game();
  }

  public void setCurrentCommand(String currentCommand) {
    this.currentCommand.set(currentCommand);
  }

  @Override
  public void run() {
    game.startGame();
    System.out.println("Please, enter your name:");
    String input;
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
      case Greeting:
        game.setPlayer(new Player(s));
        System.out.println("""
            Welcome to MonsterPractice - the game, where you'll breed
            and raise monsters to wake the MonsterBoss up!
            """);
        if (game.getPlayer() != null) {
          System.out.printf("Have a nice game, %s! Go to the Store and buy your first Monster Egg!%n",
              game.getPlayer().getName());

          //TODO Set for tests, don't forget to remove
          HashSet<Monster.Element> elements1 = new HashSet<>();
          elements1.add(Monster.Element.WATER);
          game.getPlayer().addMonster(monsterFabric.createMonster(elements1));
          HashSet<Monster.Element> elements2 = new HashSet<>();
          elements2.add(Monster.Element.FIRE);
          game.getPlayer().addMonster(monsterFabric.createMonster(elements2));

          printNavigation();
          switchState(CliGameState.Start);
        }
        break;
      case Start:
        switchBetweenStartCases(s);
        break;
      case Store:
        if (s.equals("0")) {
          switchState(CliGameState.Start);
          printNavigation();
        } else {
          var product = getProduct(s);
          switchBetweenProducts(product);
        }
        break;
      case ChoosingFoodAmountForBuying:
        if (s.equals("0")) {
          switchState(CliGameState.Store);
          printStoreProducts();
        } else {
          buyFood(s);
        }
        break;
      case ChoosingEgg:
        if (s.equals("0")) {
          switchState(CliGameState.Store);
          printStoreProducts();
        } else {
          buyEgg(s);
        }
        break;
      case ChoosingMonster1:
        try {
          this.monster1 = getMonster(s);
          System.out.println("Choose monster #2:");
          switchState(CliGameState.ChoosingMonster2);
        } catch (IndexOutOfBoundsException e) {
          System.out.println("There is no monster with this number." +
              "Choose again or press 0 to go to the Main Menu:");
        }
        break;
      case ChoosingMonster2:
        try {
          this.monster2 = getMonster(s);
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
          this.monsterForFeeding = getMonster(s);
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
          var foodAmountToFeed = Integer.parseInt(s);
          if (!isAbleToFeed(foodAmountToFeed)) {
            System.out.println("You don't have enough food bowls! Choose another amount or " +
                "press 0 to go back to the Main Menu:");
          } else {
            game.getPlayer().removeFoodBowls(foodAmountToFeed);
            //TODO add setters for monsterForFeeding level and gold.
          }
        }
        break;
    }
  }

  private void switchBetweenStartCases(String s) {
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
  }

  private String getProduct(String s) {
    int productNum = Integer.parseInt(s) - 1;
    var store = game.getStore();
    return store.getStoreProducts().get(productNum);
  }

  private void switchBetweenProducts(String product) {
    switch (product) {
      case "Food":
        System.out.println("How many Food Bowls would you like to buy?");
        switchState(CliGameState.ChoosingFoodAmountForBuying);
        break;
      case "Monster Eggs":
        System.out.println("Choose one of the monsters eggs:");
        printEggs();
        switchState(CliGameState.ChoosingEgg);
    }
  }

  private void buyFood(String s) {
    final var foodAmount = Integer.parseInt(s);
    final var price = foodAmount * game.getStore().getFoodBowl().getPrice();
    if (isAbleToBuyProduct(price)) {
      game.buyFood(foodAmount);
      System.out.println("You've bought " + foodAmount + " Food Bowl(s). Now you have " +
          game.getPlayer().getFoodBowls() + " Food Bowl(s).");
      System.out.println("Your gold: " + game.getPlayer().getGold());

      switchState(CliGameState.Store);
      printStoreProducts();
    } else {
      System.out.println("You don't have enough gold! Choose another amount or " +
          "press 0 to go back to the Monster Store:");

    }
  }

  private Monster getMonster(String s) {
    var monsterNum = Integer.parseInt(s) - 1;
    var player = game.getPlayer();
    return player.getMonsters().get(monsterNum);
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
    var monsterToCreate = game.breeding(monster1, monster2, monsterFabric).getElements();
    Monster monster = monsterFabric.createMonster(monsterToCreate);
    game.getBreeder().setMonster(monster);
    game.getBreeder().setTimeToBreed(); //Вот это questionable, но если инициализировать вместе с
    //монстром - потом ругается на null в монстре при завершении работы потока, пусть пока тут лежит
  }

  public void printStoreProducts() {
    System.out.println("Choose one of the following products " +
        "or press 0 to exit the Monster Store:");
    for (int i = 1; i <= game.getStore().getStoreProducts().size(); i++) {
      System.out.println(i + ". " + game.getStore().getStoreProducts().get(i - 1));
    }
    System.out.println("0. Exit the Monster Store");
  }

  public void printEggs() {
    for (var i = 1; i < game.getStore().getEggsList().size() + 1; i++) {
      System.out.println(i + ". " + game.getStore().getEggsList().get(i - 1).getName());
    }
  }

  private boolean isAbleToBuyProduct(int price) {
    return game.getPlayer().getGold() >= price;
  }

  private void buyEgg(String s) {
    final var egg = game.getStore().getEggsList().get(Integer.parseInt(s) - 1);
    final var price = egg.getPrice();
    if (isAbleToBuyProduct(price)) {
      System.out.println("You've bought " + egg.getName() + "!");
      var set = new HashSet<Monster.Element>();
      set.addAll(egg.getElements());
      var monster = monsterFabric.createMonster(set);
      game.getPlayer().addMonster(monster);
      game.getPlayer().setGold(game.getPlayer().getGold() - egg.getPrice());
      System.out.println("Your gold: " + game.getPlayer().getGold());
      switchState(CliGameState.Store);
      printStoreProducts();
    } else {
      System.out.println("You don't have enough gold!");
    }
  }

  private boolean isAbleToFeed(int foodAmountToFeed) {
    var playerFoodBowls = game.getPlayer().getFoodBowls();
    return playerFoodBowls >= foodAmountToFeed;
  }
}
