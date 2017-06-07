package seshat.seshatlabel;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import seshat.seshatlabel.models.Configuration;
import seshat.seshatlabel.models.LabelModel;
import seshat.seshatlabel.print.BluetoothPrinter;
import seshat.seshatlabel.views.AsyncListViewLoader;
import seshat.seshatlabel.views.SimpleAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * Adaptateur permettant l'affichage des données
     * de l'API Restful (nom d'échantillon...)
     */
    private SimpleAdapter adpt;

    private ListView list;

    private BluetoothPrinter printer;

    private int compteurHiddenConfigLayout;

    private long initTime;

    private int currentState;

    public final static int STATE_RPI = 1;
    public final static int STATE_BACKUP = 2;

    /**
     * Methode lancée à l'ouverture de l'app
     * requête l'API pour afficher la liste des échantillons
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View mainActivity = findViewById(android.R.id.content);
        Button printButton = (Button)findViewById(R.id.printButton);
        printButton.setOnClickListener(this);
        //adpt = new SimpleAdapter(new ArrayList<LabelModel>(), this);
        adpt = new SimpleAdapter(new ArrayList<LabelModel>(), this);
        this.printer = new BluetoothPrinter(this);
        this.list = (ListView) findViewById(R.id.listview);
        this.list.setAdapter(this.adpt);
        this.adpt.setMode(Attributes.Mode.Single);
        this.compteurHiddenConfigLayout = 0;
        Resources res = this.getResources();
        Configuration config = Configuration.getInstance();
        config.setContext(this);
        config.setDefault(res.getString(R.string.printerMac), res.getString(R.string.piAddress), res.getString(R.string.piPort), res.getString(R.string.backupIp), res.getString(R.string.backupPort));
        // Exec async load task
        Log.d("MainActivity", "IP : " + config.getRpiIP() + " PORT : " + config.getRpiPORT());
        this.currentState = MainActivity.STATE_RPI;
        (new AsyncListViewLoader(this, adpt, mainActivity)).execute("http://" + config.getRpiIP() + ":" + config.getRpiPORT() + "/index.php/api/labels");
    }

    public void onClick(View v)
    {
        if (v == findViewById(R.id.printButton))
        {
            for(LabelModel lm : this.adpt.getItemList()) {
                if(lm.isChecked()) {
                    for(int i = 0; i < lm.getNbImpressions(); i++) {
                        Log.d("MainActivity", "Lancement impression : " + lm.getLabel() + " : " + lm.getProject() + " : " + lm.getYear());
                        if(lm.getCurrentFormat() == LabelModel.FORMAT_STANDARD) {
                            this.printer.print(lm);
                        } else {
                            this.printer.smallPrint(lm);
                        }
                    }
                    lm.reset();
                }
            }
            this.adpt.notifyDataSetChanged();
        }
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                this.compteurHiddenConfigLayout++;
                Log.d("MainActivity", "Compteur : " + this.compteurHiddenConfigLayout);
                if(this.compteurHiddenConfigLayout >= 10) {
                    long currentTime = System.nanoTime() ;
                    Log.d("MainActivity", "temps : " + (currentTime - this.initTime) / 1000000000);
                    if(((currentTime - this.initTime) / 1000000000) <= 3) {
                        Log.d("MainActivity", "Menu caché à afficher !");
                        Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
                        startActivity(intent);
                    }
                    this.compteurHiddenConfigLayout = 0;
                } else if (this.compteurHiddenConfigLayout == 1) {
                    this.initTime = System.nanoTime() ;
                }
                View mainActivity = findViewById(android.R.id.content);
                Configuration config = Configuration.getInstance();
                if(this.currentState == MainActivity.STATE_RPI) {
                    Log.d("MainActivity", "IP : " + config.getRpiIP() + " PORT : " + config.getRpiPORT());
                    (new AsyncListViewLoader(this, adpt, mainActivity)).execute("http://" + config.getRpiIP() + ":" + config.getRpiPORT() + "/index.php/api/labels");
                } else if (this.currentState == MainActivity.STATE_BACKUP) {
                    Log.d("MainActivity", "IP : " + config.getBackupIP() + " PORT : " + config.getBackupPORT());
                    (new AsyncListViewLoader(this, adpt, mainActivity)).execute("http://" + config.getBackupIP() + ":" + config.getBackupPORT() + "/index.php/api/labels");
                }
                return true;
            case R.id.action_download :
                TextView label = (TextView) findViewById(R.id.titreEtiquette);
                label.setText("Anciennes étiquettes");
                mainActivity = findViewById(android.R.id.content);
                config = Configuration.getInstance();
                Log.d("MainActivity", "IP : " + config.getBackupIP() + " PORT : " + config.getBackupPORT());
                (new AsyncListViewLoader(this, adpt, mainActivity)).execute("http://" + config.getBackupIP() + ":" + config.getBackupPORT() + "/index.php/api/labels");
                return true;
            case R.id.action_home :
                label = (TextView) findViewById(R.id.titreEtiquette);
                label.setText("Nouvelles étiquettes");
                mainActivity = findViewById(android.R.id.content);
                config = Configuration.getInstance();
                Log.d("MainActivity", "IP : " + config.getRpiIP() + " PORT : " + config.getRpiPORT());
                (new AsyncListViewLoader(this, adpt, mainActivity)).execute("http://" + config.getRpiIP() + ":" + config.getRpiPORT() + "/index.php/api/labels");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


