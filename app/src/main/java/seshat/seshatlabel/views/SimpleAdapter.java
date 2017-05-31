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
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


//public class SimpleAdapter extends ArraySwipeAdapter {
//
//    public HashMap<String,String> checked = new HashMap<String,String>();
//    public List<LabelModel> itemList;
//
//
//    public SimpleAdapter(List<LabelModel> itemList, Context context, int resource) {
//        super(context, resource);
//        this.itemList = itemList;
//    }
//
//    public SimpleAdapter(Context context, int resource) {
//        super(context, resource);
//    }
//
//    public SimpleAdapter(Context context, int resource, int textViewResourceId) {
//        super(context, resource, textViewResourceId);
//    }
//
//    public SimpleAdapter(Context context, int resource, Object[] objects) {
//        super(context, resource, objects);
//    }
//
//    public SimpleAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
//        super(context, resource, textViewResourceId, objects);
//    }
//
//    public SimpleAdapter(Context context, int resource, List objects) {
//        super(context, resource, objects);
//    }
//
//    public SimpleAdapter(Context context, int resource, int textViewResourceId, List objects) {
//        super(context, resource, textViewResourceId, objects);
//    }
//
//    @Override
//    public int getSwipeLayoutResourceId(int position) {
//        return R.id.swipe;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View v = super.getView(position, convertView, parent);
//        ListView lv = null;
//        if (v == null) {
//            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            v = inflater.inflate(R.layout.list_detail, null);
//        } else {
//        }
//        final LabelModel c = (LabelModel) getItem(position);
//
//        TextView text = (TextView) v.findViewById(R.id.label);
//        text.setText(c.getLabel());
//
//        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                c.setChecked(checkBox.isChecked());
//            }
//        });
//        return v;
//    }
//
//    public List<LabelModel> getItemList() {
//        return itemList;
//    }
//
//    public void setItemList(List<LabelModel> itemList) {
//        this.itemList = itemList;
//    }
//
//    public int getCount() {
//        if (itemList != null)
//            return itemList.size();
//        return 0;
//    }
//
//    public LabelModel getItem(int position) {
//        if (itemList != null)
//            return itemList.get(position);
//        return null;
//    }
//
//    public long getItemId(int position) {
//        if (itemList != null)
//            return itemList.get(position).hashCode();
//        return 0;
//    }
//}


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
//                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
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
        Log.d("SimpleAdapter", "POSITION : " + position);

        TextView text = (TextView) v.findViewById(R.id.label);
        TextView compteur = (TextView) v.findViewById(R.id.compteur);
        Log.d("SimpleAdapter", compteur.toString());
        Log.d("SimpleAdapter", "" + c.getNbImpressions());
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
                Log.d("SimpleAdapter", "DECREMENTATION !");
                c.decrementImpressions();
                this.inst.notifyDataSetChanged();
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            private SimpleAdapter inst = instance;
            @Override
            public void onClick(View view) {
                Log.d("SimpleAdapter", "INCREMENTATION !");
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