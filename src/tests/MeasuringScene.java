package tests;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import measurements.ScreenPoint;
import staging.EmitterEngine;
import staging.Particle;

import java.util.HashMap;
import java.util.Map;

public class MeasuringScene implements ApplicationListener {
    public enum LOCATIONS {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, CENTER, TOUCH_POINT
    }
    public float SCREEN_HEIGHT;
    public float SCREEN_WIDTH;
    public OrthographicCamera cam;
    public ShapeRenderer renderer;
    public SpriteBatch spriteRender;
    public Map<LOCATIONS, ScreenPoint> screenPoints;
    public Array<EmitterEngine> emitterEngines;

    private void Clear() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void Gravity(ScreenPoint point) {
        if (point != null) point.position.y -= 3.5f;
    }

    public void RenderPoints() {
        Clear();

        for (Map.Entry<LOCATIONS, ScreenPoint> pointEntry: screenPoints.entrySet()) {
            ScreenPoint point = pointEntry.getValue();
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(point.color);
            renderer.circle(point.position.x, point.position.y, point.bounds.radius);
            renderer.end();

            spriteRender.begin();
            BitmapFont font = new BitmapFont();
            font.scale(-0.2f);
            Vector2 position;
            String textValue = "point: " + pointEntry.getKey().toString();
            float textLength = font.getBounds(textValue).width;

            if (pointEntry.getKey().equals(LOCATIONS.BOTTOM_LEFT)) {
                position = new Vector2(point.position.x + 12, point.position.y + 12);
            } else if (pointEntry.getKey().equals(LOCATIONS.BOTTOM_RIGHT)) {
                position = new Vector2(point.position.x - (textLength + 12), point.position.y + 12);
            } else if (pointEntry.getKey().equals(LOCATIONS.TOP_LEFT)) {
                position = new Vector2(point.position.x + 12, point.position.y - 10);
            } else if (pointEntry.getKey().equals(LOCATIONS.TOP_RIGHT)) {
                position = new Vector2(point.position.x - (textLength + 12), point.position.y - 10);
            } else {
                position = new Vector2(point.position.x + 12, point.position.y - 2);
            }

            font.draw(spriteRender, "Point: " + pointEntry.getKey().toString(), position.x, position.y);
            font.draw(spriteRender, "delta=" + Gdx.graphics.getDeltaTime(), 50, 50);
            spriteRender.end();



            if (pointEntry.getKey().equals(LOCATIONS.TOUCH_POINT)) {
                pointEntry.getValue().duration += Gdx.graphics.getDeltaTime();
            }


        }

        for (int i = 0; i < emitterEngines.size; i++) {
            for (Particle particle: emitterEngines.get(i).particles) {
                renderer.begin(ShapeRenderer.ShapeType.Filled);
                renderer.setColor(particle.color);
                renderer.circle(particle.position.x, particle.position.y, particle.bounds.radius);
                renderer.end();
            }

            emitterEngines.get(i).Update();
            if (emitterEngines.get(i).burntOut) emitterEngines.removeIndex(i);
        }

        if (Gdx.input.isTouched()) {
            Vector3 touchPoint = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPoint);

            emitterEngines.add(new EmitterEngine(new Vector2(touchPoint.x, touchPoint.y)));

            if (screenPoints.containsKey(LOCATIONS.TOUCH_POINT)) {
                ScreenPoint pointRef = screenPoints.get(LOCATIONS.TOUCH_POINT);
                pointRef.position.x = touchPoint.x;
                pointRef.position.y = touchPoint.y;
            } else {
                screenPoints.put(LOCATIONS.TOUCH_POINT, new ScreenPoint(new Vector2(touchPoint.x, touchPoint.y),
                        new Color(1, 0, 0, 1)));
            }
        }

        if (screenPoints.containsKey(LOCATIONS.TOUCH_POINT)) {
            ScreenPoint point = screenPoints.get(LOCATIONS.TOUCH_POINT);
            if (point.duration >= 8) {
                point.color.a -= 0.1f;
            }
            if (point.color.a <= 0) {
                screenPoints.remove(LOCATIONS.TOUCH_POINT);
            }
        }
    }

    @Override
    public void create() {
        //SCREEN_HEIGHT = Gdx.graphics.getHeight();
        //SCREEN_WIDTH = Gdx.graphics.getWidth();

        SCREEN_WIDTH = 800;
        SCREEN_HEIGHT = 400;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 800, 480);
        cam.update();

        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(cam.combined);

        spriteRender = new SpriteBatch();
        spriteRender.setProjectionMatrix(cam.combined);

        screenPoints = new HashMap<LOCATIONS, ScreenPoint>();
        screenPoints.put(LOCATIONS.BOTTOM_LEFT, new ScreenPoint(new Vector2(0, 0)));
        screenPoints.put(LOCATIONS.BOTTOM_RIGHT, new ScreenPoint(new Vector2(SCREEN_WIDTH, 0)));
        screenPoints.put(LOCATIONS.TOP_LEFT, new ScreenPoint(new Vector2(0, SCREEN_HEIGHT)));
        screenPoints.put(LOCATIONS.TOP_RIGHT, new ScreenPoint(new Vector2(SCREEN_WIDTH, SCREEN_HEIGHT)));
        screenPoints.put(LOCATIONS.CENTER, new ScreenPoint(new Vector2(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2)));

        emitterEngines = new Array<EmitterEngine>();
    }

    @Override
    public void resize(int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render() {
        this.RenderPoints();
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
