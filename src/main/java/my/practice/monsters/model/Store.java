package my.practice.monsters.model;

import java.util.ArrayList;
import java.util.List;

public class Store {
  public List<Product> storeProducts;

  public List<Product> getStoreProducts() {
    return this.storeProducts;
  }
  //TODO Поправить названия методов и полей после реализации парсинга JSON

  public Store() {
    this.storeProducts = new ArrayList<>();
    storeProducts.add(0, Product.FoodBowl);
    storeProducts.add(1, Product.SparkEgg);
    storeProducts.add(2, Product.SplashEgg);
    storeProducts.add(3, Product.BubbleEgg);
  }
}
