package com.example.hethongthuenha.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hethongthuenha.Model.Commission;
import com.example.hethongthuenha.Model.Person;
import com.example.hethongthuenha.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class PayRecyclerView extends RecyclerView.Adapter<PayRecyclerView.MyViewHolder> {


    private Context context;
    private List<Commission> commissions;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final NumberFormat formatter = NumberFormat.getCurrencyInstance();
    public PayRecyclerView(Context context, List<Commission> commissions) {
        this.context = context;
        this.commissions = commissions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_pay, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Commission commission = commissions.get(position);

        db.collection("User").whereEqualTo("uid", commission.getId_person())
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

        holder.tvPrice.setText(""+commission.getPrice());

    }

    @Override
    public int getItemCount() {
        return commissions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar, imgRemove;
        private TextView tvName, tvPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.img_avatar_pay);
            imgRemove = itemView.findViewById(R.id.img_remove_pay);
            tvName = itemView.findViewById(R.id.tv_custom_name_pay);
            tvPrice = itemView.findViewById(R.id.tv_price_pay);
        }
    }
}
