package in.inocular.www.quicksplit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NewGroup extends Activity implements View.OnClickListener {

    Button create,cancel;
    EditText name;
    String gname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        name = (EditText) findViewById(R.id.groupname);
        create = (Button) findViewById(R.id.createGroup);
        cancel = (Button) findViewById(R.id.cancel);
        create.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.createGroup:
                gname = name.getText().toString();
                new CreateGroup(NewGroup.this).execute(gname);
                break;
            case R.id.cancel:
                Intent intent = new Intent(this,GroupActivity.class);
                startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
