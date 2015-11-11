package in.inocular.www.quicksplit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    TextView forgot,register;
    Button login;
    EditText email,password;
    String mail,pass;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forgot = (TextView) findViewById(R.id.forgot);
        register = (TextView) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity.this, RegisterUser.class);
                startActivity(j);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail = email.getText().toString();
                pass = password.getText().toString();

                new SigninActivity(MainActivity.this,mail).execute(mail, pass);
            }
        });

        prefs = getSharedPreferences("file",0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("logged_in",false);

    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
