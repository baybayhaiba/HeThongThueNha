package com.example.hethongthuenha.Register;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.hethongthuenha.R;
import com.example.hethongthuenha.model.Province;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    private static final String TAG = "simple_test";
    private RegisterPresenter presenter;
    private Spinner spProvince;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        presenter=new RegisterPresenter(this);
        spProvince=findViewById(R.id.registerOrigin);
        presenter.InitProvince();
    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerFail(String error) {

    }

    @Override
    public void registerPending() {

    }

    @Override
    public void repositoryProvince(List<Province> provinces) {
        ArrayList<String> nameProvince=new ArrayList<>();

        for (Province province:provinces) {
            nameProvince.add(province.getName());
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                nameProvince);

        spProvince.setAdapter(adapter);


    }
}