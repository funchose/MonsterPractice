package my.practice.monsters.cli;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;
import my.practice.monsters.model.*;

public class CliGame implements Runnable {
  Game game;
  private final MonsterFactory monsterFactory = new MonsterFactory();
  private final AtomicReference<String> currentCommand;

  public void setCurrentState(CliGameState currentState) {
    this.currentState = currentState;
  }

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
      try {
        input = currentCommand.get();
        if (input != null && !input.isEmpty()) {
          handleInput(input);
          currentCommand.set(null);
        }
        game.update();
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("Thread was interrupted");
        Thread.currentThread().interrupt();
      } catch (IOException | NumberFormatException e) {
        System.out.println("Incorrect input");
        currentCommand.set(null);
        //throw new RuntimeException(e);
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
          System.out.printf("Have a nice game, %s! "
                  + "Go to the Store and buy your first Monster Egg!%n",
              game.getPlayer().getName());
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
          try {
            var product = getProduct(s);
            switchBetweenProducts(product);
          } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect option. Try again: ");
          }
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
          System.out.println("There is no monster with this number. "
              + "Choose again or press 0 to go to the Main Menu:");
        }
        break;
      case ChoosingMonster2:
        try {
          this.monster2 = getMonster(s);
          breedingChoice(monster1, monster2);
          System.out.printf("You are breeding %s and %s. Breeding time: %d second(s)%n",
              monster1.getType(), monster2.getType(), game.getBreeder().getTimeToBreed());
          switchState(CliGameState.Start);
          printNavigation();
        } catch (IndexOutOfBoundsException e) {
          System.out.println("There is no monster with this number. "
              + "Choose again or press 0 to go to the Main Menu:");
        }
        break;
      case ChoosingMonsterForFeeding:
        try {
          if (s.equals("0")) {
            printNavigation();
            switchState(CliGameState.Start);
          } else {
            this.monsterForFeeding = getMonster(s);
            System.out.println("How many bowls would you like to feed it? "
                + "(Press 0 to go the Main Menu)");
            switchState(CliGameState.ChoosingFoodForFeeding);
          }
        } catch (IndexOutOfBoundsException e) {
          System.out.println("There is no monster with this number. "
              + "Choose again or press 0 to go to the Main Menu:");
        }
        break;
      case ChoosingFoodForFeeding:
        if (s.equals("0")) {
          monsterForFeeding = null;
          printNavigation();
          switchState(CliGameState.Start);
        } else {
          var foodAmountToFeed = Integer.parseInt(s);
          if (!isAbleToFeed(foodAmountToFeed)) {
            System.out.println("You don't have enough food bowls! Choose another amount or "
                + "press 0 to go back to the Main Menu:");
          } else {
            game.getPlayer().removeFoodBowls(foodAmountToFeed);
            monsterForFeeding.feed(foodAmountToFeed);
            monsterForFeeding.refresh();
            System.out.println("You've fed the " + monsterForFeeding.getType()
                + "! You have " + game.getPlayer().getFoodBowls() + " Food Bowl(s) left.");
            System.out.println("Choose another amount to feed " + monsterForFeeding.getType()
                + " or press 0 to go back to the Main Menu:");
          }
        }
        break;
    }
  }

  private void switchBetweenStartCases(String s) {
    switch (s) {
      case "1":
        switchState(CliGameState.Store);
        game.getPlayer().printStats();
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
        System.out.println("Food Bowl price: " + game.getStore().getFoodBowl().getPrice()
            + ". How many Food Bowls would you like to buy?");
        switchState(CliGameState.ChoosingFoodAmountForBuying);
        break;
      case "Monster Eggs":
        System.out.println("Egg price: " + game.getStore().getEggsList().get(0).getPrice()
            + ". Choose one of the monsters eggs:");
        printEggs();
        switchState(CliGameState.ChoosingEgg);
    }
  }

  private void buyFood(String s) {
    final var foodAmount = Integer.parseInt(s);
    final var price = foodAmount * game.getStore().getFoodBowl().getPrice();
    if (isAbleToBuyProduct(price)) {
      game.buyFood(foodAmount);
      System.out.println("You've bought " + foodAmount + " Food Bowl(s). Now you have "
          + game.getPlayer().getFoodBowls() + " Food Bowl(s).");
      game.getPlayer().printStats();
      switchState(CliGameState.Store);
      printStoreProducts();
    } else {
      System.out.println("You don't have enough gold! Choose another amount or "
          + "press 0 to go back to the Monster Store:");
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
    var monsterToCreate = game.breeding(monster1, monster2, monsterFactory).getElements();
    Monster monster = monsterFactory.createMonster(monsterToCreate);
    game.getBreeder().setMonster(monster);
    game.getBreeder().setTimeToBreed();
  }

  public void printStoreProducts() {
    System.out.println("Choose one of the following products "
        + "or press 0 to exit the Monster Store:");
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
      var monster = monsterFactory.createMonster(set);
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
