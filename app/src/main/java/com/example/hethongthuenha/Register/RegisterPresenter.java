package com.example.hethongthuenha.Register;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hethongthuenha.API.GetDataProvinceService;
import com.example.hethongthuenha.Retrofit.RetrofitClientInstance;
import com.example.hethongthuenha.model.Province;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.observers.BlockingBaseObserver;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private FirebaseAuth mAuth;
    private GetDataProvinceService service;
    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        this.mAuth=FirebaseAuth.getInstance();
        this.service= RetrofitClientInstance.getRetrofitInstance().create(GetDataProvinceService.class);
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

    @Override
    public void InitProvince() {
        service.getAllProvince().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BlockingBaseObserver<List<Province>>() {
                    @Override
                    public void onNext(List<Province> value) {
                        view.repositoryProvince(value);
                        Log.d("simple", "onNext: "+value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("simple", "onError: "+e.getMessage());
                    }
                });
    }
}
