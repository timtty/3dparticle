package staging;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.FloatAction;
import utils.RandomColor;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Particle3DSystem implements ApplicationListener {
    PerspectiveCamera cam;
    ModelBatch renderer;
    ModelBuilder builder;
    Environment environment;
    ArrayList<Particle3D> particles;
    int EMITTER_SPEED = 60;

    final Vector3 ORIGIN = new Vector3(0, 0, 0);
    float SIZE = 0.93f;

    final Particle3D.SHAPE_TYPES shape = Particle3D.SHAPE_TYPES.SPHERE;

    Logger logger = Logger.getLogger(this.getClass().getName());

    private int flip(int i) {
        int out = 0;
        switch (i) {
            case 0:
                out = 9;
                break;
            case 1:
                out = 9;
                break;
            case 2:
                out = 8;
                break;
            case 3:
                out = 7;
                break;
            case 4:
                out = 6;
                break;
            case 5:
                out = 5;
                break;
            case 6:
                out = 4;
                break;
            case 7:
                out = 3;
                break;
            case 8:
                out = 2;
                break;
            default:
                out = 1;

        }

        return out;
    }

    public void create() {
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0, 0, 30f);
        cam.lookAt(ORIGIN);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.update();

        renderer = new ModelBatch();


        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.4f, 0.2f, 1f));
        environment.add(new DirectionalLight().set(12f, 12f, 12f, -0.1f, -0.1f, -3f));

        builder = new ModelBuilder();
        particles = new ArrayList<Particle3D>();

        particles.add(new Particle3D(ORIGIN, SIZE, shape));
    }

    @Override
    public void resize(int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        /*
        cam.rotateAround(ORIGIN, cam.up, 1f);
        cam.update();
        */

        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).duration < 5f) {
                particles.get(i).Update();

                //environment.add(particle.light);

                renderer.begin(cam);
                renderer.render(particles.get(i).instance, environment);

                for (int ii = 0; ii < particles.get(i).trails.length; ii++) {
                    Trail trail = particles.get(i).trails[ii];
                    if (trail != null) {
                        Float alpha = (flip(ii) * 0.1f);
                        trail.trail.materials.get(0).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, alpha));
                        renderer.render(trail.trail, environment);
                    }
                }

                renderer.end();
            } else {
                particles.get(i).destroy();
                particles.remove(i);
            }
        }

        SIZE = (new Random().nextFloat() / 4);

        if (particles.size() < 1500) {
            for (int ii = 0; ii < 20; ii++) {
                particles.add(new Particle3D(ORIGIN, SIZE, shape));
            }
        } else {
            for (int ii = 0; ii < 10; ii++) {
                particles.add(new Particle3D(ORIGIN, SIZE, shape));
            }
        }

        /*
        EMITTER_SPEED += (new Random().nextBoolean()) ? -20 : 20;
        if (EMITTER_SPEED < 0) EMITTER_SPEED = 0; if (EMITTER_SPEED > 100) EMITTER_SPEED = 100;
        */

        logger.log(Level.INFO, "" + particles.size());

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