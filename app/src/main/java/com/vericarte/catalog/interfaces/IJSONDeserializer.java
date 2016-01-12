package com.vericarte.catalog.interfaces;

import org.json.JSONObject;

/**
 * Created by raulgomez on 11/01/16.
 */
public interface IJSONDeserializer {
    void convertFrom(JSONObject json);
}
