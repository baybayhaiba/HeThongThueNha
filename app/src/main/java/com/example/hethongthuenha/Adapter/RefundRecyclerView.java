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

import com.example.hethongthuenha.Model.Person;
import com.example.hethongthuenha.Model.Refund;
import com.example.hethongthuenha.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class RefundRecyclerView extends RecyclerView.Adapter<RefundRecyclerView.MyViewHolder> {

    private Context context;
    private List<Refund> refunds;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();

    public RefundRecyclerView(Context context, List<Refund> refunds) {
        this.context = context;
        this.refunds = refunds;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar, imgDone;
        TextView tvName, tvPrice, tvBankCard, tvTimestamp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.img_custom_person_refund);
            imgDone = itemView.findViewById(R.id.img_done_refund);
            tvName = itemView.findViewById(R.id.tv_custom_name_refund);
            tvPrice = itemView.findViewById(R.id.tv_custom_price_refund);
            tvBankCard = itemView.findViewById(R.id.tv_bank_card_refund);
            tvTimestamp = itemView.findViewById(R.id.tv_custom_timestamp_refund);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_refund, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Refund refund = refunds.get(position);

        db.collection("User").whereEqualTo("uid", refund.getId_person())
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

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(refund.getRefundAdded()
                .getSeconds() * 1000);

        holder.tvTimestamp.setText(timeAgo);
        holder.tvBankCard.setText(refund.getBankCard());
        holder.tvPrice.setText("" + formatter.format(refund.getPrice()));

    }

    @Override
    public int getItemCount() {
        return refunds.size();
    }

}
