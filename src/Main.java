import com.badlogic.gdx.backends.lwjgl.*;
import staging.LightsTest;
import staging.Particle3DSystem;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "GAME";
        cfg.width = 1600;
        cfg.height = 900;

        new LwjglApplication(new Particle3DSystem(), cfg);
    }
}