package com.example.hethongthuenha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.hethongthuenha.API.PersonAPI;
import com.example.hethongthuenha.Login.LoginActivity;
import com.example.hethongthuenha.MainActivity.MainActivity;
import com.example.hethongthuenha.Model.Person;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();

        authStateListener = firebaseAuth -> {
            currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                db.collection("User").whereEqualTo("uid", currentUser.getUid())
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                Person person = documentSnapshot.toObject(Person.class);

                                PersonAPI personAPI = PersonAPI.getInstance();
                                personAPI.setUid(person.getUid());
                                personAPI.setName(person.getFullName());
                                personAPI.setEmail(person.getEmail());
                                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                                finish();
                            }
                        }

                    }
                }).addOnFailureListener(e -> Log.d("SplashScreen-Error", "onCreate: "+e.getMessage()));
            } else{
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }

        }

        ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(authStateListener);
    }
}