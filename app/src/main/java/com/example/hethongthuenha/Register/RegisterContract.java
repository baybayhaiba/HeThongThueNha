package com.example.hethongthuenha.Register;

import com.example.hethongthuenha.model.Province;

import java.util.ArrayList;
import java.util.List;

public interface RegisterContract {

    interface Presenter{
        void RegisterAccount(String email,String password);
        void InitProvince();
    }

    interface View{
        void registerSuccess();
        void registerFail(String error);
        void registerPending();
        void repositoryProvince(List<Province> provinces);
    }
}
