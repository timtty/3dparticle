package utils;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;

public class RandomColor {
    public static Color randomColor(boolean randomAlpha) {
        Random random = new Random();
        float alphaColor = (randomAlpha) ? random.nextFloat() : 1.0f;
        return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), alphaColor);
    }

    public static Color randomColor() {
        return randomColor(false);
    }
}
