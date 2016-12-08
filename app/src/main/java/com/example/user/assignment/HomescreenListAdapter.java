package com.example.user.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Jin Hui on 04-Dec-16.
 */

public class HomescreenListAdapter extends ArrayAdapter {

    private ListViewItemPost[] objects;

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    public HomescreenListAdapter(Context context, int resource, ListViewItemPost[] objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderPost viewHolder = null;
        ListViewItemPost listViewItem = objects[position];
        //int listViewItemType = getItemViewType(position);

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_entry_layout, parent, false);
            TextView textView1 = (TextView) convertView.findViewById(R.id.post_subject);
            TextView textView2 = (TextView) convertView.findViewById(R.id.post_category);
            textView1.setText(listViewItem.getQuestionSubject());
            textView2.setText(listViewItem.getCategory());
            //viewHolder = new ViewHolderPost(textView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderPost) convertView.getTag();
        }

        //viewHolder.getText().setText(listViewItem.getQuestionSubject());

        return convertView;
    }
}
