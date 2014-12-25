package staging;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import utils.RandomColor;

import java.util.Random;

public class Particle {
    public enum DIRECTION {
        LEFT, RIGHT
    }

    public Vector2 position;
    public Circle bounds;
    public Color color;
    public float duration = 0;

    private final DIRECTION direction = (new Random().nextBoolean()) ? DIRECTION.LEFT : DIRECTION.RIGHT;

    //math
    private float X_PULL;
    private final float MAX_ACCELERATION = 200;
    private float ACCELERATION = -100;
    private Vector2 ORIGIN;


    public Particle(Vector2 startPosition, float size) {
        position = startPosition;
        ORIGIN = new Vector2(position);
        bounds = new Circle(position.x, position.y, size);
        color = RandomColor.randomColor(false);

        X_PULL = (new Random().nextFloat() * 40);
    }

    public void Update() {
        float delta = Gdx.graphics.getDeltaTime();
        duration += delta;
        position.y -= ACCELERATION * delta;
        ACCELERATION += 0.83f;
        if (ACCELERATION > MAX_ACCELERATION) ACCELERATION = MAX_ACCELERATION;

        if ( !(position.x < 2) && !(position.x > (Gdx.graphics.getWidth() - 2)) ) {
            switch (direction) {
                case LEFT:
                    position.x -= X_PULL * delta;
                    break;
                case RIGHT:
                    position.x += X_PULL * delta;
                    break;
            }
        }
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public float getACCELERATION() {
        return ACCELERATION;
    }
}
