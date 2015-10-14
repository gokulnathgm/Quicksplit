package in.inocular.www.quicksplit;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class GroupActivity extends ActionBarActivity {

    String[] menu;
    DrawerLayout dLayout;
    ListView dList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

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
        finish();
    }

    void createNewGroup() {
        Intent i = new Intent(this, NewGroup.class);
        startActivity(i);
    }

    void newExpense() {
        Intent i = new Intent(this, NewExpense.class);
        startActivity(i);
    }

    /*
    void newFriend() {
        Intent i = new Intent(MainActivity.this, LocationFound.class);
        startActivity(i);
    }
*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                newExpense();
                return true;
            case R.id.action_add_friend:


                return true;
            case R.id.action_all_expenses:
                // refresh
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
