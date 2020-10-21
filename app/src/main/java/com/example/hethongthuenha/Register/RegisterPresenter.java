package com.example.hethongthuenha.Register;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private FirebaseAuth mAuth;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        this.mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void RegisterAccount(String email, String password) {
        view.registerPending();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                            view.registerSuccess();
                        else
                            view.registerFail("Đăng ký thất bại !");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                view.registerFail("Đăng ký thất bại !");
            }
        });
    }
}
