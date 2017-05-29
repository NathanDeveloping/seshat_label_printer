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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import seshat.seshatlabel.R;
import seshat.seshatlabel.models.LabelModel;

/**
 * Adapter liste LabelModel -> ListView
 */
public class SimpleAdapter extends ArrayAdapter<LabelModel> {

    public HashMap<String,String> checked = new HashMap<String,String>();

    public void setCheckedItem(int item) {
        if (checked.containsKey(String.valueOf(item))){
            checked.remove(String.valueOf(item));
        } else {
            checked.put(String.valueOf(item), String.valueOf(item));
        }
    }

    public HashMap<String, String> getCheckedItems(){
        return checked;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView label;
        CheckBox checkbox;
        ListView listview;
    }

    private List<LabelModel> itemList;
    private Context context;

    public SimpleAdapter(List<LabelModel> itemList, Context ctx) {
        super(ctx, android.R.layout.simple_list_item_1, itemList);
        this.itemList = itemList;
        this.context = ctx;
    }

    public int getCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }

    public LabelModel getItem(int position) {
        if (itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ListView lv = null;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_detail, null);
        } else {
        }
        final LabelModel c = (LabelModel) getItem(position);

        TextView text = (TextView) v.findViewById(R.id.label);
        text.setText(c.getLabel());

        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setChecked(checkBox.isChecked());
            }
        });
        return v;

    }

    public List<LabelModel> getItemList() {
        return itemList;
    }

    public void setItemList(List<LabelModel> itemList) {
        this.itemList = itemList;
    }

}