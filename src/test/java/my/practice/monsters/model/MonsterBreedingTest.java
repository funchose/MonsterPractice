package my.practice.monsters.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class MonsterBreedingTest {
  MonsterFabric monsterFabric = new MonsterFabric();
  Game game = new Game();

  @Test
  public void breedingTest() {
    HashSet<Monster.Element> elements1 = new HashSet<>();
    elements1.add(Monster.Element.WATER);
    HashSet<Monster.Element> elements2 = new HashSet<>();
    elements2.add(Monster.Element.FIRE);
    elements2.add(Monster.Element.AIR);
    Monster monster1 = new MonsterFabric().createMonster(elements1);
    Monster monster2 = new MonsterFabric().createMonster(elements2);
    Monster monster3 = game.breeding(monster1, monster2, monsterFabric);
    System.out.println(monster3.getType());
  }
}
