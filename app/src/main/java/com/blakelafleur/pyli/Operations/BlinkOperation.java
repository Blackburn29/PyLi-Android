package com.blakelafleur.pyli.Operations;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by blake on 2/18/16.
 */
public class BlinkOperation extends SetOperation implements OperationInterface {
    float speed;

    public BlinkOperation(int light, float speed, float h, float s, float v) {
        super(light, h, s, v);
        this.speed = speed;
    }

    public float getSpeed() {
        return this.speed;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        try {
            json.put("speed", this.speed);
        } catch (JSONException e) {
            return null;
        }
        return json;
    }
}
