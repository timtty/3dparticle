package staging;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Particle3DSystem implements ApplicationListener {
    PerspectiveCamera cam;
    ModelBatch renderer;
    ModelBuilder builder;
    Lights lights;
    ArrayList<Particle3D> particles;
    int EMITTER_SPEED = 60;

    final Vector3 ORIGIN = new Vector3(0, 0, 0);
    float SIZE = 0.93f;

    final Particle3D.SHAPE_TYPES shape = Particle3D.SHAPE_TYPES.SPHERE;

    Logger logger = Logger.getLogger(this.getClass().getName());


    public void create() {
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0, 0, 30f);
        cam.lookAt(ORIGIN);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.update();

        renderer = new ModelBatch();

        lights = new Lights();
        lights.ambientLight.set(0.4f, 0.4f, 0.4f, 1f);
        lights.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        builder = new ModelBuilder();
        particles = new ArrayList<Particle3D>();

        particles.add(new Particle3D(ORIGIN, SIZE, builder, Particle3D.SHAPE_TYPES.SPHERE));
    }

    @Override
    public void resize(int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        /*
        cam.rotateAround(ORIGIN, cam.up, 1f);
        cam.update();
        */

        for (int i = 0; i < particles.size(); i++) {
            Particle3D particle = particles.get(i);
            //logger.log(Level.INFO, "" + particle.X_PULL);
            if (particle.duration < 5f) {
                particle.Update();

                lights.add(particle.light);

                renderer.begin(cam);
                renderer.render(particle.instance, lights);
                renderer.end();
            } else {
                particle.destroy();
                particle = null;
                particles.remove(i);
            }
        }

        SIZE = (new Random().nextFloat() / 2);

        if (particles.size() < 500) {
            for (int ii = 0; ii < 22; ii++) {
                particles.add(new Particle3D(ORIGIN, SIZE, builder, shape));
            }
        } else {
            for (int ii = 0; ii < 16; ii++) {
                particles.add(new Particle3D(ORIGIN, SIZE, builder, shape));
            }
        }

        /*
        EMITTER_SPEED += (new Random().nextBoolean()) ? -20 : 20;
        if (EMITTER_SPEED < 0) EMITTER_SPEED = 0; if (EMITTER_SPEED > 100) EMITTER_SPEED = 100;
        */

        // logger.log(Level.INFO, "Number of particles: " + particles.size());

    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose() {
        renderer.dispose();

        for (Particle3D instance: particles) {
            instance.instance.model.dispose();
        }
    }
}