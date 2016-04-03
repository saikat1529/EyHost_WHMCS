package com.eysoft.eyhost;

import org.json.JSONObject;

/**
 * Created by EySoft IT Solution on 2/24/2016.
 */
public interface AsyncResponse {
    void processFinish(JSONObject[] jsonObject);
}
