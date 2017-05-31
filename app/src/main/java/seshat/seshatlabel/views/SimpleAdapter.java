package seshat.seshatlabel.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.HashMap;
import java.util.List;

import seshat.seshatlabel.R;
import seshat.seshatlabel.models.LabelModel;

public class SimpleAdapter extends BaseSwipeAdapter {

    private Context mContext;
    public HashMap<String,String> checked = new HashMap<String,String>();
    public List<LabelModel> itemList;

    public SimpleAdapter(List<LabelModel> itemList, Context mContext) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_detail, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
            }
        });
        return v;
    }

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
    }

    @Override
    public int getCount() {
        return this.itemList.size();
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
}