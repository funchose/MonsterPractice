package my.practice.monsters.cli;

import java.io.IOException;

public class Launcher {
  static String currentCommand; //TODO remove static?

  public static void main(String[] args) throws IOException, InterruptedException {

    var game = new CliGame();
    Thread gameThread = new Thread(game);
    gameThread.start();

    while (true) {
      try {
        currentCommand = game.bufferedReader.readLine();
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
    //Thread.currentThread().join();  //for now - unreachable, but when the gameLoop will be interrupted?
  }
}
