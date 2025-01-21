package my.practice.monsters.model.store;

import java.util.HashSet;
import my.practice.monsters.model.Monster;

public class BubbleEgg extends Egg {
  private final String name;
  private final HashSet<Monster.Element> elements;

  @Override
  public String getName() {
    return name;
  }

  public BubbleEgg() {
    this.elements = new HashSet<>();
    this.elements.add(Monster.Element.AIR);
    this.name = "Bubble Egg";
  }

  @Override
  public HashSet<Monster.Element> getElements() {
    return elements;
  }
}
