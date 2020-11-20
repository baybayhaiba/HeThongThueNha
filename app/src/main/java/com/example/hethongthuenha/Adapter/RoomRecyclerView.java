package com.example.hethongthuenha.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hethongthuenha.API.PersonAPI;
import com.example.hethongthuenha.ActivityRoomDetail;
import com.example.hethongthuenha.CreateRoom.CreateRoomActivity;
import com.example.hethongthuenha.Model.Description_Room;
import com.example.hethongthuenha.Model.Image_Room;
import com.example.hethongthuenha.Model.Notification;
import com.example.hethongthuenha.Model.Room;
import com.example.hethongthuenha.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class RoomRecyclerView extends RecyclerView.Adapter<RoomRecyclerView.MyViewHolder> {


    private List<Room> rooms;
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public RoomRecyclerView(Context context, List<Room> rooms) {
        this.rooms = rooms;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgRoom, imgMore;
        private TextView tvTitle, tvAddress, tvPrice, tvTypeRoom, tvTimeAdded;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cvRoomMain);
            imgRoom = itemView.findViewById(R.id.custom_img_main);
            tvTitle = itemView.findViewById(R.id.tv_title_main);
            imgMore = itemView.findViewById(R.id.custom_more_main);
            tvAddress = itemView.findViewById(R.id.tv_address_main);
            tvPrice = itemView.findViewById(R.id.tv_price_main);
            tvTypeRoom = itemView.findViewById(R.id.tv_type_room_main);
            tvTimeAdded = itemView.findViewById(R.id.tv_time_added_room);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_room_main, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Room room = rooms.get(position);
        Description_Room stage1 = room.getStage1();
        Image_Room stage3 = room.getStage3();

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        String title = stage1.getTitle();
        String address = stage1.getAddress();
        String price = formatter.format(stage1.getPrice());
        String type_room = stage1.getType_room();
        String type_date = stage1.getType_date();
        String urlImg = stage3.getImagesURL().get(0);

        holder.tvTitle.setText(title);
        holder.tvAddress.setText(address);
        holder.tvPrice.setText(price + "/" + type_date);
        holder.tvTypeRoom.setText(type_room);

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(room.getTimeAdded()
                .getSeconds() * 1000);

        holder.tvTimeAdded.setText(timeAgo);

        if (!room.getPerson_id().equals(PersonAPI.getInstance().getUid()))
            holder.imgMore.setVisibility(View.GONE);
        else
            holder.imgMore.setVisibility(View.VISIBLE);

        holder.imgMore.setOnClickListener(v -> {
            NotificationMore(room);
        });

        Picasso.with(context).load(urlImg).error(R.drawable.ic_baseline_error_24)
                .placeholder(R.drawable.loading).into(holder.imgRoom);

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ActivityRoomDetail.class);
            intent.putExtra("room", room);
            context.startActivity(intent);
        });
    }

    private void NotificationMore(Room room) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle("Tùy chọn");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item);
        arrayAdapter.add("Chỉnh sửa");
        arrayAdapter.add("Xóa");
        arrayAdapter.add("Quảng cáo");
        arrayAdapter.add("Xem danh sách người thuê");

        builderSingle.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
            //String strName = arrayAdapter.getItem(which);
            switch (which) {
                case 0:
                    Intent intent = new Intent(context, CreateRoomActivity.class);
                    intent.putExtra("update", room.getRoom_id());
                    intent.putExtra("room", room);
                    intent.putExtra("roomAdded", room.getTimeAdded());
                    context.startActivity(intent);
                    break;
                case 1:
                    db.collection("Room").whereEqualTo("room_id", room.getRoom_id())
                            .get().addOnCompleteListener(v -> {
                        if (v.isSuccessful()) {
                            for (QueryDocumentSnapshot value : v.getResult()) {
                                db.collection("Room").document(value.getId())
                                        .delete().addOnCompleteListener(c -> {
                                    if (c.isSuccessful())
                                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                });
                            }
                        }
                    });
                    break;
                case 2:
                    ShowDialogRequirement(room);
                    break;
                case 3:
                    Toast.makeText(context, "4", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
        builderSingle.show();
    }

    public void ShowDialogRequirement(Room room) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_ads, null);
        builder.setView(viewLayout);

        Spinner spinner = viewLayout.findViewById(R.id.sp_layout_ads);
        TextView content = viewLayout.findViewById(R.id.tv_layout_content);
        Button button = viewLayout.findViewById(R.id.btn_layout_ads);


        String contact = "0169xxxxxx";

        String text = "Nội dung cú pháp quảng cáo:\n" +
                "\nId:" + room.getPerson_id() + "\n" +
                "\nRoom:" + room.getRoom_id() + "\n" +
                "\nType:" + spinner.getSelectedItem().toString() + "\n" +
                " \n" +
                "-----------------------------------------------\n" +
                "Số tài khoản:" + contact + "\n" +
                " \n" +
                "Tên chủ tài khoản:An a\n" +
                " \n" +
                "Nếu tiền chênh lệch với mức giá sẽ hoãn loại với số dư trừ cho gói có thể đăng ký\n" +
                "gửi lại sau 5-10 ngày\n";

        content.setText(text);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = "Nội dung cú pháp quảng cáo:\n" +
                        "\nRoom:" + room.getRoom_id() + "\n" +
                        "\nType:" + spinner.getSelectedItem().toString() + "\n" +
                        " \n" +
                        "-----------------------------------------------\n" +
                        "Số tài khoản:" + contact + "\n" +
                        " \n" +
                        "Số tiền:Tương ứng với dãy đầu của type" + "\n" +
                        " \n" +
                        "Tên chủ tài khoản:An a\n" +
                        " \n" +
                        "Nếu tiền chênh lệch với mức giá sẽ hoãn loại với số dư trừ cho gói có thể đăng ký\n" +
                        "gửi lại sau 5-10 ngày\n";

                content.setText(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final AlertDialog show = builder.show();

        button.setOnClickListener(v -> {
            show.dismiss();
        });
    }


    @Override
    public int getItemCount() {
        return rooms.size();
    }
}
