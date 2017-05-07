package maze.maker.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import maze.maker.Main;

import static maze.maker.Main.FPS;
import static maze.maker.Main.fHeight;
import static maze.maker.Main.fWidth;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = fWidth;
        config.height = fHeight;
        config.foregroundFPS = FPS;
        config.backgroundFPS = FPS;
        new LwjglApplication(new Main(), config);
    }
}
