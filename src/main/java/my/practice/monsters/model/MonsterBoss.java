package my.practice.monsters.model;

public class MonsterBoss {
  boolean isAsleep;

  private final Monster.Element element;

  public Monster.Element getElement() {
    return element;
  }

  public MonsterBoss() {
    isAsleep = true;
    element = Monster.Element.ENERGY;
  }
}
