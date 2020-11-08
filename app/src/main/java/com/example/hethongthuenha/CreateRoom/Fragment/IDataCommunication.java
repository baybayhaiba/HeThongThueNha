package com.example.hethongthuenha.CreateRoom.Fragment;

import com.example.hethongthuenha.Model.Description_Room;
import com.example.hethongthuenha.Model.LivingExpenses_Room;

import java.util.ArrayList;

public interface IDataCommunication {
    void Description(Description_Room dataStage1);
    void LivingExpenses(LivingExpenses_Room dataStage2);
}
