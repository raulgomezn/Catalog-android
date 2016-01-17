package com.vericarte.catalog.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by raulgomez on 17/01/16.
 */
public interface IJSONSerializer {

    JSONObject toJSON() throws JSONException;

}
