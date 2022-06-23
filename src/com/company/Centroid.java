package com.company;

import java.awt.*;
import java.util.Random;

public class Centroid {
    int x;
    int y;
    Color colour;
    Random rand = new Random();

    public Centroid() {
        x = random(0,980);
        y = random(0, 760);
        colour = new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat());
    }

    public void updateX(int x) {
        this.x = x;
    }

    public void updateY(int y) {
        this.y = y;
    }
    public Color getColour() {
        return this.colour;
    }


    public int random(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }
}
