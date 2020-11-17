package com.example.hethongthuenha.MainActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.example.hethongthuenha.API.PersonAPI;
import com.example.hethongthuenha.ActivitySettingPerson;
import com.example.hethongthuenha.MainActivity.Chat.fragment_list_chat;
import com.example.hethongthuenha.MainActivity.Fragment.Main_Room.fragment_main_room;
import com.example.hethongthuenha.MainActivity.Notification.fragment_notification;
import com.example.hethongthuenha.MainActivity.Requiment.fragment_requiment;
import com.example.hethongthuenha.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        Event();
    }

    private void Init(){
        toolbar=findViewById(R.id.toolbar);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        setFragment(R.id.mnRoom);
    }
    private void Event(){
        toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId()==R.id.mnPerson)
                startActivity(new Intent(MainActivity.this, ActivitySettingPerson.class));
            return false;
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(v->{
            setFragment(v.getItemId());
            return true;
        });


    }


    private void setFragment(int id){
            switch (id){
                case R.id.mnRoom:
                    fragment=new fragment_main_room();
                    break;
                case R.id.mnNeed:
                    fragment=new fragment_requiment();
                    break;
                case R.id.mnNotification:
                    fragment=new fragment_notification();
                    break;
                case R.id.mnChat:
                    fragment=new fragment_list_chat();
                    break;
            }

            String backStateName=fragment.getClass().getName();

            FragmentManager manager=getSupportFragmentManager();

            boolean fragmentPopped=manager.popBackStackImmediate(backStateName,0);

            if(!fragmentPopped){
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FrameMainRoom,fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(backStateName);
                ft.commit();
            }
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()==1)
            finish();
        else if(bottomNavigationView.getSelectedItemId()==R.id.mnRoom)
            super.onBackPressed();
        else
            bottomNavigationView.setSelectedItemId(R.id.mnRoom);

    }

}