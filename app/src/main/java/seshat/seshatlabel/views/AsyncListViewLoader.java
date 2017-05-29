package seshat.seshatlabel.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.net.ConnectException;

import seshat.seshatlabel.R;
import seshat.seshatlabel.exceptions.NoDataException;
import seshat.seshatlabel.models.LabelModel;

/**
 * Classe permettant la récupération des données de l'API
 * de façon asynchrone (non bloquant)
 */
public class AsyncListViewLoader extends AsyncTask<String, Void, List<LabelModel>> {

    private ProgressDialog dialog;
    private SimpleAdapter adpt;
    private Context context;
    private View associatedView;

    public AsyncListViewLoader(Context context, SimpleAdapter adpt, View v) {
        super();
        this.context = context;
        this.dialog = new ProgressDialog(this.context);
        this.adpt = adpt;
        this.associatedView = v;
    }

    @Override
    protected void onPostExecute(List<LabelModel> result) {
        super.onPostExecute(result);
        dialog.dismiss();
        adpt.setItemList(result);
        adpt.notifyDataSetChanged();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Downloading labels...");
        dialog.show();
    }

    @Override
    protected List<LabelModel> doInBackground(String... params) {
        List<LabelModel> result = new ArrayList<LabelModel>();
        Log.d("AsyncListViewLoader", "Récupération étiquettes...");
        try {
            URL u = new URL(params[0]);

            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream is = conn.getInputStream();

            // Read the stream
            byte[] b = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while (is.read(b) != -1)
                baos.write(b);

            String JSONResp = new String(baos.toByteArray());

            JSONArray arr = new JSONArray(JSONResp);
            if(arr.length() == 0) throw new NoDataException("Aucune donnée disponible.");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject currentObj = arr.getJSONObject(i);
                Log.d("AsynListViewLoader", arr.getJSONObject(i).getString("label"));
                result.add(new LabelModel(currentObj.getString("label"), currentObj.getString("project"), currentObj.getString("date")));
            }
            return result;
        }
        catch(Throwable t) {
            Snackbar sb = null;
            if(t instanceof NoDataException) {
                sb = Snackbar.make(this.associatedView, t.getMessage(), Snackbar.LENGTH_LONG);
            } else {
                sb = Snackbar.make(this.associatedView, R.string.no_connect, Snackbar.LENGTH_LONG);
            }
            sb.show();
            t.printStackTrace();
        }
        return null;
    }

}