package com.nest.linka;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.nest.linka.Util.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private  EditText username;
    private  EditText email;
    private  EditText password;
    private TextView  back_button;
    private Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.register_username);
        email = (EditText) findViewById(R.id.register_email);
        password = (EditText) findViewById(R.id.register_password);
        back_button = (TextView) findViewById(R.id.back_button_register);
        register_button =(Button) findViewById(R.id.register_button);

        register_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        register();
                    }
                }
        );

        back_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );


    }


    public void register(){
        String mUsername = username.getText().toString();
        final String mail = email.getText().toString();
        String passwd = password.getText().toString();

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage(error.getMessage())
                        .setNegativeButton("retry",null)
                        .create()
                        .show();
            }
        };

        Response.Listener<String> responselistener = new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){

                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        intent.putExtra("email",mail);
                        startActivityForResult(intent,23);

                    }else{

                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("Registration Failed")
                                .setNegativeButton("retry",null)
                                .create()
                                .show();
                    }


                } catch (JSONException e) {
                    //e.printStackTrace();
                    Toast.makeText(RegisterActivity.this,"Connection failed",Toast.LENGTH_LONG).show();
                }
            }
        };

        RegisterRequest registerRequest = new RegisterRequest(mUsername, mail, passwd, responselistener,errorListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerRequest);


       // Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        //startActivityForResult(intent,23);
    }
}
