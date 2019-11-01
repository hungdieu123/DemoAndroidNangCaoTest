package com.example.demoandroidnangcaotest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import java.util.List;

public class ListToaDoActivity extends AppCompatActivity {
    ToaDoAdapter toaDoAdapter;
    ToaDoDAO toaDoDAO;
    List<ToaDo_db> toaDo_db;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_toa_do);
        recyclerView=findViewById(R.id.rcview);

        toaDoDAO = new ToaDoDAO(this);
        toaDo_db=toaDoDAO.getAllfull();
        toaDoAdapter = new ToaDoAdapter(toaDo_db,this);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(toaDoAdapter);
    }
}
