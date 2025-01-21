package my.practice.monsters.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Player {
  private String name;
  private int gold = 300;
  private int foodBowls = 0;

  public int getTotalVolume() {
    return totalVolume;
  }

  private int totalVolume = 0;

  public boolean isVolumeEnough() {
    return volumeEnough;
  }

  public void setVolumeEnough(boolean volumeEnough) {
    this.volumeEnough = volumeEnough;
  }

  private boolean volumeEnough = false;

  private final ArrayList<Monster> monsters = new ArrayList<>();

  public void addFoodBowls(int foodBowls) {
    this.foodBowls += foodBowls;
  }

  public Player setGold(int gold) {
    this.gold = gold;
    return this;
  }

  public int getFoodBowls() {
    return foodBowls;
  }

  public int getGold() {
    return gold;
  }

  public ArrayList<Monster> getMonsters() {
    return monsters;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Player(String name) {
    setName(name);
  }

  public void printStats() {
    System.out.println("Your gold is: " + this.gold);
  }

  public void addMonster(Monster monster) {
    this.monsters.add(monster);
  }

  public void removeFoodBowls(int foodBowls) {
    this.foodBowls -= foodBowls;
  }

  public void update() {
    int currentVolume = 0;
    for (Monster monster : monsters) {
      gold += monster.getGoldRate();
      currentVolume += monster.getVolume();
    }
    totalVolume = currentVolume;
  }

  public boolean hasWon() {
    var allMonsters = new HashSet<String>();
    allMonsters.add("Splash");
    allMonsters.add("Bubble");
    allMonsters.add("Spark");
    allMonsters.add("Breeze");
    allMonsters.add("Pyro");
    allMonsters.add("Whirl");
    allMonsters.add("Gale");
    var playerMonsters = monsters.stream().map(Monster::getType).collect(Collectors.toSet());
    boolean collectedAllMonsters = playerMonsters.equals(allMonsters);
    return collectedAllMonsters && isVolumeEnough();
  }
}
