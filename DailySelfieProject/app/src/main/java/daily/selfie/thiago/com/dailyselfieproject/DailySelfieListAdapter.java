package daily.selfie.thiago.com.dailyselfieproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DailySelfieListAdapter extends BaseAdapter {

    private List<DailySelfie> dailySelfies = new ArrayList<DailySelfie>();
    private Context mContext = null;

    public DailySelfieListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return dailySelfies.size();
    }

    @Override
    public Object getItem(int position) {
        return dailySelfies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView = convertView;
        ViewHolder holder;

        if (null == convertView) {
            holder = new ViewHolder();
            newView = LayoutInflater.from(mContext)
                    .inflate(R.layout.daily_selfie_item, parent, false);
            holder.pictureTaken = (ImageView) newView.findViewById(R.id.picture_taken);
            holder.nameOfPicureTaken = (TextView) newView.findViewById(R.id.picture_name);
            newView.setTag(holder);

        } else {
            holder = (ViewHolder) newView.getTag();
        }

        holder.pictureTaken.setImageBitmap(dailySelfies.get(position).getPictureTaken());
        holder.nameOfPicureTaken.setText(dailySelfies.get(position).getNameOfPictureTaken());

        return newView;
    }

    static class ViewHolder {
        ImageView pictureTaken;
        TextView nameOfPicureTaken;
    }

    public void add(DailySelfie dailySelfie ) {
        dailySelfies.add(dailySelfie);
        this.notifyDataSetChanged();
    }

    public void setDailySelfies(List<DailySelfie> dailySelfies) {
        this.dailySelfies = dailySelfies;
        this.notifyDataSetChanged();
    }

    public void remove(DailySelfie dailySelfie) {
        dailySelfies.remove(dailySelfie);
        this.notifyDataSetChanged();
    }
}
