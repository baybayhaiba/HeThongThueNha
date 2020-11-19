package com.example.hethongthuenha.Adminstrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hethongthuenha.Adminstrator.Fragment.fragment_account;
import com.example.hethongthuenha.Adminstrator.Fragment.fragment_ads;
import com.example.hethongthuenha.Adminstrator.Fragment.fragment_commission;
import com.example.hethongthuenha.Adminstrator.Fragment.fragment_pay;
import com.example.hethongthuenha.Adminstrator.Fragment.fragment_refund;
import com.example.hethongthuenha.Adminstrator.Fragment.fragment_repay;
import com.example.hethongthuenha.Adminstrator.Fragment.fragment_report;
import com.example.hethongthuenha.MainActivity.MainActivity;
import com.example.hethongthuenha.R;
import com.google.android.material.navigation.NavigationView;

public class ActivityAdmintrators extends AppCompatActivity {


    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admintrators);

        Init();
        setEvent();
        SetFragment(R.id.mnCommission);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open_drawer,
                R.string.navigation_close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void Init() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        //dung cai nay de get id tu drawlayout
        //View view=navigationView.getHeaderView(0);
        //bt_person=view.findViewById(R.id.bt_person);
    }

    private void setEvent() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                SetFragment(menuItem.getItemId());
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void SetFragment(int id) {
        switch (id) {
            case R.id.mnCommission:
                fragment = new fragment_commission();
                break;
            case R.id.mnPay:
                fragment = new fragment_pay();
                break;
            case R.id.mnAccount:
                fragment = new fragment_account();
                break;
            case R.id.mnAds:
                fragment = new fragment_ads();
                break;
            case R.id.mnRepay:
                fragment = new fragment_repay();
                break;
            case R.id.mnReport:
                fragment = new fragment_report();
                break;
            case R.id.mnRefund:
                fragment = new fragment_refund();
                break;
            case R.id.mnExit:
                startActivity(new Intent(ActivityAdmintrators.this, MainActivity.class));
                finish();
                break;
        }

        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();

        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameAdminstrator, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            startActivity(new Intent(ActivityAdmintrators.this, MainActivity.class));
            finish();
        }

        super.onBackPressed();
    }
}