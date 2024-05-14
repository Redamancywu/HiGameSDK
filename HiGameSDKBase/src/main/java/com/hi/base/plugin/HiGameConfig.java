package com.hi.base.plugin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HiGameConfig {
    private Map<String, String> configs;
    public HiGameConfig(){
        configs=new HashMap<String, String>();
    }
    public HiGameConfig(Map<String,String> configs){
        this();

        if(configs!=null){
            for(String key : configs.keySet()){
                put(key, configs.get(key));
            }
        }


    }

    public boolean contains(String key){

        return configs.containsKey(key);
    }

    public void put(String key, String value){
        configs.put(key, value);
    }

    public void put(String key, boolean val) {
        configs.put(key, String.valueOf(val));
    }

    public void put(String key, int val) {
        configs.put(key, val+"");
    }

    public void put(String key, long val) {
        configs.put(key, val+"");
    }

    public void put(String key, short val) {
        configs.put(key, val+"");
    }

    public void put(String key, float val) {
        configs.put(key, val+"");
    }

    public void put(String key, double val) {
        configs.put(key, val+"");
    }

    public void put(String key, byte val) {
        configs.put(key, val+"");
    }

    public String getString(String key){
        if(configs.containsKey(key)){
            return configs.get(key);
        }
        return null;
    }

    public int getInt(String key){
        String val = getString(key);

        try{
            return val == null ? 0 : Integer.parseInt(val);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;

    }

    public Float getFloat(String key){
        String val = getString(key);

        try{
            return val == null ? null : Float.parseFloat(val);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Double getDouble(String key){
        String val = getString(key);

        try{
            return val == null ? null : Double.parseDouble(val);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    public Long getLong(String key){
        String val = getString(key);

        try{
            return val == null ? null : Long.parseLong(val);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Boolean getBoolean(String key){
        String val = getString(key);

        try{
            return val == null ? null :Boolean.parseBoolean(val);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Short getShort(String key){
        String val = getString(key);

        try{
            return val == null ? null :Short.parseShort(val);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    public Byte getByte(String key){
        String val = getString(key);

        try{
            return val == null ? null : Byte.parseByte(val);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(String key : configs.keySet()){
            sb.append(key).append("=").append(configs.get(key)).append("\n");

        }
        return sb.toString();
    }



    public JSONObject toJSONOBject() throws JSONException {

        JSONObject json = new JSONObject();
        for (String key : configs.keySet()) {
            json.put(key, configs.get(key));
        }
        return json;
    }


}
