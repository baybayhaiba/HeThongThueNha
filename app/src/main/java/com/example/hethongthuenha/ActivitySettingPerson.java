package com.example.hethongthuenha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hethongthuenha.API.PersonAPI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ActivitySettingPerson extends AppCompatActivity {
    private ListView lvSetting;
    private FirebaseAuth mAuth;
    private TextView tvName, tvPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_person);
        mAuth = FirebaseAuth.getInstance();
        lvSetting = findViewById(R.id.lvSettingPerson);
        tvName = findViewById(R.id.tv_name_setting_person);
        tvPoint = findViewById(R.id.tv_point_setting_person);
        tvName.setText(PersonAPI.getInstance().getName());
        tvPoint.setText(PersonAPI.getInstance().getPoint() + " VNĐ");
        ArrayList<String> controls = new ArrayList<>();
        controls.add("Xem trang cá nhân");
        controls.add("Thanh toán hoa hồng");
        controls.add("Nạp tiền");
        controls.add("Đổi ngôn ngữ");
        controls.add("Thay đổi mật khẩu");
        controls.add("Đăng xuất");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, controls);
        lvSetting.setAdapter(adapter);


        lvSetting.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0) {
                Intent intent = new Intent(this, ActivityPerson.class);
                intent.putExtra("id_person", PersonAPI.getInstance().getUid());
                startActivity(intent);
            }
            if (position == 2){
                AddPoint();
            }
            if (position == 4) {
                mAuth.signOut();
            }
        });
    }

    private void AddPoint() {

        String contact = "0169xxxxxx";
        String text = "Nội dung cú pháp nạp tiền:\n" +
                "\nID:" + PersonAPI.getInstance().getUid() + "\n" +
                "\nEmail:" + PersonAPI.getInstance().getEmail() + "\n" +
                " \n" +
                "-----------------------------------------------\n" +
                "Số tài khoản:" + contact + "\n" +
                " \n" +
                "Tên chủ tài khoản:An a\n" +
                " \n" +
                "*Bạn có thể bỏ qua ID nếu bạn chắc rằng sẽ nhập chính xác email\n" +
                "nếu sai tiền sẽ gửi lại sau 5-10 ngày\n";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo trả trước");
        builder.setMessage(text);
        builder.setPositiveButton("Tôi đã hiểu", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}