package com.example.user.assignment;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.assignment.domain.SessionManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReplyActivity extends AppCompatActivity {

    int questionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_cancel);
        //TextView textViewMessage = (TextView)findViewById(R.id.textViewMessage);

        Bundle extras = getIntent().getExtras();
        if(!extras.isEmpty()) {
            questionID = extras.getInt("QUESTION");
        } else {
            questionID = 0;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createAnswer(View view) {
        EditText replyContent = (EditText) findViewById(R.id.reply_content);

        Answer answer = new Answer();
        answer.setContent(replyContent.getText().toString());
        answer.setQuesId(questionID);
        answer.setStudId(new SessionManager(this).getUserDetails().get("id"));
        //answer.setStudName(new SessionManager(this).getUserDetails().get("name"));

        try {
            String url = getApplicationContext().getString(R.string.insert_answer_url);
            makeServiceCall(this, url, answer);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void makeServiceCall(Context context, String url, final Answer answer){
        //mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);

        //Send data
        try {
            StringRequest postRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "Posted new answer!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    //params.put("id", answer.getId()+"");
                    params.put("quesId", answer.getQuesId()+"");
                    params.put("content", answer.getContent());
                    params.put("up", answer.getUp()+"");
                    params.put("down", answer.getDown()+"");
                    //params.put("reviewTime", answer.getDate().toString());
                    params.put("studId", answer.getStudId());
                    //params.put("studentName", answer.getStudName())
;                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
