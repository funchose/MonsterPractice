package my.practice.monsters.model;

import org.junit.jupiter.api.Assertions;
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

  @Test
  public void playerWinningTest() {
    HashSet<Monster.Element> elements1 = new HashSet<>();
    elements1.add(Monster.Element.FIRE);
    HashSet<Monster.Element> elements2 = new HashSet<>();
    elements2.add(Monster.Element.AIR);
    HashSet<Monster.Element> elements3 = new HashSet<>();
    elements3.add(Monster.Element.WATER);
    HashSet<Monster.Element> elements4 = new HashSet<>();
    elements4.add(Monster.Element.FIRE);
    elements4.add(Monster.Element.AIR);
    HashSet<Monster.Element> elements5 = new HashSet<>();
    elements5.add(Monster.Element.FIRE);
    elements5.add(Monster.Element.WATER);
    HashSet<Monster.Element> elements6 = new HashSet<>();
    elements6.add(Monster.Element.AIR);
    elements6.add(Monster.Element.WATER);
    HashSet<Monster.Element> elements7 = new HashSet<>();
    elements7.add(Monster.Element.AIR);
    elements7.add(Monster.Element.WATER);
    elements7.add(Monster.Element.FIRE);
    var monsterFactory = new MonsterFactory();
    var monster1 = monsterFactory.createMonster(elements1);
    var monster2 = monsterFactory.createMonster(elements2);
    var monster3 = monsterFactory.createMonster(elements3);
    var monster4 = monsterFactory.createMonster(elements4);
    var monster5 = monsterFactory.createMonster(elements5);
    var monster6 = monsterFactory.createMonster(elements6);
    var monster7 = monsterFactory.createMonster(elements7);
    var player = new Player("TestName");
    player.addMonster(monster1);
    player.addMonster(monster2);
    player.addMonster(monster3);
    player.addMonster(monster4);
    player.addMonster(monster5);
    player.addMonster(monster6);
    player.addMonster(monster7);
    for (Monster monster : player.getMonsters()) {
      System.out.println(monster.getType());
    }
    Assertions.assertTrue(player.hasWon());
  }
}
