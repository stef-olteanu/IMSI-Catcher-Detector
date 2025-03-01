package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.licentav00.R;

import java.util.ArrayList;
import java.util.List;

import Model.Cell;
import Utils.MConstants;

public class CellListAdapter extends ArrayAdapter<Cell> implements Filterable {
    //region Members Declaration
    private static final String TAG = "CellListAdapter";
    private Context mContext;
    private int mResource;
    private ArrayList<Cell> mObjects;
    private ArrayList<Cell> mOrigObjects;

    static class ViewHolder {
        TextView cidTV;
        TextView latTV;
        TextView lonTV;
        ImageView listImage;
    }
    //endregion

    //region Constructor
    public CellListAdapter(Context context, int resource, ArrayList<Cell> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.mObjects = objects;
        this.mOrigObjects = new ArrayList<>();
        this.mOrigObjects.addAll(mObjects);
    }
    //endregion

    //region Public Methods

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String CID = getItem(position).GetCid();
        String LAT = getItem(position).GetCellLat();
        String LON = getItem(position).GetCellLong();
        String STATUS = getItem(position).getmCellStatus();

        Cell cell = new Cell();
        cell.setmCid(CID);
        cell.setmCellLat(LAT);
        cell.setmCellLong(LON);
        cell.setmCellStatus(STATUS);
        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            convertView = inflater.inflate(this.mResource,parent,false);

             holder = new ViewHolder();

            holder.cidTV = convertView.findViewById(R.id.cidList);;
            holder.latTV = convertView.findViewById(R.id.latList);
            holder.lonTV = convertView.findViewById(R.id.lonList);
            holder.listImage = convertView.findViewById(R.id.imageList);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.cidTV.setText(CID);
        holder.latTV.setText(LAT);
        holder.lonTV.setText(LON);
        switch (STATUS) {
            case MConstants.Cell.GOOD:
                holder.listImage.setImageResource(R.drawable.circlegreen);
                break;
            case MConstants.Cell.WARNING:
                holder.listImage.setImageResource(R.drawable.circleorange);
                break;
            case MConstants.Cell.ALERT:
                holder.listImage.setImageResource(R.drawable.circle);
                break;
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filteredResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filteredResults.values = mOrigObjects;
                    filteredResults.count = mOrigObjects.size();
                } else {
                    ArrayList<Cell> newFilteredList = new ArrayList<>();
                    for(Cell cell : mObjects) {
                        if(cell.GetCid().startsWith(constraint.toString())) {
                            newFilteredList.add(cell);
                        }
                    }
                    filteredResults.values = newFilteredList;
                    filteredResults.count = newFilteredList.size();
                }
                return filteredResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results.count == 0)
                    notifyDataSetInvalidated();
                else {
                    mObjects.clear();
                    mObjects.addAll((ArrayList<Cell>)results.values);
                    //mObjects = (ArrayList<Cell>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }

    public void ResetData(){
        this.mObjects.clear();
        this.mObjects.addAll(mOrigObjects);
    }


    //endregion



}
