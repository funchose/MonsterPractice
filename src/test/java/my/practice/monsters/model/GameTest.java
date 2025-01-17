package my.practice.monsters.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class GameTest {
  MonsterFactory monsterFactory = new MonsterFactory();
  Game game = new Game();

  @Test
  public void breedingTest() {
    HashSet<Monster.Element> elements1 = new HashSet<>();
    elements1.add(Monster.Element.FIRE);
    elements1.add(Monster.Element.AIR);
    HashSet<Monster.Element> elements2 = new HashSet<>();
    elements2.add(Monster.Element.FIRE);
    elements2.add(Monster.Element.WATER);
    Monster monster1 = new MonsterFactory().createMonster(elements1);
    Monster monster2 = new MonsterFactory().createMonster(elements2);
    Monster monster3 = game.breeding(monster1, monster2, monsterFactory);
    System.out.println(monster1.getType() + " + " + monster2.getType()
        + " = " + monster3.getType());
  }
}
