package in.inocular.www.quicksplit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

/**
 * Created by goks on 29/9/15.
 */
public class Home extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


         //Setup the DrawerLayout and NavigationView

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                int itemId = menuItem.getItemId();
                String grpName = menuItem.getTitle().toString();
                switch(itemId) {
                    case R.id.nav_item_home:
                        break;
                    case R.id.nav_item_allExpense :
                        break;
                    default:
                        launchGroupActivity(itemId,grpName);
                        //Toast.makeText(getApplicationContext(), itemId + "", Toast.LENGTH_SHORT).show();
                        break;
                }
/*
                if (menuItem.getItemId() == R.id.nav_item_inbox) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                }*/

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        final Menu navMenu = mNavigationView.getMenu();
        SharedPreferences menuItems = getSharedPreferences("file", 0);
        int num = menuItems.getInt("number_of_groups", 0);
        String[] grp_names = new String[num];
        int[] grp_id = new int[num];
        final SubMenu subMenu = navMenu.addSubMenu("GROUPS");
        for (int i=0;i<num;i++) {
            grp_id[i] = menuItems.getInt("group_id" + i, 121);
            grp_names[i] = menuItems.getString("group_name" + i, "No group");

            MenuItem item = subMenu.add(0, grp_id[i], i, grp_names[i]);
            item.setIcon(R.drawable.abc_btn_radio_to_on_mtrl_015);

        }

        for (int i=0,count = mNavigationView.getChildCount();i<count;i++){
            final View child = mNavigationView.getChildAt(i);
            if (child!= null && child instanceof ListView) {
                final ListView menuView = (ListView) child;
                final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
                final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
                wrapped.notifyDataSetChanged();
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);



        mDrawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void launchAddExpenseActivity(MenuItem item) {
        Intent intent = new Intent(this,NewExpense.class);
        startActivity(intent);
    }

    public void launchCreateNewGroupActivity(MenuItem item) {
        Intent intent = new Intent(this,NewGroup.class);
        startActivity(intent);
    }

    public void launchGroupActivity(int itemId,String grpName) {
        new FetchAllOwings(Home.this,grpName).execute(String.valueOf(itemId));
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }
}

