package com.liveinbits.androidpagination;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> list;
    CustomAdapter adapter;
    boolean listener=false;
    LinearLayoutManager manager;
    int visibleitem, totalitem, hiddenitem;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        progressBar=findViewById(R.id.prgressbar);
        recyclerView.hasFixedSize();
        manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        String [] s={"20","21","34","44","43","55","54","66","76","88","98","100","78","89","1002","1212","2323"};
        list=new ArrayList<>(Arrays.asList(s));
        adapter=new CustomAdapter(list);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration divider=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    listener = true;
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleitem= manager.getChildCount();
                totalitem=manager.getItemCount();
                hiddenitem=manager.findFirstVisibleItemPosition();
                if(listener && (visibleitem+hiddenitem==totalitem)){
                    listener=false;
                    fetchData();
                }
            }
        });


    }

    private void fetchData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<5;i++){
                    list.add((Math.floor(Math.random()*100)+""));
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, 2000);
    }
}
