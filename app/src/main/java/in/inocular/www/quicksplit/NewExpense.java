package in.inocular.www.quicksplit;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class NewExpense extends Activity implements View.OnClickListener {

    Button addExpense,paidBy,owings;
    EditText title,total;
    final Context context = this;
    int[][] expense = new int[50][50];
    SharedPreferences prefs;
    String members,gid,uid,paid_owe="";
    int l,temp[];

    Bundle extras;
    int grpId,loc = 0,userowe=0;
    Spinner spinner;
    String grpName;
    String[] k;


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
//        paidBy = (Button) findViewById(R.id.paidBy);
        owings = (Button) findViewById(R.id.owings);

        title = (EditText) findViewById(R.id.title);
        total = (EditText) findViewById(R.id.total);

        prefs = getSharedPreferences("file", 0);
        members = prefs.getString("group_members", "");
        uid = prefs.getString("members_id", "");
        int user = prefs.getInt("user_id",0);
        String arr[] = uid.split(" ");
        Log.d("aabbb" + userowe +"  "+ loc, "");
        if(uid.compareTo("")!=0) {
            Log.d("aaaa" + uid + uid.length(), "");
            for (int i = 0; i < arr.length; i++) {
                Log.d("\n------------,,,,,,,," + arr[i], "");
                if (user == Integer.parseInt(arr[i])) {
                    loc = i;
                    break;
                }
            }
        }
        spinner = (Spinner) findViewById(R.id.spinner);

        //ArrayAdapter<String> adapter = ArrayAdapter.createFromResource(this, R.array.object_array, android.R.layout.simple_spinner_item);

        members = members + "Multiple_People...";
        k = members.split("%");
        l = k.length-1;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,k);
        spinner.setAdapter(adapter);

        temp = new int[k.length-1];
        for (int i=0;i<k.length-1;i++) {
            expense[0][i] = 0;
            expense[1][i] = 0;
        }
        userowe = expense[0][loc]-expense[1][loc];
        Log.d("aaaaa" + userowe +"  "+ loc, "");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == k.length-1) {
                    android.support.v7.app.AlertDialog.Builder builder =
                            new android.support.v7.app.AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);

                    Context context = getApplicationContext();
                    LinearLayout layout = new LinearLayout(context);
                    layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setPadding(20, 5, 20, 5);

                    for (int i = 0; i < k.length - 1; i++) {
                        LinearLayout subLayout1 = new LinearLayout(context);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.weight = 1;
                        subLayout1.setLayoutParams(layoutParams);
                        subLayout1.setGravity(Gravity.CENTER_VERTICAL);


                        final TextView t1 = new TextView(context);
                        t1.setText(k[i]);
                        t1.setTextColor(Color.parseColor("#C5070607"));
                        t1.setLayoutParams(new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.30f)));
                        t1.setTextSize(20);

                        //t1.setGravity(Gravity.BOTTOM);
                        subLayout1.addView(t1);


                        final EditText text1 = new EditText(context);
                        text1.setGravity(Gravity.TOP);
                        text1.setId(i);
                        text1.getBackground().setColorFilter(Color.parseColor("#449094"), PorterDuff.Mode.SRC_ATOP);
                        text1.setTextColor(Color.parseColor("#C5070607"));
                        text1.setInputType(InputType.TYPE_CLASS_NUMBER);
                        text1.setText(expense[0][i]+"");
                        text1.setLayoutParams(new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.70f)));

                        subLayout1.addView(text1);
                        layout.addView(subLayout1);
                    }
                    builder.setView(layout);

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Dialog f = (Dialog) dialog;
                            for (int m = 0; m < k.length - 1; m++) {

                                EditText text = (EditText) f.findViewById(m);
                                expense[0][m] = Integer.parseInt(text.getText().toString());
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setTitle("Multiple people paid");

                    android.support.v7.app.AlertDialog dialog = builder.create();
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                    window.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        extras = getIntent().getExtras();
        grpId = extras.getInt("Group_Id");
        grpName = extras.getString("Group_Name");

        gid = String.valueOf(grpId);
        new FetchFriends(NewExpense.this).execute(gid);



     //   Log.d("members",members);
     //   Log.d("members id",uid);

        addExpense.setOnClickListener(this);
//        paidBy.setOnClickListener(this);
        owings.setOnClickListener(this);

        //Toast.makeText(getApplicationContext(),grpId+"",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.owings:
                displayOwingsPopup();
                break;
            case R.id.addExpenseButton:
                add_expense();
                break;
        }
    }

    private void add_expense() {
        int pos = spinner.getSelectedItemPosition();
        String totalexpense = total.getText().toString();
        String expensetitle = title.getText().toString();

        prefs = getSharedPreferences("file", 0);
        members = prefs.getString("group_members", "");
        uid = prefs.getString("members_id", "");
        int user = prefs.getInt("user_id",0);
        String arr[] = uid.split(" ");
        Log.d("aabbb" + userowe +"  "+ loc, "");
        if(uid.compareTo("")!=0) {
            Log.d("aaaa" + uid + uid.length(), "");
            for (int i = 0; i < arr.length; i++) {
                Log.d("\n------------,,,,,,,," + arr[i], "");
                if (user == Integer.parseInt(arr[i])) {
                    loc = i;
                    break;
                }
            }
        }

        if (pos != k.length - 1) {
            for (int i=0;i<k.length-1;i++) {
                expense[0][i] = 0;
            }
            expense[0][pos] = Integer.parseInt(totalexpense);
        }

        for(int i=0;i<l;i++){
            paid_owe+=String.valueOf(expense[0][i])+" ";
            paid_owe+=String.valueOf(expense[1][i])+" ";
        }

        userowe = expense[0][loc]-expense[1][loc];
        Log.d("aaaaa" + userowe +"  "+ loc, "");

        Log.d("paid-owe",paid_owe);
        new AddExpense(NewExpense.this,grpName,userowe).execute(gid,expensetitle,totalexpense,grpName,uid,paid_owe);

        for(int i=0;i<k.length-1;i++) {
            Log.d("",expense[0][i] + " ---------" + expense[1][i]+"\n");
        }
        //Intent i = new Intent(this, GroupActivity.class);
        //startActivity(i);


        //new AddExpense(NewExpense.this).execute(gid,expensetitle,totalexpense,grpName);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void displayOwingsPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);

        Context context = getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20, 5, 20, 5);

        for (int i = 0; i < k.length - 1; i++) {
            LinearLayout subLayout1 = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            subLayout1.setLayoutParams(layoutParams);
            subLayout1.setGravity(Gravity.CENTER_VERTICAL);


            final TextView t1 = new TextView(context);
            t1.setText(k[i]);
            t1.setTextColor(Color.parseColor("#C5070607"));
            t1.setLayoutParams(new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.30f)));
            t1.setTextSize(20);

            //t1.setGravity(Gravity.BOTTOM);
            subLayout1.addView(t1);


            final EditText text1 = new EditText(context);
            text1.setGravity(Gravity.TOP);
            text1.setId(i);
            text1.getBackground().setColorFilter(Color.parseColor("#449094"), PorterDuff.Mode.SRC_ATOP);
            text1.setTextColor(Color.parseColor("#C5070607"));
            text1.setInputType(InputType.TYPE_CLASS_NUMBER);
            text1.setText(expense[1][i]+"");
            text1.setLayoutParams(new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.70f)));

            subLayout1.addView(text1);
            layout.addView(subLayout1);
        }
        builder.setView(layout);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Dialog f = (Dialog) dialog;
                for (int m = 0; m < k.length - 1; m++) {

                    EditText text = (EditText) f.findViewById(m);
                    expense[1][m] = Integer.parseInt(text.getText().toString());
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setTitle("Enter each person's share");

        AlertDialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        window.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);

    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
