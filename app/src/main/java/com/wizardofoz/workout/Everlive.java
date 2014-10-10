package com.wizardofoz.workout;

import com.telerik.everlive.sdk.core.EverliveApp;

public class Everlive {
    private EverliveApp app;
    private static Everlive instance;

    protected Everlive(String apiKey){
        app = new EverliveApp(apiKey);
    }

    public static Everlive getEverlive(){
        if (instance == null){
            instance = new Everlive("8hJ6nOSy4uST8iDq");
        }

        return instance;
    }
}
