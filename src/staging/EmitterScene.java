package staging;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class EmitterScene implements ApplicationListener {
    final float WIDTH = 320;
    final float HEIGHT = 480;
    OrthographicCamera cam;
    ShapeRenderer shapeRenderer;
    SpriteBatch fontRenderer;
    ParticleEngine engine;

    @Override
    public void create() {
        cam = new OrthographicCamera(WIDTH, HEIGHT);
        cam.setToOrtho(false, WIDTH, HEIGHT);
        cam.update();

        shapeRenderer = new ShapeRenderer();
        fontRenderer = new SpriteBatch();

        shapeRenderer.setProjectionMatrix(cam.combined);
        fontRenderer.setProjectionMatrix(cam.combined);

        engine = new ParticleEngine(new Vector2(WIDTH / 2, HEIGHT / 2));
    }

    @Override
    public void resize(int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        engine.Update();
        engine.Render(shapeRenderer);
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
