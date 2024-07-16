package my.practice.monsters.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

class MonsterFabricTest {

  @Test
  void createMonsterTest() {
    MonsterFabric fabric = new MonsterFabric();
    HashSet<Monster.Element> elements = new HashSet<>();
    elements.add(Monster.Element.WATER);
    elements.add(Monster.Element.FIRE);
    final var monster = fabric.createMonster(elements);
    Assertions.assertEquals("Pyro", monster.getType());
  }
}
