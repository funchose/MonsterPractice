package my.practice.monsters.model;

import java.util.ArrayList;

public class Player {
  private String name;
  private int gold = 10;

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
