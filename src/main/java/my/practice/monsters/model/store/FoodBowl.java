package my.practice.monsters.model.store;

public class FoodBowl implements Product {
  private final int price = 25;

  public FoodBowl() {
  }

  @Override
  public int getPrice() {
    return price;
  }

  @Override
  public String getName() {
    return "Food Bowl";
  }
}
