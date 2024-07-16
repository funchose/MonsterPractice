package my.practice.monsters.model;

public class Breeder {

  private Monster monster;
  private final int sleepTime = 1; //in seconds

  Callback callback; // = (Monster monster) -> game.getPlayer().addMonster(this.monster);

  public void setMonster(Monster monster) {
    this.monster = monster;
  }

  public Breeder(Callback callback) {
    this.callback = callback;
  }

  public void setTimeToBreed() {
    this.timeToBreed = this.monster.getBreedTime();
  }

  private int timeToBreed;

  public void update() throws InterruptedException {
    if (this.monster != null) {
      timeToBreed -= sleepTime;
      if (timeToBreed <= 0) {
        callback.onReady(this.monster);
        System.out.println("New monster is added!");
        setMonster(null);
      }
    }
  }
}
