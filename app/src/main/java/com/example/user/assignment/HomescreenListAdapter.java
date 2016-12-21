package com.example.user.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jin Hui on 04-Dec-16.
 */

public class HomescreenListAdapter extends ArrayAdapter {

    public HomescreenListAdapter(Context context, int resource, List<Question> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Question question = (Question) getItem(position);

        ViewHolderPost viewHolder = null;
        //int listViewItemType = getItemViewType(position);

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_entry_layout, parent, false);
            TextView textView1 = (TextView) convertView.findViewById(R.id.post_subject);
            TextView textView2 = (TextView) convertView.findViewById(R.id.post_category);
            TextView textView3 = (TextView) convertView.findViewById(R.id.num_ans);
            textView1.setText(question.getSubject());
            textView2.setText(question.getCategory().toUpperCase());
            //viewHolder = new ViewHolderPost(textView);
            textView3.setText(countAns(question.getId()) + " ANS");
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderPost) convertView.getTag();
        }

        //viewHolder.getText().setText(listViewItem.getQuestionSubject());

        return convertView;
    }

    public int countAns(int id) {
        int numAns = 0;


        return numAns;
    }
}
