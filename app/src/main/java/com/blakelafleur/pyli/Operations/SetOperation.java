package com.blakelafleur.pyli.Operations;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by blake on 2/18/16.
 */
public class SetOperation implements OperationInterface {
    private int light;
    private float h, s, v;

    public SetOperation(int light, float h, float s, float v) {
        this.light = light;
        this.h = h;
        this.s = s;
        this.v = v;
    }

    public int getLight() {
        return light;
    }

    public float[] getHSV() {
        return new float[]{h, s, v};
    }

    public JSONObject toJSON() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("light", light);
            obj.put("h", h);
            obj.put("s", s);
            obj.put("v", v);
            return obj;
        } catch (JSONException e) {
            return null;
        }
    }
}
