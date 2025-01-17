package my.practice.monsters.model.store;

import my.practice.monsters.model.Monster;

import java.util.HashSet;

public class SparkEgg extends Egg{
  private final String name;
  private final HashSet<Monster.Element> elements;
  @Override
  public String getName() {
    return name;
  }

  public SparkEgg() {
    this.elements = new HashSet<>();
    this.elements.add(Monster.Element.FIRE);
    this.name = "Spark Egg";
  }

  @Override
  public HashSet<Monster.Element> getElements() {
    return elements;
  }
}
