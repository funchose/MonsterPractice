package my.practice.monsters.cli;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class Launcher {


  public static void main(String[] args) throws IOException, InterruptedException {
    var currentCommand = new AtomicReference<String>();
    var game = new CliGame(currentCommand);
    var gameThread = new Thread(game);
    gameThread.start();

    while (true) {
      try {
        currentCommand.set(game.bufferedReader.readLine());
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
    //Thread.currentThread().join();  //for now - unreachable, but when the gameLoop will be interrupted?
  }
}
