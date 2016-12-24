package com.example.user.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.assignment.domain.SessionManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Jin Hui on 22-Dec-16.
 */

public class DiscussionListAdapter extends ArrayAdapter<Answer> {

    public DiscussionListAdapter(Context context, int resource, List<Answer> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        final Answer ans = getItem(position);

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.discussion, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.answerContent = (TextView) convertView.findViewById(R.id.answer_content);
            viewHolder.posterId = (TextView) convertView.findViewById(R.id.postuser_id);
            viewHolder.postDateTime = (TextView) convertView.findViewById(R.id.post_datetime);
            viewHolder.upVote = (Button) convertView.findViewById(R.id.btnUp);
            viewHolder.downVote = (Button) convertView.findViewById(R.id.btnDown);
            viewHolder.edit = (Button) convertView.findViewById(R.id.btnEdit);
            viewHolder.upVoteNum = (TextView) convertView.findViewById(R.id.upVotes);
            viewHolder.downVoteNum = (TextView) convertView.findViewById(R.id.downVotes);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(!ans.getStudId().equals(new SessionManager(getContext()).getUserDetails().get("id"))) {
            viewHolder.edit.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.edit.setVisibility(View.VISIBLE);
            viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Hi", Toast.LENGTH_SHORT).show();
                }
            });
        }

        viewHolder.upVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewHolder.downVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewHolder.answerContent.setText(ans.getContent());
        viewHolder.posterId.setText("Posted by " + ans.getStudName());
        viewHolder.posterId.setTag(ans.getStudId());
        viewHolder.upVoteNum.setText(ans.getUp()+"");
        viewHolder.downVoteNum.setText(ans.getDown()+"");
        viewHolder.postDateTime.setText("Posted on " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ans.getDate()));
        return convertView;
    }

    public class ViewHolder {
        TextView answerContent;
        TextView posterId;
        TextView postDateTime;
        TextView upVoteNum;
        TextView downVoteNum;
        Button upVote;
        Button downVote;
        Button edit;

    }

}
