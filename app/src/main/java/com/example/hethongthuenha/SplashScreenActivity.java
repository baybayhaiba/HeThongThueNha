package com.example.hethongthuenha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hethongthuenha.API.PersonAPI;
import com.example.hethongthuenha.Login.LoginActivity;
import com.example.hethongthuenha.MainActivity.MainActivity;
import com.example.hethongthuenha.Model.CreditCard;
import com.example.hethongthuenha.Model.Person;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    private static int SPLASH_TIMER = 3000;
    Animation rightAnim, bottomAnim, rotateAnim, topAnim;
    ImageView image, imagea, imageb;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Animation();
        GetInformPerson();

    }

    public void GetInformPerson() {

        mAuth = FirebaseAuth.getInstance();

        authStateListener = firebaseAuth -> {
            currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                db.collection("User").whereEqualTo("uid", currentUser.getUid())
                        .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Person person = documentSnapshot.toObject(Person.class);

                            PersonAPI personAPI = PersonAPI.getInstance();
                            personAPI.setUid(person.getUid());
                            personAPI.setName(person.getFullName());
                            personAPI.setEmail(person.getEmail());
                            personAPI.setType_person(person.getType_person());


                            //Get Point
                            db.collection("CreditCard").whereEqualTo("id_person", person.getUid())
                                    .get().addOnSuccessListener(v -> {
                                if (v.isEmpty()){
                                    personAPI.setPoint(0);
                                }

                                else {
                                    for (QueryDocumentSnapshot value : v) {
                                        CreditCard creditCard = value.toObject(CreditCard.class);
                                        personAPI.setPoint(creditCard.getPoint());
                                    }
                                }

                                //Set Lock Account
                                if (person.isLocked())
                                    NotificationLock();
                                else {
                                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                                    finish();
                                }
                            });
                        }

                    }

                }).addOnFailureListener(e -> Log.d("SplashScreen-Error", "onCreate: " + e.getMessage()));
            } else {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }

        };
    }

    private void NotificationLock() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Tài khoản của bạn đã bị khóa muốn biết chi tiết xin liên hệ ******");
        builder.setPositiveButton("Ok", (dialog, which) -> {
            dialog.dismiss();
            mAuth.signOut();
        });
        builder.show();
    }

    public void Animation() {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        rightAnim = AnimationUtils.loadAnimation(this, R.anim.right_animation);
        rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);


        textView = findViewById(R.id.chu);
        textView.setAnimation(bottomAnim);

        image = findViewById(R.id.may);
        image.setAnimation(rightAnim);

        imagea = findViewById(R.id.sun);
        imagea.setAnimation(rotateAnim);

        imageb = findViewById(R.id.anhtp);
        imageb.setAnimation(topAnim);
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(authStateListener);
    }


}