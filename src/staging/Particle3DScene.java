package staging;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class Particle3DScene implements ApplicationListener {
    PerspectiveCamera cam;
    Lights lights;
    ModelBatch modelRenderer;
    ParticleEngine3D engine;

    final float WIDTH = 320;
    final float HEIGHT = 480;


    @Override
    public void create() {
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 1f);
        cam.lookAt(0, 0, 0);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.update();

        lights = new Lights();
        lights.ambientLight.set(0.4f, 0.4f, 0.4f, 1f);
        lights.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelRenderer = new ModelBatch();

        engine = new ParticleEngine3D(new Vector3(WIDTH / 2, HEIGHT / 2, 0));
    }

    @Override
    public void resize(int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.4f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        engine.Update();
        cam.update();


        modelRenderer.begin(cam);

        for (Particle3D particle3D: engine.particles) {
            modelRenderer.render(particle3D.instance, lights);
        }

        modelRenderer.end();

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
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
