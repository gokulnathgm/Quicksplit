package in.inocular.www.quicksplit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class NewExpense extends Activity implements View.OnClickListener {

    Button addExpense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        addExpense = (Button) findViewById(R.id.addExpenseButton);



        addExpense.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, GroupActivity.class);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
