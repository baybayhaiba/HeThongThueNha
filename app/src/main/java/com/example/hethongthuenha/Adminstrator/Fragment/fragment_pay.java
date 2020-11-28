package com.example.hethongthuenha.Adminstrator.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hethongthuenha.Adapter.PayRecyclerView;
import com.example.hethongthuenha.Model.BookRoom;
import com.example.hethongthuenha.Model.Commission;
import com.example.hethongthuenha.Model.Person;
import com.example.hethongthuenha.Model.Room;
import com.example.hethongthuenha.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class fragment_pay extends Fragment {

    private RecyclerView recyclerView;
    private PayRecyclerView adapter;
    private List<Commission> commissions;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private double price = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pay, container, false);
        recyclerView = view.findViewById(R.id.payRecyclerview);
        commissions = new ArrayList<>();
        adapter = new PayRecyclerView(getActivity(), commissions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        db.collection("User").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot value : task.getResult()) {
                            String idCommission = db.collection("Commissions").document().getId();
                            Commission commission = new Commission();
                            commission.setId(idCommission);
                            commission.setId_person(value.toObject(Person.class).getUid());

                            db.collection("Room")
                                    .whereEqualTo("person_id", commission.getId_person())
                                    .get().addOnCompleteListener(task1 -> {

                                if (task1.isSuccessful()) {

                                    for (QueryDocumentSnapshot value1 : task1.getResult()) {
                                        Room room = value1.toObject(Room.class);

                                        db.collection("BookRoom")
                                                .whereEqualTo("id_room", room.getRoom_id())
                                                .get().addOnCompleteListener(task2 -> {
                                            if (task2.isSuccessful()) {
                                                price = 0;
                                                for (QueryDocumentSnapshot value2 : task2.getResult()) {
                                                    BookRoom bookRoom = value2.toObject(BookRoom.class);

                                                    Calendar calBookRoom = Calendar.getInstance();
                                                    calBookRoom.setTime(bookRoom.getBookRoomAdded().toDate());

                                                    Calendar calNow = Calendar.getInstance();
                                                    calNow.setTime(new Date());

                                                    if (calBookRoom.get(Calendar.DAY_OF_MONTH) < calNow.get(Calendar.DAY_OF_MONTH)) {
                                                        db.collection("Commission").get()
                                                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                                                    if (!queryDocumentSnapshots.isEmpty()) {
                                                                        for (QueryDocumentSnapshot value3 : queryDocumentSnapshots) {
                                                                            price += room.getStage1().getPrice() * Double.parseDouble(value3.get("commission").toString());
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            }
                                        });
                                    }

                                    if (task1.isComplete()) {
                                        {
                                            commission.setPrice(price);
                                            commissions.add(commission);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

                                }
                            });
                        }


                    }
                });


        return view;
    }
}