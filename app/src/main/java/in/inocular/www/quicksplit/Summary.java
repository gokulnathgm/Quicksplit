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
import android.widget.TextView;

import java.util.ArrayList;


public class Summary extends Fragment {

    int result=0;

    RecyclerView mRecyclerView;
    static  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_summary,null);
        TextView getsBack = (TextView) view.findViewById(R.id.getsBack);
       // TextView payBack = (TextView) view.findViewById(R.id.payBack);

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

        if (result>0)
            getsBack.setText("You gets Back: " + "\u20B9" + result);
        else if (result<0)
            getsBack.setText("You needs to pay: " + "\u20B9" + ((-1)*result));
        else
            getsBack.setText("All your balances have been settled");
        //payBack.setText("You need to pay: 100");

        return view;
    }



    private ArrayList<DataObject> getDataSet() {
        SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences("file", 0);
        int n = prefs.getInt("number_of_members", 0);
        ArrayList results = new ArrayList<DataObject>();
        String message,names[] = new String[n]; // = {"Gokul Nath","Aravind Sai", "Divya", "Dhrisya"};
        int[] owe = new int[n];
        //System.out.println();
        for (int index = 0; index < n; index++) {
            names[index] = prefs.getString("member" + index, "");
            //names[index] = temp[0];
            owe[index] = prefs.getInt("owe" + index, 0);
            //  System.out.println(" || " + names + " - - - " + owe + " | |");
        }
        GroupCalculations gC = new GroupCalculations();
        int[][] balances = gC.getTransactionsFromNetOwings(owe);


        for (int i=0,t=0;i<n;i++) {
            for (int j=i;j<n;j++) {
                if (balances[i][j]<0) {
                    DataObject obj = new DataObject(names[i] + " owes " + names[j],"",Math.abs(balances[i][j]));
                    results.add(t, obj);
                    t++;
                } else if (balances[i][j] > 0) {
                    DataObject obj = new DataObject(names[j] + " owes " + names[i],"",Math.abs(balances[i][j]));
                    results.add(t, obj);
                    t++;
                }
                if (i==0) {
                    result += balances[0][j];
                }
            }

        }


        return results;
    }

}