package com.example.hethongthuenha.API;

import com.example.hethongthuenha.model.District;
import com.example.hethongthuenha.model.Province;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetDataProvinceService {

    @GET("/kenzouno1/DiaGioiHanhChinhVN/master/json/tinh.json")
    Observable<List<Province>> getAllProvince();
    @GET("/kenzouno1/DiaGioiHanhChinhVN/master/json/huyen.json")
    Observable<List<District>> getAllDistrict();
}
