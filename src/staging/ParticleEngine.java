package staging;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class ParticleEngine {
    public Array<Particle> particles;
    Vector2 emitterPoint;

    public ParticleEngine(Vector2 point) {
        emitterPoint = point;
        particles = new Array<Particle>();
    }

    public void Update() {
        if (particles.size < 10000) {
            Random random = new Random();
            Vector2 position = new Vector2(emitterPoint);
            float dist = random.nextFloat() * 1.6f;
            position.x += (random.nextBoolean()) ? -dist : +dist;
            particles.add(new Particle(position, 1.4f));
        }

        for (int i = 0; i < particles.size; i++) {
            particles.get(i).Update();
            if (particles.get(i).duration > 9) particles.get(i).color.a -= 0.1f;
            if (particles.get(i).color.a < 0) particles.removeIndex(i);
        }
    }

    public void Render(ShapeRenderer renderer) {
        for (Particle particle: particles) {
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(particle.color);
            renderer.circle(particle.position.x, particle.position.y, particle.bounds.radius);
            renderer.end();
        }
    }
 }
