package my.practice.monsters.model;

public class Store {
  private final int foodPrice = 10;
  private int splashEggPrice = 10;
  private int bubbleEggPrice = 10;
  private int sparkEggPrice = 10;

  public int getFoodPrice() {
    return foodPrice;
  }

  public int getSplashEggPrice() {
    return splashEggPrice;
  }

  public int getBubbleEggPrice() {
    return bubbleEggPrice;
  }

  public int getSparkEggPrice() {
    return sparkEggPrice;
  }

  public void setSplashEggPrice(int splashEggPrice) {
    this.splashEggPrice = splashEggPrice;
  } //?

  public void setBubbleEggPrice(int bubbleEggPrice) {
    this.bubbleEggPrice = bubbleEggPrice;
  } //?

  public void setSparkEggPrice(int sparkEggPrice) {
    this.sparkEggPrice = sparkEggPrice;
  } //?
}
