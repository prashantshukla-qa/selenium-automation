package com.qait.automation.utils;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author prashantshukla
 */
public class JsonParser {

    public String getJsonValue(String jsonString, String jsonKey) throws JSONException {
        JSONObject obj = new JSONObject(jsonString);
        String jsonValue = obj.getString(jsonKey.split(":")[0]);
        if (jsonKey.contains(":")) {
            String[] splitString = jsonKey.split(":", 2);
            jsonValue = getJsonValue(jsonValue, splitString[1]);
        }

        return jsonValue;
    }
}
