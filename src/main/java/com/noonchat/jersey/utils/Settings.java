package com.noonchat.jersey.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Settings
{
    private static final JSONObject settings = _getSettings();

    private Settings() throws Exception
    {
        if(settings == null)
        {
            throw new IOException("Cannot read settings");
        }
    }

    public static String get(String key)
    {
        return (String) settings.get(key);
    }

    private static JSONObject _getSettings()
    {
        try
        {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(new FileReader("src/params/settings.json"));
        }
        catch (IOException | ParseException e)
        {
            System.out.println(e);
            return null;
        }
    }
}
