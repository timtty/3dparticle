package staging;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class EmitterEngine {
    public Vector2 startPoint;
    public Array<Particle> particles;
    public boolean burntOut = false;

    public EmitterEngine(Vector2 origin) {
        startPoint = origin;
        particles = new Array<Particle>();
        Random random = new Random();

        for (int i = 0; i < 25; i++) {
            Vector2 start = new Vector2(startPoint.x + random.nextFloat(), startPoint.y);
            particles.add(new Particle(start, 1.2f));
        }
    }

    public void Update() {
        boolean allLanded = true;
        for (int i = 0; i < particles.size; i++) {
            Particle particle = particles.get(i);
            particle.Update();
            if (!(particle.position.x <= 0)) allLanded = false;
        }

        if (allLanded) burntOut = true;
    }
}
