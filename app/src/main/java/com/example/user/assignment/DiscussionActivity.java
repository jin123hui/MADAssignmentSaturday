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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class DiscussionActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView ansList;
    List<Answer> answerList;
    ListView quesList;
    List<Question> questionList;
    private SwipeRefreshLayout swipeRefreshLayout;
    int questionID = -1;

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
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent = new Intent(view.getContext(), ReplyActivity.class);
                intent.putExtra("QUESTION", questionID);
                startActivity(intent);
            }
        });


            Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            if (!extras.isEmpty()) {
                questionID = extras.getInt("QUESTION");
            } else {
                questionID = 0;
            }
        } else {

        }

        answerList = new ArrayList<>();
        ansList = (ListView) findViewById(R.id.list);
        //ansList.setOnClickListener();

        questionList = new ArrayList<>();
        quesList = (ListView) findViewById(R.id.question);

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
                printQuestion(this, getString(R.string.select_one_question_url));
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

        StringRequest postRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray questResponse = new JSONArray(response);
                            answerList.clear();
                            for (int i = 0; i < questResponse.length(); i++) {
                                if (questResponse.getJSONObject(i) != null) {
                                    JSONObject courseResponse = questResponse.getJSONObject(i);
                                    int id = courseResponse.getInt("id");
                                    int questId = courseResponse.getInt("quesId");
                                    String content = courseResponse.getString("content");
                                    String studId = courseResponse.getString("studId");
                                    String studName = courseResponse.getString("studName");
                                    int up = courseResponse.getInt("up");
                                    int down = courseResponse.getInt("down");
                                    String date = courseResponse.getString("reviewTime");

                                    Date d = new Date();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    try {
                                        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                                        d = sdf.parse(date);
                                    } catch (ParseException ex) {
                                        Toast.makeText(getApplicationContext(), "Date error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                    Answer answer = new Answer();
                                    answer.setId(id);
                                    answer.setQuesId(questId);
                                    answer.setContent(content);
                                    answer.setStudId(studId);
                                    answer.setStudName(studName);
                                    answer.setUp(up);
                                    answer.setDown(down);
                                    answer.setDate(d);

                                    answerList.add(answer);
                                }
                            }

                            loadAnswer();

                            swipeRefreshLayout.setRefreshing(false);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "BLABLA: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error: " + volleyError.getMessage(), Toast.LENGTH_LONG).show();

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("quesId", Integer.toString(questionID));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        queue.add(postRequest);
    }

    private void loadAnswer() {
        final DiscussionListAdapter adapter = new DiscussionListAdapter(this, R.layout.content_discussion, answerList);
        ansList.setAdapter(adapter);
        //Toast.makeText(getApplicationContext(), "Count :" + answerList.size(), Toast.LENGTH_LONG).show();
    }

    private void printQuestion(Context context, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        //swipeRefreshLayout.setRefreshing(true);

        StringRequest postRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray questResponse = new JSONArray(response);
                            questionList.clear();
                            for (int i = 0; i < questResponse.length(); i++) {
                                if (questResponse.getJSONObject(i) != null) {
                                    JSONObject courseResponse = questResponse.getJSONObject(i);

                                    int id = courseResponse.getInt("id");
                                    String subject = courseResponse.getString("subject");
                                    String content = courseResponse.getString("content");
                                    String category = courseResponse.getString("category");
                                    String postedTime = courseResponse.getString("postedTime");
                                    String studId = courseResponse.getString("studId");
                                    String studName = courseResponse.getString("studentName");

                                    Date d = new Date();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    try {
                                        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                                        d = sdf.parse(postedTime);
                                    } catch (ParseException ex) {
                                        Toast.makeText(getApplicationContext(), "Date error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                    Question question = new Question();
                                    question.setId(id);
                                    question.setSubject(subject);
                                    question.setContent(content);
                                    question.setCategory(category);
                                    question.setPostedTime(d);
                                    question.setStudId(studId);
                                    question.setStudName(studName);
                                    //question.setNumAns(countAns(getApplicationContext(), getString(R.string.count_answer), id));

                                    questionList.add(question);
                                }
                            }
                            loadQuestion();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error: " + volleyError.getMessage(), Toast.LENGTH_LONG).show();

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("quesId", Integer.toString(questionID));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        queue.add(postRequest);
    }

    private void loadQuestion() {
        final DiscussionQuestionAdapter adapter = new DiscussionQuestionAdapter(this, R.layout.content_discussion, questionList);
        quesList.setAdapter(adapter);
        //Toast.makeText(getApplicationContext(), "Count :" + answerList.size(), Toast.LENGTH_LONG).show();
    }

}
