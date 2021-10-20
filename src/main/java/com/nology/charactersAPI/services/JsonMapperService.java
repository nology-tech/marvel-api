package com.nology.charactersAPI.services;

import com.google.gson.JsonObject;

// Note: There is no public keywords, I only want this class and methods visible within this package
class JsonMapperService {

    // TODO: This needs Unit Tests
    static String getStringFromJson(JsonObject jsonObject, String propertyName) {
        if (jsonObject.has(propertyName) && !jsonObject.get(propertyName).isJsonNull()) {
            return jsonObject.get(propertyName).getAsString();
        } else {
            return "";
        }
    }

    // TODO: This needs Unit Tests
    // Note: .has() checks for keys, however I can't find a way to check if value is null without UnsupportedExceptions being thrown
    static int getIntFromJson(JsonObject jsonObject, String propertyName) {
        if (jsonObject.has(propertyName) && !jsonObject.get(propertyName).isJsonNull()) {
            return jsonObject.get(propertyName).getAsInt();
        } else {
            return -1;
        }
    }
}
