package my.practice.monsters.model;

import com.google.gson.Gson;
import java.util.HashSet;
import java.util.Set;
import kotlin.NotImplementedError;

public class MonsterFactory {
  Gson gson = new Gson();
  int goldRateCoef;
  int level;
  int volume;
  String type;
  int breedTime; //in seconds

  public Monster createMonster(HashSet<Monster.Element> elements) {
    // MonsterFactory mf = this.gson.fromJson("MonstersData", MonsterFactory.class);
    var monster = new Monster(elements);
    if (elements.size() == 1) {
      level = 1;
      goldRateCoef = 1;
      volume = 1;
      breedTime = 1; //for testing

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
      goldRateCoef = 2;
      volume = 2;
      breedTime = 1; //for testing
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
      goldRateCoef = 3;
      volume = 3;
      type = "Gale";
      breedTime = 1; //for testing
    }
    monster.setLevel(1)
        .setFoodAmount(0)
        .setGoldRate(goldRateCoef)
        .setGoldRateCoef(goldRateCoef)
        .setFoodAmount(0)
        .setVolume(volume)
        .setType(type)
        .setBreedTime(breedTime);
    return monster;
  }
}
