package my.practice.monsters.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Launcher {

  public static void main(String[] args) {
    var game = new CliGame();
    var gameThread = new Thread(game);
    gameThread.start();
    var bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    while (!gameThread.isInterrupted()) {
      try {
        game.setCurrentCommand(bufferedReader.readLine());
        /*
        performs this step one extra time after gameThread termination. Accepts any command.
         */
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
    System.exit(0);
  }
}
