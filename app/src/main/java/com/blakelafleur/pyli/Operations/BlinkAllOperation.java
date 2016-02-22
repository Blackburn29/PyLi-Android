package com.blakelafleur.pyli.Operations;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by blake on 2/21/16.
 */
public class BlinkAllOperation implements OperationInterface {

    private float speed;
    private int light;

    public BlinkAllOperation(int light, float speed) {
        this.light = light;
        this.speed = speed;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("light", this.light);
            json.put("speed", this.speed);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
