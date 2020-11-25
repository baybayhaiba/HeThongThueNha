package com.example.hethongthuenha.Adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hethongthuenha.Model.Ads;
import com.example.hethongthuenha.Model.Room;
import com.example.hethongthuenha.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class AdsRecyclerView extends RecyclerView.Adapter<AdsRecyclerView.MyViewHolder> {

    private Context context;
    private List<Ads> adsList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();

    public AdsRecyclerView(Context context, List<Ads> adsList) {
        this.context = context;
        this.adsList = adsList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRoom, imgRemove;
        TextView tvTitle, tvPrice, tvCountDown;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgRoom = itemView.findViewById(R.id.img_room_ads);
            imgRemove = itemView.findViewById(R.id.img_remove_ads);
            tvTitle = itemView.findViewById(R.id.tv_title_ads);
            tvPrice = itemView.findViewById(R.id.tv_price_ads);
            tvCountDown = itemView.findViewById(R.id.tv_timestamp_ads);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_ads_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ads ads = adsList.get(position);

        db.collection("Room").whereEqualTo("room_id", ads.getId_room())
                .get().addOnSuccessListener(v -> {
            if (!v.isEmpty()) {
                for (QueryDocumentSnapshot value : v) {
                    Room room = value.toObject(Room.class);

                    Picasso.with(context).load(room.getStage3().getImagesURL().get(0))
                            .placeholder(R.drawable.home).into(holder.imgRoom);
                    holder.tvTitle.setText(room.getStage1().getTitle());
                }
            }
        });

        holder.tvPrice.setText("" + formatter.format(ads.getPrice()));

        String timeFuture = (String) DateUtils.getRelativeTimeSpanString(ads.getCount_down()
                .getSeconds() * 1000);
        holder.tvCountDown.setText(timeFuture);
    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }

}
