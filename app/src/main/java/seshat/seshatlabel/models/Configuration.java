package seshat.seshatlabel.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import seshat.seshatlabel.ConfigActivity;
import seshat.seshatlabel.MainActivity;

/**
 * Created by root on 06/06/17.
 */

public class Configuration {

    private static Configuration instance;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;

    public static final String PREF_FILE_NAME = "PrefFile";


    public String getPrinterMAC() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("printerMAC", null);
    }

    public void setPrinterMAC(String printerMAC) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("printerMAC", printerMAC);
        editor.commit();
    }

    public String getRpiIP() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("rpiIP", null);
    }

    public void setRpiIP(String rpiIP) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("rpiIP", rpiIP);
        editor.commit();
    }

    public String getRpiPORT() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("rpiPORT", null);
    }

    public void setRpiPORT(String rpiPORT) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("rpiPORT", rpiPORT);
        editor.commit();
    }

    public String getBackupIP() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("backupIP", null);
    }

    public void setBackupIP(String backupIP) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("backupIP", backupIP);
        editor.commit();
    }

    public String getBackupPORT() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("backupPORT", null);
    }

    public void setBackupPORT(String backupPORT) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("backupPORT", backupPORT);
        editor.commit();
    }

    public String getqrURL() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("qrURL", null);
    }

    public void setqrURL(String qrURL) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("qrURL", qrURL);
        editor.commit();
    }

    public void setDefault(String printerMAC, String rpiIP, String rpiPORT, String backupIP, String backupPORT) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if((preferences.getString("printerMAC", null) == null) || (preferences.getString("rpiIP", null) == null) ||
                (preferences.getString("rpiPORT", null) == null) ||(preferences.getString("backupIP", null) == null) ||
                (preferences.getString("backupPORT", null) == null)) {
            Log.d("Configuration", "Set default with config file");
            Log.d("Configuration", "DEFAULT CONFIG ==== " + printerMAC + ":" + rpiIP + ":" + rpiPORT + ":" + backupIP + ":" + backupPORT);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("printerMAC", printerMAC);
            editor.putString("rpiIP", rpiIP);
            editor.putString("rpiPORT", rpiPORT);
            editor.putString("backupIP", backupIP);
            editor.putString("backupPORT", backupPORT);
            editor.putString("qrURL", null);
            editor.commit();
        }
    }

    private String printerMAC, rpiIP, rpiPORT, backupIP, backupPORT;

    private Configuration() {

    }

    public static Configuration getInstance() {
        if(instance == null) {
            instance= new Configuration();
        }
        return instance;
    }
}
