package in.inocular.www.quicksplit;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class NewFriend extends ActionBarActivity {

    int itemId;
    String grpName;

    private Toolbar toolbar;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<ContactDetails> studentList;

    ArrayList name1 = new ArrayList();
    ArrayList phno1 = new ArrayList();

    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);


        Bundle extras = getIntent().getExtras();
        itemId = extras.getInt("Group_Id");
        grpName = extras.getString("Group_Name");

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        studentList = new ArrayList<ContactDetails>();
        //Toast.makeText(getApplicationContext(),itemId + "",Toast.LENGTH_SHORT).show();

        /*final EditText userid;
        userid = (EditText)findViewById(R.id.userid);*/
        add = (Button)findViewById(R.id.addfriend);


        getAllContacts(this.getContentResolver());
        for (int i = 1; i < name1.size(); i++) {
            //ContactDetails st = new ContactDetails("Student " + i, "androidstudent" + i
//                    + "@gmail.com", false);
            ContactDetails st = new ContactDetails(name1.get(i).toString(), phno1.get(i).toString(), false);
            studentList.add(st);
        }

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Select Friends");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create an Object for Adapter
        mAdapter = new CardViewDataAdapter(studentList);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String data = "";
                List<ContactDetails> stList = ((CardViewDataAdapter) mAdapter)
                        .getStudentist();

                for (int i = 0; i < stList.size(); i++) {
                    ContactDetails singleStudent = stList.get(i);
                    if (singleStudent.isSelected() == true) {

                        data = data + "\n" + singleStudent.getName().toString();
      /*
       * Toast.makeText( CardViewActivity.this, " " +
       * singleStudent.getName() + " " +
       * singleStudent.getEmailId() + " " +
       * singleStudent.isSelected(),
       * Toast.LENGTH_SHORT).show();
       */
                    }

                }

                Toast.makeText(NewFriend.this,
                        "Selected Students: \n" + data, Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

    public void getAllContacts(ContentResolver cr) {

        Cursor phones = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            name1.add(name);
            phno1.add(phoneNumber);
        }

        phones.close();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(this,GroupActivity.class);
        intent.putExtra("Group_Id",itemId);
        intent.putExtra("Group_Name",grpName);
        startActivity(intent);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
