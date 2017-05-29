package seshat.seshatlabel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;

import seshat.seshatlabel.models.LabelModel;
import seshat.seshatlabel.print.BluetoothPrinter;
import seshat.seshatlabel.views.AsyncListViewLoader;
import seshat.seshatlabel.views.SimpleAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Adaptateur permettant l'affichage des données
     * de l'API Restful (nom d'échantillon...)
     */
    private SimpleAdapter adpt;

    private ListView list;

    private BluetoothPrinter printer;

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
        adpt = new SimpleAdapter(new ArrayList<LabelModel>(), this);
        this.printer = new BluetoothPrinter();
        this.list = (ListView) findViewById(R.id.listview);
        this.list.setAdapter(adpt);
        // Exec async load task
        (new AsyncListViewLoader(this, adpt, mainActivity)).execute("http://10.10.10.10:8080/index.php/api/labels");
    }

    public void onClick(View v)
    {
        if (v == findViewById(R.id.printButton))
        {
            for(LabelModel lm : this.adpt.getItemList()) {
                if(lm.isChecked()) {
                    Log.d("MainActivity", "Lancement impression : " + lm.getLabel() + " : " + lm.getProject() + " : " + lm.getYear());
                    this.printer.print(lm);
                }
            }
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
                View mainActivity = findViewById(android.R.id.content);
                (new AsyncListViewLoader(this, adpt, mainActivity)).execute("http://10.10.10.10:8080/index.php/api/labels");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


