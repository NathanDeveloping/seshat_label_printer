package seshat.seshatlabel.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import seshat.seshatlabel.R;
import seshat.seshatlabel.models.LabelModel;

/**
 * Classe permettant la gestion de la ListView
 * remplissage, affichage et affectation des actions
 * aux boutons
 */
public class SimpleAdapter extends BaseSwipeAdapter {

    private Context mContext;

    /**
     * Liste des étiquettes
     */
    public List<LabelModel> itemList = new ArrayList<LabelModel>();

    /**
     * Constructeur
     * @param itemList
     *          liste d'étiquettes disponibles
     * @param mContext
     *          contexte/vue sur laquelle se trouve la listview
     */
    public SimpleAdapter(List<LabelModel> itemList, Context mContext) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    /**
     * Retrouve le layout en background
     * de chaque item de la liste (balayage vers la gauche pour
     * le faire apparaître)
     * @param position
     * @return
     */
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    /**
     * Génère l'item à la position
     * (association layout, etc)
     * @param position
     *          position de l'item à générer
     * @param parent
     * @return
     */
    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_detail, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
            }
        });
        /**
         * Spinner de choix de format
         */
        Spinner spinner = (Spinner) v.findViewById(R.id.printFormat_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.mContext,
                R.array.printFormat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return v;
    }

    /**
     * Remplissage de l'item à la
     * position donnée
     * @param position
     *          position de l'item à remplir
     * @param convertView
     */
    @Override
    public void fillValues(int position, View convertView) {
        View v = convertView;
        ListView lv = null;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_detail, null);
        } else {
        }
        final LabelModel c = (LabelModel) getItem(position);
        TextView text = (TextView) v.findViewById(R.id.label);
        TextView compteur = (TextView) v.findViewById(R.id.compteur);
        compteur.setText("" + c.getNbImpressions());
        text.setText(c.getLabel());

        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        checkBox.setChecked(c.isChecked());
        final Button moins = (Button)v.findViewById(R.id.moins);
        final Button plus = (Button)v.findViewById(R.id.plus);
        final SimpleAdapter instance = this;
        checkBox.setOnClickListener(new View.OnClickListener() {
            private SimpleAdapter inst = instance;
            @Override
            public void onClick(View v) {
                c.setChecked(checkBox.isChecked());
                if(c.getNbImpressions() == 0) {
                    if(checkBox.isChecked()) {
                        c.setNbImpressions(1);
                        this.inst.notifyDataSetChanged();
                    }
                } else if(c.getNbImpressions() == 1){
                    if(!checkBox.isChecked()) {
                        c.setNbImpressions(0);
                        this.inst.notifyDataSetChanged();
                    }
                }
            }
        });

        moins.setOnClickListener(new View.OnClickListener() {
            private SimpleAdapter inst = instance;
            @Override
            public void onClick(View view) {
                c.decrementImpressions();
                this.inst.notifyDataSetChanged();
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            private SimpleAdapter inst = instance;
            @Override
            public void onClick(View view) {
                c.incrementImpressions();
                this.inst.notifyDataSetChanged();

            }
        });
        Spinner spinner = (Spinner) v.findViewById(R.id.printFormat_spinner);
       // spinner.setSelection(c.getCurrentFormat() - 1);
        this.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected = adapterView.getItemAtPosition(i).toString();
                int itemSelectedFormat = LabelModel.FORMAT_STANDARD;
                switch (itemSelected) {
                    case "Standard" :
                        break;
                    case "Cryotube" :
                        itemSelectedFormat = LabelModel.FORMAT_CRYOTUBE;
                }
                c.setCurrentFormat(itemSelectedFormat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getCount() {
        if(this.itemList != null) {
            return this.itemList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return this.itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<LabelModel> getItemList() {
        return itemList;
    }

    public void setItemList(List<LabelModel> itemList) {
        this.itemList = itemList;
    }

    /**
     * Réinitialise la listview et tous ses items
     * généralement lancé après une impression
     */
    public void reset() {
        if(this.itemList != null) {
            for(int i = 0; i < this.itemList.size(); i++) {
                this.itemList.get(i).reset();
            }
        }
        this.notifyDataSetChanged();
    }
}