package my.practice.monsters.model;

import java.util.HashSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    System.out.printf("%s + %s = %s%n", monster1.getType(), monster2.getType(), monster3.getType());
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
    var monster11 = monsterFactory.createMonster(elements1);
    var monster12 = monsterFactory.createMonster(elements1);
    var monster21 = monsterFactory.createMonster(elements2);
    var monster22 = monsterFactory.createMonster(elements2);
    var monster23 = monsterFactory.createMonster(elements2);
    var monster24 = monsterFactory.createMonster(elements2);
    var monster25 = monsterFactory.createMonster(elements2);
    var monster31 = monsterFactory.createMonster(elements3);
    var monster32 = monsterFactory.createMonster(elements3);
    var monster33 = monsterFactory.createMonster(elements3);
    var monster34 = monsterFactory.createMonster(elements3);
    var monster35 = monsterFactory.createMonster(elements3);
    var monster36 = monsterFactory.createMonster(elements3);
    var monster4 = monsterFactory.createMonster(elements4);
    var monster5 = monsterFactory.createMonster(elements5);
    var monster6 = monsterFactory.createMonster(elements6);
    var monster7 = monsterFactory.createMonster(elements7);
    var player = new Player("TestName");
    player.addMonster(monster11);
    player.addMonster(monster12);
    player.addMonster(monster21);
    player.addMonster(monster22);
    player.addMonster(monster23);
    player.addMonster(monster24);
    player.addMonster(monster25);
    player.addMonster(monster31);
    player.addMonster(monster32);
    player.addMonster(monster33);
    player.addMonster(monster34);
    player.addMonster(monster35);
    player.addMonster(monster36);
    player.addMonster(monster4);
    player.addMonster(monster5);
    player.addMonster(monster6);
    player.addMonster(monster7);
    var game = new Game();
    game.setPlayer(player);
    for (Monster monster : player.getMonsters()) {
      System.out.println(monster.getType());
    }
    Assertions.assertFalse(player.hasWon());
    player.update();
    System.out.println(player.getTotalVolume());
    game.isVolumeEnough();
    Assertions.assertTrue(player.hasWon());
  }
}
