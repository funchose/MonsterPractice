package my.practice.monsters;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class MonstersApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("My Practice Monsters");
    }

    public static void main(String[] args) {
        launch(args);
    }
}