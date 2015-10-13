package in.inocular.www.quicksplit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by goks on 29/9/15.
 */
public class RegisterUser extends Activity {
    EditText mail,pass;
    Button register;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mail = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.password);
        register = (Button)findViewById(R.id.register);

        email = mail.getText().toString();
        password = pass.getText().toString();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mail.getText().toString();
                password = pass.getText().toString();
                new UpdateEntry(RegisterUser.this).execute(email,password);
            }
        });
    }
    public void done(){
        finish();
    }
}

