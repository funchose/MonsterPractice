package my.practice.monsters.model;

import java.util.ArrayList;

public class Player {
  private String name;

  private int gold = 25;

  public void addFoodBowls(int foodBowls) {
    this.foodBowls += foodBowls;
  }

  public int getFoodBowls() {
    return foodBowls;
  }

  private int foodBowls = 0;

  public int getGold() {
    return gold;
  }

  public ArrayList<Monster> getMonsters() {
    return monsters;
  }

  private ArrayList<Monster> monsters = new ArrayList<>();

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
}
