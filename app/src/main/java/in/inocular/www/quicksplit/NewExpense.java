package in.inocular.www.quicksplit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;


public class NewExpense extends Activity implements View.OnClickListener {

    Button addExpense,paidBy,owings;
    EditText title,total;
    final Context context = this;
    int[][] expense = new int[5][5];



    Bundle extras;
    int grpId;
    String grpName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
/*

        ListView listView = (ListView) findViewById(R.id.listView);
        String[] valuesForListView = {"Anil paid ", "Gokul paid", "Sai paid", "Divya paid", "Dhrisya paid"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_view_for_popup,R.id.nameOfGroupMember,valuesForListView);
        listView.setAdapter(adapter);*/

        addExpense = (Button) findViewById(R.id.addExpenseButton);
        paidBy = (Button) findViewById(R.id.paidBy);
        owings = (Button) findViewById(R.id.owings);

        title = (EditText) findViewById(R.id.title);
        total = (EditText) findViewById(R.id.total);

        extras = getIntent().getExtras();
        grpId = extras.getInt("Group_Id");
        grpName = extras.getString("Group_Name");


        addExpense.setOnClickListener(this);
        paidBy.setOnClickListener(this);
        owings.setOnClickListener(this);

        //Toast.makeText(getApplicationContext(),grpId+"",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addExpenseButton:
                Intent i = new Intent(this, GroupActivity.class);
                startActivity(i);
                break;
            case R.id.paidBy:
                displayPaymentPopup();
                break;
            case R.id.owings:
                displayOwingsPopup();
                break;
        }
    }

    private void displayOwingsPopup() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View popUpView = inflater.inflate(R.layout.payment_popup, null);


        final ListView listView = (ListView) popUpView.findViewById(R.id.listView);
        String[] valuesForListView = {"Anil owes ", "Gokul owes", "Sai owes", "Divya owes", "Dhrisya owes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_view_for_popup,R.id.nameOfGroupMember,valuesForListView);
        listView.setAdapter(adapter);



        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(popUpView);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for (int i = 0; i < 5; i++) {
                    View view = listView.getChildAt(i);
                    EditText text = (EditText) view.findViewById(R.id.value);
                    expense[1][i] = Integer.parseInt(text.getText().toString());
                }
            }
        });



        AlertDialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        int width = (int) (getResources().getDisplayMetrics().widthPixels*0.80);
        window.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);


    }

    private void displayPaymentPopup() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View popUpView = inflater.inflate(R.layout.payment_popup, null);


        final ListView listView = (ListView) popUpView.findViewById(R.id.listView);
        String[] valuesForListView = {"Anil paid ", "Gokul paid", "Sai paid", "Divya paid", "Dhrisya paid"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_view_for_popup,R.id.nameOfGroupMember,valuesForListView);
        listView.setAdapter(adapter);


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(popUpView);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < 5; i++) {
                    View view = listView.getChildAt(i);
                    EditText text = (EditText) view.findViewById(R.id.value);
                    expense[0][i] = Integer.parseInt(text.getText().toString());
                }
            }
        });
                AlertDialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        int width = (int) (getResources().getDisplayMetrics().widthPixels*0.80);
        window.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
