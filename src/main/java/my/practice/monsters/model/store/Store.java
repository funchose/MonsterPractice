package my.practice.monsters.model.store;

import java.util.ArrayList;
import java.util.List;

public class Store {
  private final List<String> storeProducts;

  private final List<Egg> eggsList;

  private final FoodBowl foodBowl;

  public Store() {
    this.storeProducts = new ArrayList<>();
    storeProducts.add(0, "Food");
    storeProducts.add(1, "Monster Eggs");
    eggsList = new ArrayList<>();
    eggsList.add(0, new SplashEgg());
    eggsList.add(1, new SparkEgg());
    eggsList.add(2, new BubbleEgg());
    foodBowl = new FoodBowl();
  }

  public FoodBowl getFoodBowl() {
    return foodBowl;
  }

  public List<String> getStoreProducts() {
    return this.storeProducts;
  }

  public List<Egg> getEggsList() {
    return eggsList;
  }
}
