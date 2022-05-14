package com.jack.algera.gahlificapption.http;

import android.net.http.HttpResponseCache;
import android.util.JsonReader;
import android.util.JsonToken;

import com.jack.algera.gahlificapption.authentication.UserLogin;
import com.jack.algera.gahlificapption.utils.PreferencesUtils;

import org.apache.http.HttpHeaders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackendAPIClient {
    private static final String BASE_URL = "https://gahlification.herokuapp.com/api";

    public static List<String> getAllSheetNames(PreferencesUtils preferencesUtils) throws IOException {
        List<String> sheetNames = new ArrayList<>();

        URL url = new URL(BASE_URL + "/sheets");
        HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
        myConnection.setRequestProperty(HttpHeaders.AUTHORIZATION, "Bearer " + preferencesUtils.getToken());

        if (myConnection.getResponseCode() == 200) {
            InputStream responseBody = myConnection.getInputStream();
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, StandardCharsets.UTF_8);

            JsonReader jsonReader = new JsonReader(responseBodyReader);
            jsonReader.beginArray(); // Start processing the JSON object
            while (jsonReader.hasNext()) { // Loop through all keys
                sheetNames.add(jsonReader.nextString());
            }

            jsonReader.close();
        } else {
            System.out.println("getAllSheetNames didn't work");
        }
        myConnection.disconnect();
        return sheetNames;
    }

    public static int addCategoryValue(PreferencesUtils preferencesUtils, String sheetName, String category, String cost, String description) throws IOException {
        URL url = new URL(BASE_URL + "/sheets/" + sheetName + "/categories/" + category);

        String jsonInputString =
                "{" +
                        "\"description\":" + "\"" + description + "\"," +
                        "\"cost\":" + cost +
                "}";

        System.out.println(jsonInputString);

        HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
        myConnection.setRequestProperty(HttpHeaders.AUTHORIZATION, "Bearer " + preferencesUtils.getToken());
        myConnection.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/json");
        myConnection.setRequestMethod("POST");
        myConnection.setDoOutput(true);

        try(OutputStream os = myConnection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = myConnection.getResponseCode();
        myConnection.disconnect();
        return responseCode;
    }

    public static int login(PreferencesUtils preferencesUtils, String username, String password) throws IOException {
        URL url = new URL(BASE_URL + "/authenticate");

        String jsonInputString =
                "{" +
                    "\"username\":" + "\"" + username + "\"," +
                    "\"password\":" + "\"" + password + "\"" +
                "}";

        System.out.println(jsonInputString);

        HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
        myConnection.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/json");
        myConnection.setRequestMethod("POST");
        myConnection.setDoOutput(true);

        try(OutputStream os = myConnection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = myConnection.getResponseCode();

        if (responseCode == 200) {
            InputStream responseBody = myConnection.getInputStream();
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, StandardCharsets.UTF_8);

            Map<String, String> map = new HashMap<>();

            JsonReader jsonReader = new JsonReader(responseBodyReader);
            jsonReader.beginObject(); // Start processing the JSON object
            while (jsonReader.hasNext()) { // Loop through all keys
                JsonToken keyJT = jsonReader.peek();
                if (keyJT == JsonToken.NAME) {
                    String key = jsonReader.nextName();
                    JsonToken valueJT = jsonReader.peek();
                    switch (valueJT) {
                        case STRING:
                            map.put(key, jsonReader.nextString());
                            break;
                        case NUMBER:
                            map.put(key, "" + jsonReader.nextLong());
                            break;
                    }
                } else {
                    jsonReader.skipValue();
                }
            }

            if (map.containsKey(PreferencesUtils.TOKEN) && map.containsKey(PreferencesUtils.TIMESTAMP)) {
                preferencesUtils.setToken(new UserLogin(map.get(PreferencesUtils.TOKEN), Long.parseLong(map.get(PreferencesUtils.TIMESTAMP))));
            } else {
                responseCode = -1;
            }

            jsonReader.close();
        } else {
            System.out.println("login didn't work");
        }

        myConnection.disconnect();
        return responseCode;
    }
}
