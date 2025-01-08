package my.practice.monsters.model.store;

import my.practice.monsters.model.Monster;

import java.util.HashSet;

public class Egg implements Product {
  private String name;

  private int price;
  private HashSet<Monster.Element> elements;

  public HashSet<Monster.Element> getElements() {
    return elements;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getPrice() {
    return price;
  }
}
