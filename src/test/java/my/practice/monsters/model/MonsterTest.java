package my.practice.monsters.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class MonsterTest {
  @Test
  public void feedingTwoElemMonsterTest() {
    HashSet<Monster.Element> elements = new HashSet<>();
    elements.add(Monster.Element.FIRE);
    elements.add(Monster.Element.AIR);
    var monsterFactory = new MonsterFactory();
    var monster = monsterFactory.createMonster(elements);
    monster.feed(200);
    Assertions.assertEquals(14, monster.getLevel());
    Assertions.assertEquals(18, monster.getFoodAmount());
  }

  @Test
  public void feedingThreeElemMonsterTest() {
    HashSet<Monster.Element> elements = new HashSet<>();
    elements.add(Monster.Element.FIRE);
    elements.add(Monster.Element.AIR);
    elements.add(Monster.Element.WATER);
    var monsterFactory = new MonsterFactory();
    var monster = monsterFactory.createMonster(elements);
    monster.feed(134);
    Assertions.assertEquals(9, monster.getLevel());
  }

  @Test
  public void volumeUpTest1() {
    HashSet<Monster.Element> elements = new HashSet<>();
    elements.add(Monster.Element.FIRE);
    elements.add(Monster.Element.WATER);
    var monsterFactory = new MonsterFactory();
    var monster = monsterFactory.createMonster(elements);
    monster.feed(1000);
    monster.refresh();
    Assertions.assertEquals(5, monster.getVolume());
  }

  @Test
  public void volumeUpTest2() {
    HashSet<Monster.Element> elements = new HashSet<>();
    elements.add(Monster.Element.FIRE);
    var monsterFactory = new MonsterFactory();
    var monster = monsterFactory.createMonster(elements);
    monster.feed(100);
    monster.refresh();
    Assertions.assertEquals(14, monster.getLevel());
    Assertions.assertEquals(3, monster.getVolume());
  }
}
