package com.example.user.assignment;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.user.assignment.Student;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_sign_up);
    }

    public void goBack(View view) {
        finish();
    }


    public void signUp(View view){
        EditText editEmail = (EditText) findViewById(R.id.email_signup);
        EditText editPassword1 = (EditText) findViewById(R.id.password_signup);
        EditText editPassword2 = (EditText) findViewById(R.id.confirm_password_signup);
        EditText editStudId = (EditText) findViewById(R.id.student_id_signup);
        EditText editTelNo = (EditText) findViewById(R.id.telephone_no_signup);
        EditText editName = (EditText)findViewById(R.id.display_name_signup);

        String email = editEmail.getText().toString();
        String password1 = editPassword1.getText().toString();
        String password2 = editPassword2.getText().toString();
        String studId = editStudId.getText().toString();
        String telNo = editTelNo.getText().toString();
        String name = editName.getText().toString();


        if(email.isEmpty()){
            editEmail.setError("Required field!!");
            editEmail.requestFocus();
            return;
        }
        if(password1.isEmpty()){
            editPassword1.setError("Required field!!");
            editPassword1.requestFocus();
            return ;

        }
        if(studId.isEmpty()){
            editEmail.setError("Required field!!");
            editEmail.requestFocus();
            return ;

        }

        if(telNo.isEmpty()){
            editTelNo.setError("Required field!!");
            editTelNo.requestFocus();
            return ;

        }

        if(name.isEmpty()){
            editName.setError("Required field!!");
            editName.requestFocus();
            return ;
        }
        if(!password1.equals(password2)){
            editPassword2.setError("Both password are not match!!");
            editPassword2.requestFocus();
            return ;
        }

        if(!email.contains("@") || !email.contains("tarc.edu.my")){
            editEmail.setError("Invalid email format!! (must contain @ and tarc.edu.my)");
            editEmail.requestFocus();
            return ;
        }

        if(!telNo.matches("^([0-9]{3})[-]([0-9]{7,9})+$")){
            editTelNo.setError("Invalid handphone format!!");
            editTelNo.requestFocus();
            return ;
        }


        Student student = new Student();
        student.setEmail(email.toLowerCase());
        student.setPassword(password1);
        student.setTelNo(telNo);
        student.setStudId(studId.toUpperCase());
        student.setName(name);

        try {
            String url = getApplicationContext().getString(R.string.insert_student_url);
            makeServiceCall(this, url , student);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }



    }

    public void makeServiceCall(Context context, String url, final Student student){
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
                            try {
                                JSONObject obj = new JSONObject(response);
                                String msg = obj.getString("success");
                                String error = obj.getString("message");
                                if(Integer.parseInt(msg) > 0){
                                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                                }


                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
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
                    params.put("studId",student.getStudId());
                    params.put("name",student.getName());
                    params.put("email",student.getEmail());
                    params.put("password",student.getPassword());
                    params.put("telNo",student.getTelNo());
                    return params;
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

    public void cancel(View view) {
        finish();
    }


}
