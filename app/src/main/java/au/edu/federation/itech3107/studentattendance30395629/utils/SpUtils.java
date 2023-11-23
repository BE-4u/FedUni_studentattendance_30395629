package au.edu.federation.itech3107.studentattendance30395629.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;


public class SpUtils {
    private SharedPreferences mSharedPreferences;

    public SpUtils(Context context){
        mSharedPreferences = context.getSharedPreferences("student",Context.MODE_PRIVATE);
    }

    public void putSp_Str(String key, String value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key,
                         value);
        editor.apply();
    }

    public String getString(String strKey){
        String result = mSharedPreferences.getString(strKey, "");
        return result;
    }

    public String getString(String strKey, String strDefault){
        String result = mSharedPreferences.getString(strKey,
                                                     strDefault);
        return result;
    }

    public void putString(String strKey, String strData){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(strKey,
                         strData);
        editor.commit();
    }

    public Boolean getBoolean(String strKey){
        Boolean result = mSharedPreferences.getBoolean(strKey,
                                                       false);
        return result;
    }

    public Boolean getBoolean(String strKey, Boolean strDefault){
        Boolean result = mSharedPreferences.getBoolean(strKey,
                                                       strDefault);
        return result;
    }


    public void putBoolean(String strKey, Boolean strData){

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(strKey,
                          strData);
        editor.commit();
    }

    public void putStringSet(String strKey, HashSet<String> strData){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putStringSet(strKey,
                            strData);
        editor.apply();
    }

    public HashSet<String> getStringSet(String strKey){
        HashSet<String> result = (HashSet<String>) mSharedPreferences.getStringSet(strKey,
                                                                                   new HashSet<String>());
        return result;
    }

}
