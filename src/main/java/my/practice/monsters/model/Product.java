package my.practice.monsters.model;

public enum Product {
  FoodBowl("Food Bowl", 25),
  SparkEgg("Spark Egg", 10),
  SplashEgg("Splash Egg", 10),
  BubbleEgg("Bubble Egg", 10);
  public final String name;
  public final int price;

  Product(String name, int price) {
    this.name = name;
    this.price = price;
  }
}
