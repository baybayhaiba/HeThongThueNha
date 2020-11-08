package com.example.hethongthuenha.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;


import com.example.hethongthuenha.ActivityPerson;
import com.example.hethongthuenha.CreateRoom.CreateRoomActivity;
import com.example.hethongthuenha.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CreateRoomActivity.class)));

        toolbar=findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId()==R.id.mnPerson)
                startActivity(new Intent(MainActivity.this, ActivityPerson.class));
            return false;
        });


    }
}