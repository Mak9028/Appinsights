package com.example.alefmobitech.appinsights;

/**
 * Created by alefmobitech on 20/6/17.
 */

public class UserData {
    private String key_value;
    private String value;


    public  UserData(){

    }

    public UserData(String key_value, String value) {
        this.key_value = key_value;
        this.value = value;
    }

    public void setKey_value(String key_value) {
        this.key_value = key_value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey_value() {
        return key_value;
    }

    public String getValue() {
        return value;
    }
}
