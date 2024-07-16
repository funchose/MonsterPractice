package my.practice.monsters.model;

import com.google.gson.Gson;
import kotlin.NotImplementedError;

import java.util.HashSet;
import java.util.Set;

public class MonsterFabric {
  Gson gson = new Gson();
  int goldRate;
  int level;
  int volume;
  String type;
  int breedTime; //in seconds


  public MonsterFabric setGoldRate(int goldRate) {
    this.goldRate = goldRate;
    return this;
  }

  public MonsterFabric setLevel(int level) {
    this.level = level;
    return this;
  }

  public MonsterFabric setVolume(int volume) {
    this.volume = volume;
    return this;
  }

  public MonsterFabric setType(String type) {
    this.type = type;
    return this;
  }

  public MonsterFabric setBreedTime(int breedTime) {
    this.breedTime = breedTime;
    return this;
  }

  public Monster createMonster(HashSet<Monster.Element> elements) {
    // MonsterFabric mf = this.gson.fromJson("MonstersData", MonsterFabric.class);
    if (elements.size() == 1) {
      level = 1;
      goldRate = level * 5;
      volume = 1;
      breedTime = 10; //for testing

      if (elements.equals(Set.of(Monster.Element.WATER))) {
        type = "Splash";
      } else if (elements.equals(Set.of(Monster.Element.AIR))) {
        type = "Bubble";
      } else if (elements.equals(Set.of(Monster.Element.FIRE))) {
        type = "Spark";
      } else {
        throw new NotImplementedError();
      }

    } else if (elements.size() == 2) {
      level = 1;
      goldRate = level * 10;
      volume = 2;
      breedTime = 10; //for testing
      if (elements.equals(Set.of(Monster.Element.WATER, Monster.Element.AIR))) {
        type = "Breeze";
      } else if (elements.equals(Set.of(Monster.Element.WATER, Monster.Element.FIRE))) {
        type = "Pyro";
      } else if (elements.equals(Set.of(Monster.Element.AIR, Monster.Element.FIRE))) {
        type = "Whirl";
      } else {
        throw new NotImplementedError();
      }
    } else {
      level = 1;
      goldRate = level * 15;
      volume = 3;
      type = "Gale";
      breedTime = 7200;
    }
    return new Monster(
        1, 0, elements, goldRate, volume, type, breedTime
    );
  }
}
