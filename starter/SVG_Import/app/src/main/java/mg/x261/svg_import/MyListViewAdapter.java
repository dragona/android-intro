package mg.x261.svg_import;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyListViewAdapter extends ArrayAdapter<RowItem> {

    Context context;

    public MyListViewAdapter(Context context, int resourceId,
                             List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private static class ViewHolder {
        ImageView imageViewFlag;
        TextView txtCountryName;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_history, null);
            holder = new ViewHolder();

            holder.txtCountryName = (TextView) convertView.findViewById(R.id.tvCountryName);
            holder.imageViewFlag = (ImageView) convertView.findViewById(R.id.imageCountryFlag);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtCountryName.setText(rowItem.getCountryName());

        Log.d("TAG",rowItem.getFlagImageId());
        int imageId = context.getResources().getIdentifier(rowItem.getFlagImageId(), "drawable", context.getPackageName());
        holder.imageViewFlag.setImageResource(imageId);


        return convertView;
    }
}
