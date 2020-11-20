package com.example.hethongthuenha.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hethongthuenha.Model.Account;
import com.example.hethongthuenha.Model.Person;
import com.example.hethongthuenha.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AccountRecyclerView extends RecyclerView.Adapter<AccountRecyclerView.MyViewHolder> {

    private Context context;
    private List<Person> persons;


    public AccountRecyclerView(Context context, List<Person> persons) {
        this.context = context;
        this.persons = persons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAccount;
        private TextView tvName, tvEmail;
        private Spinner spLock;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAccount = itemView.findViewById(R.id.img_custom_account);
            tvName = itemView.findViewById(R.id.tv_custom_account_name);
            tvEmail = itemView.findViewById(R.id.tv_custom_account_email);
            spLock = itemView.findViewById(R.id.sp_type_account);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_account_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Person person = persons.get(position);
        holder.tvName.setText(person.getFullName());
        holder.tvEmail.setText(person.getEmail());
        if (!person.getUrl().equals(""))
            Picasso.with(context)
                    .load(person.getUrl())
                    .placeholder(R.drawable.border_person_room)
                    .into(holder.imgAccount);
        else
            holder.imgAccount.setImageResource(R.drawable.ic_baseline_person_24);

        if(person.isLocked())
            holder.spLock.setSelection(1);
        else
            holder.spLock.setSelection(0);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

}
