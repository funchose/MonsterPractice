package my.practice.monsters.model;

import java.util.HashSet;
import java.util.Random;

public class Game {
  private Player player = null;

  Breeder breeder;

  public void setBreeder(Breeder breeder) {
    this.breeder = breeder;
  }

  public Player getPlayer() {
    return player;
  }

  public Breeder getBreeder() {
    return breeder;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public void startGame() {
    MonsterBoss monsterBoss = new MonsterBoss();
    setBreeder(new Breeder((Monster monster) -> this.getPlayer().addMonster(monster)));
  }

  public Monster breeding(Monster monster1, Monster monster2, MonsterFabric monsterFabric) {
    //TODO sleep for each breeding
    HashSet<Monster.Element> elementSet = new HashSet<Monster.Element>();
    Random random = new Random();
    int randomNum = random.nextInt(1, 101);
    if (monster1.elementSet.size() == 1 && monster2.elementSet.size() == 1) {
      if (randomNum <= 40) {
        if (monster1.elementSet != monster2.elementSet) {
          elementSet.addAll(monster1.elementSet);
          elementSet.addAll(monster2.elementSet);
        }
      } else if (randomNum <= 70) {
        elementSet = monster1.elementSet;
      } else {
        elementSet = monster2.elementSet;
      }
    } else if (monster1.elementSet.size() + monster2.elementSet.size() == 3) {
      if (randomNum <= 20) {
        elementSet.addAll(monster1.elementSet);
        elementSet.addAll(monster2.elementSet);
      } else if (randomNum <= 60) {
        elementSet = monster1.elementSet;
      } else {
        elementSet = monster2.elementSet;
      }
      //А если поместить два двухэлементных монстра?
      //А если поместить два одинаковых двухэлементных монстра?
    }
    return monsterFabric.createMonster(elementSet);
  }

  public void update() throws InterruptedException {
    breeder.update();
  }
}
