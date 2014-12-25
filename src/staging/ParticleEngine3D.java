package staging;

import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class ParticleEngine3D {
    public Array<Particle3D> particles;
    Vector3 emitterPoint;
    ModelBuilder modelBuilder;

    public ParticleEngine3D(Vector3 point) {
        modelBuilder = new ModelBuilder();
        emitterPoint = point;
        particles = new Array<Particle3D>();
    }

    public void Update() {
        if (particles.size < 10000) {
            Random random = new Random();
            Vector3 position = new Vector3(emitterPoint);
            float dist = random.nextFloat() * 1.6f;
            position.x += (random.nextBoolean()) ? -dist : +dist;
            particles.add(new Particle3D(position, 1.4f, modelBuilder));
        }

        for (int i = 0; i < particles.size; i++) {
            particles.get(i).Update();
            if (particles.get(i).duration > 9) particles.get(i).color.a -= 0.1f;
            if (particles.get(i).color.a < 0) particles.removeIndex(i);
        }
    }
 }
