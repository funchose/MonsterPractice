package my.practice.monsters.model.store;

import java.util.HashSet;
import my.practice.monsters.model.Monster;

public abstract class Egg implements Product {
  private String name;
  protected final int price = 100;

  public abstract HashSet<Monster.Element> getElements();

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getPrice() {
    return this.price;
  }
}
