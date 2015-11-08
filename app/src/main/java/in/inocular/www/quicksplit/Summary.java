package in.inocular.www.quicksplit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Summary extends Fragment {


    RecyclerView mRecyclerView;
    static  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_summary,null);
        TextView getsBack = (TextView) view.findViewById(R.id.getsBack);
        TextView payBack = (TextView) view.findViewById(R.id.payBack);
        getsBack.setText("You gets Back: 600");
        payBack.setText("You need to pay: 100");
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
                Toast.makeText(getActivity(), " Clicked on Item " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }



    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        String[] names = {"Gokul Nath","Aravind Sai", "Divya", "Dhrisya"};
        for (int index = 0; index < 4; index++) {
            DataObject obj = new DataObject(names[index],"owes you",index*100);
            results.add(index, obj);
        }
        return results;
    }

}