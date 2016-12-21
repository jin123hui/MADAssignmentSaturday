package com.example.user.assignment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscussionActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView ansList;
    List<Answer> answerList;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(view.getContext(), ReplyActivity.class);
                startActivity(intent);
            }
        });

        answerList = new ArrayList<>();
        ansList = (ListView) findViewById(R.id.list);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        readQuestion();
                                    }
                                }

        );
    }

    @Override
    public void onRefresh() {
        readQuestion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        readQuestion();
    }

    private void readQuestion() {
        try {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            Boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
            if (isConnected) {
                //new downloadCourse().execute(getResources().getString(R.string.get_course_url));
                downloadAnswer(this, getString(R.string.select_answer_url));
            } else {
                Toast.makeText(getApplication(), "Network is NOT available",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplication(),
                    "Error reading record:" + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void downloadAnswer(Context context, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        swipeRefreshLayout.setRefreshing(true);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            answerList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject questResponse = (JSONObject) response.get(i);
                                int id = questResponse.getInt("id");
                                int questId = questResponse.getInt("questId");
                                String content = questResponse.getString("content");
                                int up = questResponse.getInt("up");
                                int down = questResponse.getInt("down");
                                String date = questResponse.getString("date");
                                String studId = questResponse.getString("studId");

                                Answer answer = new Answer();
                                answer.setId(id);
                                answer.setQuesId(questId);
                                answer.setContent(content);
                                answer.setUp(up);
                                answer.setDown(down);
                                //answer.setDate();
                                answer.setStudId(studId);

                                answerList.add(answer);
                            }

                            loadQuestion();
                            /*if (pDialog.isShowing())
                                pDialog.dismiss();*/
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error:" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        /*if (pDialog.isShowing())
                            pDialog.dismiss();*/
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        queue.add(jsonObjectRequest);
    }

    private void loadQuestion() {
        final DiscussionListAdapter adapter = new DiscussionListAdapter(this, R.layout.content_discussion, answerList);
        ansList.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), "Count :" + answerList.size(), Toast.LENGTH_LONG).show();
    }
}
