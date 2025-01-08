package my.practice.monsters.model.store;

import my.practice.monsters.model.Monster;

import java.util.HashSet;

public class SparkEgg extends Egg{
  private final HashSet<Monster.Element> elements;
  @Override
  public int getPrice() {
    return price;
  }

  private final int price;
  @Override
  public String getName() {
    return name;
  }

  private final String name;

  public SparkEgg() {
    this.elements = new HashSet<>();
    this.elements.add(Monster.Element.FIRE);
    this.name = "Spark Egg";
    this.price = 10;
  }

  @Override
  public HashSet<Monster.Element> getElements() {
    return elements;
  }
}
