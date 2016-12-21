package com.example.user.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jin Hui on 22-Dec-16.
 */

public class DiscussionListAdapter extends ArrayAdapter {

    public DiscussionListAdapter(Context context, int resource, List<Answer> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Answer answer = (Answer) getItem(position);

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.discussion, parent, false);
            TextView textView1 = (TextView) convertView.findViewById(R.id.question_content);
            TextView textView2 = (TextView) convertView.findViewById(R.id.postuser_id);
            TextView textView3 = (TextView) convertView.findViewById(R.id.post_datetime);
            textView1.setText(answer.getContent());
            textView2.setText(answer.getStudId());
            textView3.setText(answer.getDate().toString());

        } else {
            //viewHolder = (ViewHolderPost) convertView.getTag();
        }

        return convertView;
    }

}
