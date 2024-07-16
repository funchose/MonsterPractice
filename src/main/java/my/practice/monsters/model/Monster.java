package my.practice.monsters.model;

import java.util.HashSet;
import java.util.Set;

public class Monster {
  protected int level;
  protected int foodAmount;

  public HashSet<Element> getElements() {
    return elementSet;
  }

  protected HashSet<Element> elementSet;
  protected int goldRate;
  protected int goldRateCoef;
  protected int volume;
  private final String type;

  private final int breedTime;

  public String getType() {
    return type;
  }
  public int getBreedTime() {
    return breedTime;
  }

  public Monster(int level, int foodAmount, HashSet<Element> elementSet, int goldRate, int volume,
                 String type, int breedTime) {
    this.level = level;
    this.foodAmount = foodAmount;
    this.elementSet = elementSet;
    this.goldRate = goldRate;
    this.volume = volume;
    this.type = type;
    this.breedTime = breedTime;
  }

  public int getGoldRate() {
    return level * 5;
  }

  public enum Element {
    FIRE,
    WATER,
    AIR,
    ENERGY //special for MonsterBoss
  }

  //call after each level up
  public void refresh() {
    this.goldRate = this.level * goldRateCoef;
    this.volume = level / 5 + elementSet.size();
  }
}
