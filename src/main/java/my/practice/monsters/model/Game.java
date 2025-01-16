package my.practice.monsters.model;

import my.practice.monsters.model.store.Store;

import java.util.HashSet;
import java.util.Random;

public class Game {
  private Player player = null;

  Store store;

  Breeder breeder;

  public void setPlayer(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }

  public Store getStore() {
    return store;
  }

  public void setStore(Store store) {
    this.store = store;
  }

  public void setBreeder(Breeder breeder) {
    this.breeder = breeder;
  }

  public Breeder getBreeder() {
    return breeder;
  }

  public void startGame() {
    MonsterBoss monsterBoss = new MonsterBoss();
    setStore(new Store());
    setBreeder(new Breeder((Monster monster) -> this.getPlayer().addMonster(monster)));
  }

  public Monster breeding(Monster monster1, Monster monster2, MonsterFactory monsterFactory) {
    var elementSet = new HashSet<Monster.Element>();
    Random random = new Random();
    int randomNum = random.nextInt(1, 101);
    if (monster1.elementSet.equals(monster2.elementSet)) {
      elementSet.addAll(monster1.elementSet);
    } else if (monster1.elementSet.size() == 1 && monster2.elementSet.size() == 1) {
      if (randomNum <= 40) {
        elementSet.addAll(monster1.elementSet);
        elementSet.addAll(monster2.elementSet);
      } else if (randomNum <= 70) {
        elementSet = monster1.elementSet;
      } else {
        elementSet = monster2.elementSet;
      }
    } else if (monster1.elementSet.size() + monster2.elementSet.size() == 3) {
      if (randomNum <= 20) {
        elementSet.addAll(monster1.elementSet);
        elementSet.addAll(monster2.elementSet);
      } else if (randomNum <= 60) {
        elementSet = monster1.elementSet;
      } else {
        elementSet = monster2.elementSet;
      }
    } else if (monster1.elementSet.size() == 2 && monster2.elementSet.size() == 2) {
      if (randomNum <= 50) {
        elementSet.addAll(monster1.elementSet);
      } else {
        elementSet.addAll(monster2.elementSet);
      }
    }
    return monsterFactory.createMonster(elementSet);
  }

  public void update() throws InterruptedException {
    breeder.update();
  }

  public void buyFood(int foodAmount) {
    player.addFoodBowls(foodAmount);
    final var newGold = player.getGold() - store.getFoodBowl().getPrice();
    player.setGold(newGold);
  }
}
