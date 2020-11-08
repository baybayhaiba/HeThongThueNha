package com.example.hethongthuenha.CreateRoom.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.hethongthuenha.Model.Description_Room;
import com.example.hethongthuenha.R;


public class fragment_description extends Fragment {


    Button btFinishStage1;
    IDataCommunication dataCommunication;
    EditText etTitle,etDescription,etAddress,etPrice,etArea,etAccommodation,etAmout;
    RadioButton radPhongTro,radNhaNguyenCan,radOGhep;
    Spinner spDate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataCommunication = (IDataCommunication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataCommunication");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        etTitle=view.findViewById(R.id.etTitle);
        etDescription=view.findViewById(R.id.etDescription);
        etAddress=view.findViewById(R.id.etAddress);
        etPrice=view.findViewById(R.id.etPrice);
        etAccommodation=view.findViewById(R.id.etAccommodation);
        etAmout=view.findViewById(R.id.etAmout);
        etArea=view.findViewById(R.id.etArea);
        btFinishStage1=view.findViewById(R.id.btnFinishStage1);
        radNhaNguyenCan=view.findViewById(R.id.radNhaNguyenCan);
        radOGhep=view.findViewById(R.id.radOGhep);
        radPhongTro=view.findViewById(R.id.radPhongTro);
        spDate=view.findViewById(R.id.spDate);


        btFinishStage1.setOnClickListener(v -> {
            String title=etTitle.getText().toString();
            String description=etDescription.getText().toString();
            String address=etAddress.getText().toString();
            double price=Double.parseDouble(etPrice.getText().toString());
            double area=Double.parseDouble(etArea.getText().toString());
            int amout=Integer.parseInt(etAmout.getText().toString());
            int accommodation=Integer.parseInt(etAccommodation.getText().toString());
            String typeDate=spDate.getSelectedItem().toString();
            String typeRoom;
            if(radPhongTro.isChecked())
                typeRoom="Phòng trọ";
            else if(radOGhep.isChecked())
                typeRoom="Ở ghép";
            else
                typeRoom="Nhà nguyên căn";

            Description_Room dataStage1=new Description_Room
                    (title,description,address,typeDate,price,area,accommodation,amout,typeRoom);
            dataCommunication.Description(dataStage1);

            fragment_living_expenses fragment=new fragment_living_expenses();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });



        return view;
    }

}