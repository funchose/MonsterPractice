package my.practice.monsters.model;

import java.util.HashSet;

public class Monster {
  private int level;
  private int foodAmount;
  private final HashSet<Element> elementSet;
  private int goldRate;
  private int goldRateCoef;
  private int volume;
  private String type;
  private int breedTime;

  public int getGoldRateCoef() {
    return goldRateCoef;
  }

  public int getFoodAmount() {
    return foodAmount;
  }

  public int getLevel() {
    return level;
  }

  public int getGoldRate() {
    return goldRate;
  }

  public int getVolume() {
    return volume;
  }

  public Monster setLevel(int level) {
    this.level = level;
    return this;
  }

  public Monster setFoodAmount(int foodAmount) {
    this.foodAmount = foodAmount;
    return this;
  }

  public Monster setGoldRate(int goldRate) {
    this.goldRate = goldRate;
    return this;
  }

  public Monster setGoldRateCoef(int goldRateCoef) {
    this.goldRateCoef = goldRateCoef;
    return this;
  }

  public Monster setVolume(int volume) {
    this.volume = volume;
    return this;
  }

  public Monster setType(String type) {
    this.type = type;
    return this;
  }

  public Monster setBreedTime(int breedTime) {
    this.breedTime = breedTime;
    return this;
  }

  public Monster(HashSet<Element> elementSet) {
    this.elementSet = elementSet;
  }

  public String getType() {
    return type;
  }

  public HashSet<Element> getElements() {
    return elementSet;
  }

  public int getBreedTime() {
    return breedTime;
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
    if (this.volume > 5) {
      this.volume = 5;
    }
  }

  public void feed(int foodAmountToFeed) {
    int levelSum = level;
    int reachedLevel = level;
    int foodPerElement = foodAmountToFeed / elementSet.size();
    while (foodPerElement >= reachedLevel) {
      foodPerElement -= reachedLevel;
      reachedLevel++;
      levelSum += reachedLevel;
    }
    level = reachedLevel;
    foodAmount = foodAmountToFeed - elementSet.size() * (levelSum - level);
  }
}
