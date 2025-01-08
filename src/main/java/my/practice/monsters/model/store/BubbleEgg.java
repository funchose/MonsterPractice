package my.practice.monsters.model.store;

import my.practice.monsters.model.Monster;

import java.util.HashSet;

public class BubbleEgg extends Egg{
  private final HashSet<Monster.Element> elements;
  private final String name;

  @Override
  public int getPrice() {
    return price;
  }

  private final int price;

  @Override
  public String getName() {
    return name;
  }

  public BubbleEgg() {
    this.elements = new HashSet<>();
    this.elements.add(Monster.Element.AIR);
    this.name = "Bubble Egg";
    this.price = 10;
  }

  @Override
  public HashSet<Monster.Element> getElements() {
    return elements;
  }
}
