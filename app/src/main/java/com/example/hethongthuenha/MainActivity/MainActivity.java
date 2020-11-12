package com.example.hethongthuenha.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.hethongthuenha.API.PersonAPI;
import com.example.hethongthuenha.ActivityPerson;
import com.example.hethongthuenha.Adapter.RoomRecyclerView;
import com.example.hethongthuenha.CreateRoom.CreateRoomActivity;
import com.example.hethongthuenha.Model.Image_Room;
import com.example.hethongthuenha.Model.Person;
import com.example.hethongthuenha.Model.Room;
import com.example.hethongthuenha.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainRoomContract.View{
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    private RoomRecyclerView adapter;
    private RecyclerView recyclerView;
    private MainRoomPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter=new MainRoomPresenter(this);
        Init();
        Event();
    }

    private void Init(){
        presenter.InitRoom();
        recyclerView=findViewById(R.id.roomRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingActionButton=findViewById(R.id.floatingActionButton);
        toolbar=findViewById(R.id.toolbar);
    }
    private void Event(){
        floatingActionButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CreateRoomActivity.class)));
        toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId()==R.id.mnPerson)
                startActivity(new Intent(MainActivity.this, ActivityPerson.class));
            return false;
        });
    }


    @Override
    public void InitAdapter(List<Room> rooms) {
        adapter=new RoomRecyclerView(this,rooms);
        recyclerView.setAdapter(adapter);
    }

}