package staging;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.materials.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class LoadModelTest implements ApplicationListener {
    PerspectiveCamera cam;
    ModelBatch batch;
    Model model;
    ModelInstance modelInstance;

    Lights lights;

    Vector3 tmp = new Vector3();
    final Vector3 origin = new Vector3(0, 0, 0);

    Vector3 translation = new Vector3(0, 0, 0);
    int rotation = 0;

    @Override
    public void create() {
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(2f, 2f, 2f);
        cam.lookAt(0, 0, 0);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.update();

        batch = new ModelBatch();

        lights = new Lights();
        lights.ambientLight.set(0.4f, 0.4f, 0.4f, 1f);
        lights.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        ModelLoader loader = new ObjLoader();
        model = loader.loadModel(Gdx.files.internal("ship/ship.obj"));
        modelInstance = new ModelInstance(model);
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
        translation.y += 0.005f;

        modelInstance.transform.setToTranslation(translation);
        */

        //translation.y += 0.005;
        rotation += 1;

        Random random = new Random();

        modelInstance.transform.setToRotation(new Vector3(random.nextFloat(), random.nextFloat(), random.nextFloat()), rotation);





        batch.begin(cam);
        batch.render(modelInstance, lights);
        batch.end();

        /*
        tmp.set(cam.up);
        cam.rotateAround(origin, tmp, 1f);
        tmp.crs(cam.position);
        cam.rotateAround(origin, tmp, 1f);
        cam.update();
        */

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
        batch.dispose();
        model.dispose();
    }
}