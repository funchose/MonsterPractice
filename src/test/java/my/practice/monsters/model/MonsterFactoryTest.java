package my.practice.monsters.model;

import org.junit.jupiter.api.Assertions;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

class MonsterFactoryTest {

  @Test
  void createMonsterTest() {
    MonsterFactory fabric = new MonsterFactory();
    HashSet<Monster.Element> elements = new HashSet<>();
    elements.add(Monster.Element.WATER);
    elements.add(Monster.Element.FIRE);
    final var monster = fabric.createMonster(elements);
    Assertions.assertEquals("Pyro", monster.getType());
  }
}
