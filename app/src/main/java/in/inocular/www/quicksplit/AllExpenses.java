package in.inocular.www.quicksplit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by anil on 8/11/15.
 */
public class AllExpenses extends Fragment {


    int result=0;

    RecyclerView mRecyclerView;
    static  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.all_expenses,null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                //Log.i(LOG_TAG, " Clicked on Item " + position);
                //       Toast.makeText(getActivity(), " Clicked on Item " + position, Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }



    private ArrayList<DataObject> getDataSet() {
        SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences("file", 0);
        int n = prefs.getInt("number_of_transactions", 0);
        ArrayList results = new ArrayList<DataObject>();
        String message,names[] = new String[n];
        int[] owe = new int[n];

        for (int index = 0; index < n; index++) {
            String temp = prefs.getString("title" + index, "");
            names[index] = temp;
            owe[index] = prefs.getInt("owings" + index, 0);
            //  System.out.println(" || " + names + " - - - " + owe + " | |");
        }


        for (int i=0;i<n;i++) {
            int t = Math.abs(owe[i]);
            String grp;
                if (owe[i]<0) {
                    grp = "You owes";
                } else {
                    grp = "You gets back";
                }
                    DataObject obj = new DataObject(names[i], grp ,t);
                    results.add(i, obj);
        }


        return results;
    }
}
