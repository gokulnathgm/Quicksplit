package in.inocular.www.quicksplit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by goks on 29/9/15.
 */
public class RegisterUser extends Activity {
    EditText mail,pass,fullname,number;
    Button signup;
    String email,password,name,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mail = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.password);
        fullname = (EditText)findViewById(R.id.fullname);
        number = (EditText)findViewById(R.id.phonenumber);
        signup = (Button)findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mail.getText().toString();
                password = pass.getText().toString();
                name = fullname.getText().toString();
                phone = number.getText().toString();
                new UpdateEntry(RegisterUser.this).execute(email,password,name,phone);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}

