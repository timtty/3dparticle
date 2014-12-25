package staging;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class LoadModelTest implements ApplicationListener {
    PerspectiveCamera cam;
    ModelBatch batch;
    Model model;
    ModelInstance modelInstance;
    Environment environment;

    BaseLight lights;

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

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        /*
        translation.y += 0.005f;

        modelInstance.transform.setToTranslation(translation);
        */

        //translation.y += 0.005;
        rotation += 1;

        Random random = new Random();

        modelInstance.transform.setToRotation(new Vector3(random.nextFloat(), random.nextFloat(), random.nextFloat()), rotation);





        batch.begin(cam);
        batch.render(modelInstance, environment);
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