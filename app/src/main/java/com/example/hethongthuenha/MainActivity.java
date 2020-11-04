package com.example.hethongthuenha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hethongthuenha.CreateRoom.CreateRoomActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CreateRoomActivity.class)));
    }
}