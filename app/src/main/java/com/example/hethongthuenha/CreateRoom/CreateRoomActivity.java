package com.example.hethongthuenha.CreateRoom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hethongthuenha.CreateRoom.Fragment.fragment_description;
import com.example.hethongthuenha.CreateRoom.Fragment.fragment_image;
import com.example.hethongthuenha.CreateRoom.Fragment.fragment_living_expenses;
import com.example.hethongthuenha.CreateRoom.Fragment.fragment_utilities;
import com.example.hethongthuenha.MainActivity;
import com.example.hethongthuenha.R;

public class CreateRoomActivity extends AppCompatActivity {

    private TextView tvStage1, tvStage2, tvStage3, tvStage4;
    private ImageView imgStage1, imgStage2, imgStage3, imgStage4;
    private Button btnFinishStage;
    private LinearLayout linearStage1, linearStage2, linearStage3, linearStage4;
    private Fragment fragment;
    private Toolbar toolbar;
    private int stage = 1;
    private ProgressDialog progressDialog;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        init();
        setFragment();
    }


    private void init() {
        tvStage1 = findViewById(R.id.tvStage1);
        tvStage2 = findViewById(R.id.tvStage2);
        tvStage3 = findViewById(R.id.tvStage3);
        tvStage4 = findViewById(R.id.tvStage4);


        imgStage1 = findViewById(R.id.imgStage1);
        imgStage2 = findViewById(R.id.imgStage2);
        imgStage3 = findViewById(R.id.imgStage3);
        imgStage4 = findViewById(R.id.imgStage4);

        linearStage1 = findViewById(R.id.linearStage1);
        linearStage2 = findViewById(R.id.linearStage2);
        linearStage3 = findViewById(R.id.linearStage3);
        linearStage4 = findViewById(R.id.linearStage4);

        progressDialog = new ProgressDialog(this);
        toolbar=findViewById(R.id.toolbar);
        linearStage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stage = 1;
                setFragment();
            }
        });

        linearStage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stage = 2;
                setFragment();
            }
        });

        linearStage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stage = 3;
                setFragment();
            }
        });

        linearStage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stage = 4;
                setFragment();
            }
        });
        btnFinishStage = findViewById(R.id.btnFinishStageRoom);

        btnFinishStage.setOnClickListener(v -> {
            if (stage == 4)
                startActivity(new Intent(CreateRoomActivity.this, MainActivity.class));
            stage++;
            setFragment();

        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.mnCancel)
                {
                    startActivity(new Intent(CreateRoomActivity.this, MainActivity.class));
                    finish();
                }
                return false;
            }
        });
    }

    private void setFragment() {
        switch (stage) {
            case 1:
                tvStage1.setTextColor(Color.GREEN);
                fragment = new fragment_description();
                break;
            case 2:
                tvStage2.setTextColor(Color.GREEN);
                fragment = new fragment_living_expenses();
                break;
            case 3:
                tvStage3.setTextColor(Color.GREEN);
                fragment = new fragment_image();
                break;
            case 4:
                tvStage4.setTextColor(Color.GREEN);
                fragment = new fragment_utilities();
                break;
        }

        String backStateName=fragment.getClass().getName();

        FragmentManager manager=getSupportFragmentManager();

        boolean fragmentPopped=manager.popBackStackImmediate(backStateName,0);

        if(!fragmentPopped){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();


            ft.add(R.id.frameContainer, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            startActivity(new Intent(CreateRoomActivity.this, MainActivity.class));
            finish();
        }


    }
}