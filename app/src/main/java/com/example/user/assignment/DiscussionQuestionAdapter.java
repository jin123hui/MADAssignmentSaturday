package com.example.user.assignment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.assignment.domain.SessionManager;

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

public class DiscussionQuestionAdapter extends ArrayAdapter<Question> {

    public DiscussionQuestionAdapter(Context context, int resource, List<Question> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.question, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.category = (TextView) convertView.findViewById(R.id.tvCategory);
            viewHolder.subject = (TextView) convertView.findViewById(R.id.tvSubject);
            viewHolder.content = (TextView) convertView.findViewById(R.id.tvContent);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.postuser_id2);
            viewHolder.timePosted = (TextView) convertView.findViewById(R.id.post_datetime2);
            viewHolder.favorite = (Button) convertView.findViewById(R.id.btnFavorite);
            viewHolder.editQ = (Button) convertView.findViewById(R.id.btnEditQ);
            viewHolder.favorite.setTag(1);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Question ques = getItem(position);
        if(!ques.getStudId().equals(new SessionManager(getContext()).getUserDetails().get("id"))) {
            viewHolder.editQ.setVisibility(View.INVISIBLE);
            viewHolder.editQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Edit", Toast.LENGTH_SHORT).show();
                }
            });
        }

        viewHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((Integer)viewHolder.favorite.getTag())==1) {
                    viewHolder.favorite.setBackgroundResource(R.mipmap.ic_faved);
                    Toast.makeText(getContext(), "Favourited", Toast.LENGTH_SHORT).show();
                    viewHolder.favorite.setTag(2);
                } else {
                    viewHolder.favorite.setBackgroundResource(R.mipmap.ic_fav);
                    Toast.makeText(getContext(), "Unfavourite", Toast.LENGTH_SHORT).show();
                    viewHolder.favorite.setTag(1);
                }
            }
        });

        viewHolder.category.setText(ques.getCategory().toUpperCase());
        viewHolder.subject.setText(ques.getSubject());
        viewHolder.content.setText(ques.getContent());
        viewHolder.userName.setText("Posted by " + ques.getStudName());
        viewHolder.userName.setTag(ques.getStudId());
        viewHolder.timePosted.setText("Posted on " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ques.getPostedTime()));
        return convertView;
    }

    public class ViewHolder {
        TextView category;
        TextView subject;
        TextView content;
        TextView userName;
        TextView timePosted;
        Button favorite;
        Button editQ;
    }


}
