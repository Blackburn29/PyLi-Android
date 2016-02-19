package com.blakelafleur.pyli.Operations;

/**
 * Created by blake on 2/18/16.
 */
public class FadeOperation extends SetOperation {
    float speed;

    public FadeOperation(int light, float speed, float h, float s, float v) {
        super(light, h, s, v);
        this.speed = speed;
    }

    public float getSpeed() {
        return this.speed;
    }
}
