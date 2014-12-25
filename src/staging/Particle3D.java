package staging;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import utils.RandomColor;

import java.util.Random;

public class Particle3D {
    public enum DIRECTION {
        LEFT, RIGHT
    }

    public enum Z_DIRECTION {
        FORWARD, BACKWARD
    }

    public enum FRAME3D {
        WIRE_FRAME(0), SOLID_FRAME(4);
        private int val;

        private FRAME3D(int value) {
            this.val = value;
        }
    }

    public enum SHAPE_TYPES {
        CUBE, SPHERE, CONE
    }

    public Vector3 position;
    public ModelInstance instance;
    private Model model;
    public PointLight light = new PointLight();

    public Color color;
    public FRAME3D frame3D;
    public float duration = 0;

    private boolean randomShapes = false;
    private SHAPE_TYPES shapeType = SHAPE_TYPES.SPHERE;

    // static
    private final DIRECTION direction = (new Random().nextBoolean()) ? DIRECTION.LEFT : DIRECTION.RIGHT;
    private final Z_DIRECTION z_direction = (new Random().nextBoolean()) ? Z_DIRECTION.BACKWARD: Z_DIRECTION.FORWARD;

    // math
    public float X_PULL = (new Random().nextFloat());
    private float Z_PULL = (new Random().nextFloat());
    private float _ROTATION = (new Random().nextFloat() * 1f);
    private float ROTATION = 8.83f;
    private final float MAX_ACCELERATION = 1.4f;
    private float ACCELERATION = -0.9f;
    // private final float SPEED = 0.018f;
    private final float SPEED = 0.018f;
    private Vector3 ORIGIN;


    public Particle3D(Vector3 startPosition, float size, ModelBuilder modelBuilder) {
        this(startPosition, size, modelBuilder, false);
    }

    public Particle3D(Vector3 startPosition, float size, ModelBuilder modelBuilder, SHAPE_TYPES shapeType) {
        position = startPosition;
        ORIGIN = new Vector3(position);
        color = RandomColor.randomColor(false);
        this.shapeType = shapeType;

        //frame3D = (new Random().nextBoolean()) ? FRAME3D.WIRE_FRAME: FRAME3D.SOLID_FRAME;
        frame3D = FRAME3D.SOLID_FRAME;

        switch (this.shapeType) {
            case CUBE:
                model = modelBuilder.createBox(size, size, size,
                        new Material(ColorAttribute.createDiffuse(color)),
                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                break;
            case SPHERE:
                model = modelBuilder.createSphere(size, size, size, 18, 18,
                        frame3D.val, new Material(ColorAttribute.createDiffuse(color)),
                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                break;
            case CONE:
                model = modelBuilder.createCone(size, size, size, 18,
                        new Material(ColorAttribute.createDiffuse(color)),
                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                break;
            default:
                model = modelBuilder.createSphere(size, size, size, 18, 18,
                        new Material(ColorAttribute.createDiffuse(color)),
                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        }

        instance = new ModelInstance(model);
        instance.transform.setToTranslation(position);

    }

    public Particle3D(Vector3 startPosition, float size, ModelBuilder modelBuilder, Boolean randomShapes) {
        position = startPosition;
        ORIGIN = new Vector3(position);
        color = RandomColor.randomColor(false);
        this.randomShapes = randomShapes;

        //frame3D = (new Random().nextBoolean()) ? FRAME3D.WIRE_FRAME: FRAME3D.SOLID_FRAME;
        frame3D = FRAME3D.SOLID_FRAME;

        int I = new Random().nextInt(2);

        switch (I) {
            case 0:
                model = modelBuilder.createBox(size, size, size,
                        new Material(ColorAttribute.createDiffuse(color)),
                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                break;
            case 1:
                model = modelBuilder.createSphere(size, size, size,
                        18, 18, frame3D.val, new Material(ColorAttribute.createDiffuse(color)),
                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                break;
            case 2:
                model = modelBuilder.createCone(size, size, size,
                        18, new Material(ColorAttribute.createDiffuse(color)),
                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                break;
            default:
                model = modelBuilder.createSphere(size, size, size,
                        18, 18, frame3D.val, new Material(ColorAttribute.createDiffuse(color)),
                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                break;
        }

        instance = new ModelInstance(model);
        instance.transform.setToTranslation(position);

    }

    public void Update() {
        ACCELERATION += SPEED;
        if (ACCELERATION > MAX_ACCELERATION) ACCELERATION = MAX_ACCELERATION;

        position = instance.nodes.get(0).translation;
        position.y -= ACCELERATION;

        switch (direction) {
            case LEFT:
                position.x -= X_PULL;
                break;
            case RIGHT:
                position.x += X_PULL;
                break;
        }

        switch (z_direction) {
            case FORWARD:
                position.z -= Z_PULL;
                break;
            case BACKWARD:
                position.z += Z_PULL;
                break;
        }

        _ROTATION += ROTATION;

        instance.nodes.get(0).translation.set(position);
        instance.nodes.get(0).rotation.set(new Vector3(20f, 0, 0), _ROTATION);
        instance.calculateTransforms();

        light.set(Color.WHITE, position, 22f);

        duration += 0.03;
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public float getACCELERATION() {
        return ACCELERATION;
    }

    public void destroy() {
        this.model.dispose();
    }
}
