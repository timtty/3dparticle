package measurements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class ScreenPoint {
    public float SIZE = 12.0f;
    public Vector2 position;
    public Circle bounds;
    public Color color;
    public float duration = 0;

    public ScreenPoint(Vector2 startPosition) {
        position = startPosition;
        bounds = new Circle(startPosition.x, startPosition.y, SIZE);
        color = new Color(0, 1.0f, 0, 1.0f);
    }

    public ScreenPoint(Vector2 startPosition, Color startColor) {
        position = startPosition;
        bounds = new Circle(startPosition.x, startPosition.y, SIZE);
        color = startColor;
    }
}
