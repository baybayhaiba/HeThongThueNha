package com.example.hethongthuenha.CreateRoom.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hethongthuenha.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_utilities#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_utilities extends Fragment {


    public fragment_utilities() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_utilities, container, false);
    }
}