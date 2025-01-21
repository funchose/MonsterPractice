package my.practice.monsters.model;

public class MonsterBoss {
  boolean isAwake;

  private final Monster.Element element;

  public Monster.Element getElement() {
    return element;
  }

  public MonsterBoss() {
    isAwake = false;
    element = Monster.Element.ENERGY;
  }

  public void wakeUp() {
    isAwake = true;
    System.out.println("Monster Boss is finally awake! Congratulations! You won!");
    //TODO check that it appears only once
  }
}
