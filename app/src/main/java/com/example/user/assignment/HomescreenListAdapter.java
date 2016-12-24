package com.example.user.assignment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jin Hui on 04-Dec-16.
 */

public class HomescreenListAdapter extends ArrayAdapter<Question> {

    public HomescreenListAdapter(Context context, int resource, List<Question> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_entry_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.category = (TextView) convertView.findViewById(R.id.post_category);
            viewHolder.subject = (TextView) convertView.findViewById(R.id.post_subject);
            viewHolder.numAns = (TextView) convertView.findViewById(R.id.num_ans);
            viewHolder.timePosted = (TextView) convertView.findViewById(R.id.time_posted);

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Question ques = getItem(position);
        viewHolder.category.setText(ques.getCategory().toUpperCase());
        viewHolder.subject.setText(ques.getSubject());
        viewHolder.numAns.setText(ques.getNumAns() + "ANS");
        viewHolder.timePosted.setText(calcTimePosted(ques.getPostedTime()));
        return convertView;
    }

    public class ViewHolder {
        TextView subject;
        TextView category;
        TextView numAns;
        TextView timePosted;
    }

    public String calcTimePosted(Date postedTime) {
        String timePassed;

        Date dd = postedTime;
        Date d = postedTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            d = sdf.parse(dd.toString());
        } catch (ParseException ex) {
           // Toast.makeText("Date error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        Date today = new Date();

        long diff = today.getTime() - d.getTime();
        long diffSeconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60;
        long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;
        long diffHours = TimeUnit.MILLISECONDS.toHours(diff) % 24;
        long diffDays = TimeUnit.MILLISECONDS.toDays(diff);

        if(diffDays>365)
            timePassed = (int)(diffDays/365) + "YR";
        else if(diffDays>7)
            timePassed = (int)(diffDays/7) + "WK";
        else if(diffDays>0)
            timePassed = diffDays + "DAY";
        else if(diffHours>0)
            timePassed = diffHours + "HR";
        else if(diffMinutes>0)
            timePassed = diffMinutes + "MIN";
        else
            timePassed = diffSeconds + "SEC";


        return timePassed;
    }

}
