package in.inocular.www.quicksplit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class NewGroup extends ActionBarActivity implements View.OnClickListener {

    Button create;

    String[] menu;
    DrawerLayout dLayout;
    ListView dList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        create = (Button) findViewById(R.id.createGroup);
        create.setOnClickListener(this);

        menu = new String[]{"Home","Expenses","Create Group","Settings"};
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dList = (ListView) findViewById(R.id.left_drawer);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu);

        dList.setAdapter(adapter);
        dList.setSelector(android.R.color.holo_blue_dark);

        dList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

                /*dLayout.closeDrawers();
                Bundle args = new Bundle();
                args.putString("Menu", menu[position]);
                Fragment detail = new DetailFragment();
                detail.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.tabMode, detail).commit();*/

                switch (position) {
                    case 0:
                        launchHomeActivity();
                        return;
                    case 1:
                        return;
                    case 2:
                        createNewGroup();
                        return;
                    case 3:
                        return;
                    default:
                        return;
                }

            }

        });

    }


    void launchHomeActivity() {
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    void createNewGroup() {
        Intent i = new Intent(this, NewGroup.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_group, menu);
        return true;
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
