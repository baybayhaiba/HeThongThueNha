package com.example.hethongthuenha.Register;

public interface RegisterContract {

    interface Presenter{
        void RegisterAccount(String email,String password);
    }

    interface View{
        void registerSuccess();
        void registerFail(String error);
        void registerPending();
    }
}
