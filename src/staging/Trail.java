package staging;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

/**
 * Created by Tim on 12/25/2014.
 */
public class Trail {
    public ModelInstance trail;
    public Color color;

    public Trail(ModelInstance trail, Color color) {
        this.color = color;
        this.trail = new ModelInstance(trail);
    }
}
