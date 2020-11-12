package com.example.hethongthuenha;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hethongthuenha.Model.Description_Room;
import com.example.hethongthuenha.Model.Image_Room;
import com.example.hethongthuenha.Model.Room;
import com.squareup.picasso.Picasso;

public class ActivityRoomDetail extends AppCompatActivity {
    ImageView imgRoomMain;
    ImageView[] images;
    TextView tvTitle, tvDescription, tvPrice, tvAccommodation, tvAmout, tvAddress,tvArea,tvTypeRoom;
    Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        images = new ImageView[4];
        room = (Room) getIntent().getSerializableExtra("room");
        Init();
    }

    private void Init() {
        Image_Room image_room = room.getStage3();
        Description_Room description_room=room.getStage1();


        imgRoomMain = findViewById(R.id.img_room_main_detail);

        for (int i = 0; i < 4; i++) {
            String imgId = "img_room" + (i + 1) + "_detail";
            int resId = getResources().getIdentifier(imgId, "id", getPackageName());
            images[i] = findViewById(resId);
        }

        tvTitle=findViewById(R.id.tv_title_detail_room);
        tvDescription=findViewById(R.id.tv_description_detail_room);
        tvAccommodation=findViewById(R.id.tv_accommodation_detail_room);
        tvPrice=findViewById(R.id.tv_price_detail_room);
        tvAmout=findViewById(R.id.tv_amout_detail_room);
        tvAddress=findViewById(R.id.tv_address_detail_room);
        tvArea=findViewById(R.id.tv_area_detail_room);
        tvTypeRoom=findViewById(R.id.tv_type_room_detail_room);

        LoadImage(image_room);
        LoadInformation(description_room);
    }

    private void LoadInformation(Description_Room description_room){
        tvTitle.setText(description_room.getTitle());
        tvAddress.setText("Địa chỉ:"+description_room.getAddress());
        tvAmout.setText("Sức chứa:"+description_room.getAmout()+" người");
        tvAccommodation.setText("Số lượng:"+description_room.getAccommodation());
        tvPrice.setText("Giá"+description_room.getPrice()+"/"+description_room.getType_date());
        tvArea.setText("Diện tích:"+description_room.getArea()+"m2");
        tvTypeRoom.setText("Loại:"+description_room.getType_room());
        tvDescription.setText("Mô tả:"+description_room.getDescription());
    }


    private void LoadImage(Image_Room image_room) {
        for (int i = 0; i < 4; i++) {
            Picasso.with(this).load(image_room.getImagesURL().get(i))
                    .placeholder(R.drawable.home).error(R.drawable.home)
                    .into(images[i]);


            int indexImage = i;
            images[i].setOnClickListener(v ->
                    imgRoomMain.setImageDrawable(images[indexImage].getDrawable()));
        }

        Picasso.with(this).load(image_room.getImagesURL().get(0))
                .placeholder(R.drawable.home).error(R.drawable.home)
                .into(imgRoomMain);
    }


}