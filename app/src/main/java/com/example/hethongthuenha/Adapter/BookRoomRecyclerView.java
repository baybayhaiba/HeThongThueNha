package com.example.hethongthuenha.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hethongthuenha.API.PersonAPI;
import com.example.hethongthuenha.Model.BookRoom;
import com.example.hethongthuenha.Model.Person;
import com.example.hethongthuenha.Model.Requirement;
import com.example.hethongthuenha.Model.Room;
import com.example.hethongthuenha.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookRoomRecyclerView extends RecyclerView.Adapter<BookRoomRecyclerView.MyViewHolder> {

    private Context context;
    private List<BookRoom> bookRooms;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public BookRoomRecyclerView(Context context, List<BookRoom> bookRooms) {
        this.context = context;
        this.bookRooms = bookRooms;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_book_room, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookRoom bookRoom = bookRooms.get(position);

        db.collection("User").whereEqualTo("uid", bookRoom.getId_person())
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot value : queryDocumentSnapshots) {
                    Person person = value.toObject(Person.class);
                    if (!person.getUrl().equals(""))
                        Picasso.with(context).load(person.getUrl())
                                .placeholder(R.drawable.ic_baseline_person_24)
                                .into(holder.imgAvatar);

                    holder.tvName.setText(person.getFullName());
                }
            }
        });

        db.collection("Room").whereEqualTo("room_id", bookRoom.getId_room())
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot value : queryDocumentSnapshots) {
                    Room room = value.toObject(Room.class);

                    if (!room.getPerson_id().equals(PersonAPI.getInstance().getUid())) {
                        holder.imgRemove.setVisibility(View.GONE);
                    } else
                        holder.imgRemove.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.tvTypePay.setText(bookRoom.getType_description());
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(bookRoom.getBookRoomAdded()
                .getSeconds() * 1000);

        holder.tvAdded.setText(timeAgo);

        holder.imgRemove.setOnClickListener(v -> {
            NotificationChooseDelete(bookRoom);
        });
    }

    private AlertDialog NotificationChooseDelete(BookRoom bookRoom) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo xóa");
        builder.setMessage("Bạn có thật sự muốn xóa ?");

        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());

        builder.setPositiveButton("Có", (dialog, which) -> {
            db.collection("BookRoom").whereEqualTo("id", bookRoom.getId())
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot value : queryDocumentSnapshots) {
                            db.collection("BookRoom").document(value.getId())
                                    .delete().addOnSuccessListener(v -> {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            });

                            if(!bookRoom.getType_description().equals("Trả sau")){

                            }
                        }
                    }
                }
            });
        });

        AlertDialog show = builder.show();

        return show;
    }

    @Override
    public int getItemCount() {
        return bookRooms.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar, imgRemove;
        private TextView tvName, tvTypePay, tvAdded;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.img_custom_book_room);
            tvName = itemView.findViewById(R.id.tv_custom_name_bookroom);
            tvTypePay = itemView.findViewById(R.id.tv_description_bookroom);
            tvAdded = itemView.findViewById(R.id.tv_custom_bookroom_added);
            imgRemove = itemView.findViewById(R.id.imgCancelBookRoom);
        }
    }
}
