package com.example.hethongthuenha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ActivityPerson extends AppCompatActivity {
    private ListView lvSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        lvSetting=findViewById(R.id.lvSettingPerson);


        ArrayList<String> controls=new ArrayList<>();
        controls.add("Xác thực thông tin");
        controls.add("Thanh toán hoa hồng");
        controls.add("Đổi ngôn ngữ");
        controls.add("Thay đổi mật khẩu");
        controls.add("Đăng xuất");

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,controls);

        lvSetting.setAdapter(adapter);

        lvSetting.setOnItemClickListener((parent, view, position, id) -> {
            if(position==4){}
        });
    }
}