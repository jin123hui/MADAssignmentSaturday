package com.example.user.assignment;

import android.widget.TextView;

/**
 * Created by Jin Hui on 04-Dec-16.
 */

public class ViewHolderPost {

    TextView text;

    public ViewHolderPost(TextView text) {
        this.text = text;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }

}
