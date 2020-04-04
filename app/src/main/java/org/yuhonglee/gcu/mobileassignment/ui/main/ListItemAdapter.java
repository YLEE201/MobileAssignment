package org.yuhonglee.gcu.mobileassignment.ui.main;
//Name.Yu Hong Lee
//StD No.S1620580

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.yuhonglee.gcu.mobileassignment.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//extends base adapter for Filterable which allows the filtering class
public class ListItemAdapter extends BaseAdapter implements Filterable {
    //getters and setter variables for the xml pull parser
    private String title = "";
    private String description = "";
    private Date Startime = null;
    private Date Endtime = null;
    private String delay = "";
    private Double LongGeo = null;
    private Double LatGeo = null;
    private String PubDate = "";
    private String difference = "";

    //for the expand class
    private boolean expand = false;

    //Filtering variables
    private ArrayList<String> mStringList;
    private ArrayList<String> mStringFilterList;
    private LayoutInflater mInflater;
    private ValueFilter valueFilter;

    public ListItemAdapter() {

    }

    //--------------------------------------------------------------------------------------------------
    //filtering code
    //How many items are in the data set represented by this Adapter.
    @Override
    public int getCount() {
        return mStringList.size();
    }

    //Get the data item associated with the specified position in the data set.
    @Override
    public Object getItem(int position) {
        return mStringList.get(position);
    }

    //Get the row id associated with the specified position in the list.
    @Override
    public long getItemId(int position) {
        return position;
    }

    //Get a View that displays the data at the specified position in the data set.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder viewHolder;

        if(convertView==null) {
            viewHolder=new Holder();
            convertView=mInflater.inflate(R.layout.row,null);
            viewHolder.nameTv=(TextView)convertView.findViewById(R.id.listtext);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(Holder)convertView.getTag();
        }
        viewHolder.nameTv.setText(mStringList.get(position).toString());

        return convertView;
    }

    private class  Holder{
        TextView nameTv;
    }

    //Returns a filter that can be used to constrain data with a filtering pattern.
    @Override
    public Filter getFilter() {
        if(valueFilter==null) {
            valueFilter=new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {
        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();

            if(constraint!=null && constraint.length()>0){
                ArrayList<String> filterList=new ArrayList<String>();
                for(int i=0;i<mStringFilterList.size();i++){
                    if(mStringFilterList.get(i).contains(constraint)) {
                        filterList.add(mStringFilterList.get(i));
                    }
                }

                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=mStringFilterList.size();
                results.values=mStringFilterList;
            }
            return results;
        }

        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mStringList=(ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }
    //--------------------------------------------------------------------------------------------------
    //pull parser getters and setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getStarttime() {
        return Startime;
    }
    public void setStartime(Date Startime) {
        this.Startime = Startime;
    }
    public Date getEndtime() {
        return Endtime;
    }
    public void setEndtime(Date Endtime) {
        this.Endtime = Endtime;
    }
    public String getDifference() {
        return difference;
    }
    public void setDifference(String difference) {
        this.difference = difference;
    }
    public String getDelay() {
        return delay;
    }
    public void setDelay(String delay) {
        this.delay = delay;
    }
    public Double getLongGeo() {
        return LongGeo;
    }
    public void setLongGeo(Double LongGeo) {
        this.LongGeo = LongGeo;
    }
    public Double getLatGeo() {
        return LatGeo;
    }
    public void setLatGeo(Double LatGeo) {
        this.LatGeo = LatGeo;
    }
    public String getPubDate() {
        return PubDate;
    }
    public void setPubDate(String PubDate) {
        this.PubDate = PubDate;
    }


    //getters and setters for expanding the items
    public boolean getExpand() {
        if (expand == false) {
            return expand = false;
        } else {
            return expand = true;
        }
    }
    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    //calculates the difference of start date and end date
    public static Map<TimeUnit,Long> computeDiff(Date date1, Date date2) {

        long diffInMillies = date2.getTime() - date1.getTime();

        //create the list
        List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS));
        Collections.reverse(units);

        //create the result map of TimeUnit and difference
        Map<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
        long milliesRest = diffInMillies;

        for ( TimeUnit unit : units ) {

            //calculate difference in millisecond
            long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;

            //put the result in the map
            result.put(unit,diff);
        }

        return result;
    }

    //returns the formatted items either as an expanded or collapse
    @Override
    public String toString() {
        String s = "";
        if (getExpand() == true) {
            if (description.isEmpty())
            {
                s = title + "\n" + "\n" + "Start Time: " +Startime + "\n"+ "\n" + "End Time: " + Endtime + "\n" + "\n" + delay + "\n" +"\n"  + "Duration: " + difference +"\n" + "\n"+ "Location; " + "\n" + LatGeo + "\n" + LongGeo + "\n" + "\n"  + PubDate;
            }
            else {
                s = title + "\n" + "\n" + description + "\n" + "\n"+ "Location; " + "\n" + LatGeo + "\n" + LongGeo + "\n" + "\n"  + PubDate;
            }
        } else if (getExpand() == false) {
            s = title  + "\n" + PubDate;
        }
        return s;
    }
}
