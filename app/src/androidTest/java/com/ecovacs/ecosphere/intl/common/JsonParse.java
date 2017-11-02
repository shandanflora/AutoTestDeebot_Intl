package com.ecovacs.ecosphere.intl.common;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ecosqa on 16/11/24.
 * parse json file
 */

public class JsonParse {
    private static  JsonParse jsonParse = null;

    private JsonParse(){

    }

    public static JsonParse getJsonParse(){
        if(jsonParse == null){
            jsonParse = new JsonParse();
        }
        return jsonParse;
    }


    private String inputStream2String(InputStream   is)   throws   IOException{
        ByteArrayOutputStream baos   =   new   ByteArrayOutputStream();
        int i;
        while((i=is.read())!=-1){
            baos.write(i);
        }
        return   baos.toString();
    }


    public List readDataFromJson(String strFile, String strKey) {
        String str="";
        try {
            InputStream inputstream = this.getClass().getClassLoader().getResourceAsStream(strFile);
            str = inputStream2String(inputstream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (str == null) {
            Log.e("AutoTest", "(readDataFromJson)Not found data in json file!!!!");
            return null;
        }
        //Log.i("AutoTest", "(readDataFromJson)str:" + str);
        List<String> list = new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(str);
            JSONArray jsonArray = jsonObj.getJSONArray(strKey);
            if (jsonArray.length() == 0) {
                Log.e("AutoTest", "(readDataFromJson)Not found data with the key!!!!");
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++){
                list.add(jsonArray.get(i).toString());
            }
            for(int i = 0; i < list.size(); i++){
                Log.i("AutoTest", "(readDataFromJson)list:" + list.get(i));
            }
        } catch (JSONException e) {
            Log.e("AutoTest", "(readDataFromJson)Could not find the value matched key!!!");
            e.printStackTrace();
        }
        return list;
    }



}
