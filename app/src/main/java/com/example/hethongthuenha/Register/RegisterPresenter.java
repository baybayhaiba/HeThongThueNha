package com.example.hethongthuenha.Register;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hethongthuenha.API.GetDataProvinceService;
import com.example.hethongthuenha.Retrofit.RetrofitClientInstance;
import com.example.hethongthuenha.model.District;
import com.example.hethongthuenha.model.Province;
import com.example.hethongthuenha.model.Renter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.observers.BlockingBaseObserver;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirebase;
    private String TAG = "SIMPLE_TAG";

    //private GetDataProvinceService service;
    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        this.mAuth = FirebaseAuth.getInstance();
        this.mFirebase = FirebaseFirestore.getInstance();
        //this.service= RetrofitClientInstance.getRetrofitInstance().create(GetDataProvinceService.class);

    }

    @Override
    public void RegisterAccount(String email, String password, String username, String contact) {
        view.registerPending();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        mFirebase.collection("User")
                                .add(new Renter(email, username, contact))
                                .addOnSuccessListener(documentReference -> Log.d(TAG, "onSuccess: "))
                                .addOnFailureListener(e -> Log.d(TAG, "onFailure: "));
                        view.registerSuccess();
                    } else
                        view.registerFail("Đăng ký thất bại !");
                }).addOnFailureListener(e -> view.registerFail("Đăng ký thất bại !"));
    }

//    @Override
//    public void InitProvince() {
//        service.getAllProvince().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BlockingBaseObserver<List<Province>>() {
//                    @Override
//                    public void onNext(List<Province> value) {
//                        view.repositoryProvince(value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//                });
//    }
//
//    @Override
//    public void InitDistrict(int id_tinh) {
//        service.getAllDistrict().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BlockingBaseObserver<List<District>>() {
//                    @Override
//                    public void onNext(List<District> value) {
//                        ArrayList<District> temp=new ArrayList<>();
//                        for (District district:value) {
//                            if(district.getTinh_id()==id_tinh)
//                            temp.add(district);
//                        }
//                        view.repositoryDistrict(temp);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
//    }
}
