package com.example.hethongthuenha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hethongthuenha.API.PersonAPI;
import com.example.hethongthuenha.Adapter.ChatRecyclerView;
import com.example.hethongthuenha.Model.Chat;
import com.example.hethongthuenha.Model.HistoryChat;
import com.example.hethongthuenha.Model.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityChat extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText edText;
    private Button btnSend;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String fromEmail;
    private String toEmail;
    private List<Chat> chats;
    private CollectionReference collectionReference;
    private ChatRecyclerView adapter;
    private TextView tvNamePerson;
    private List<Chat> path;
    private ImageView imgAvatar, imgBack;
    private FirebaseDatabase firebaseDatabase;
    private HistoryChat historyChat;
    CollectionReference history_chat_path;
    HashMap<String, Object> myObject;

    public interface FireStoreCallBack {
        void onCallBack(String path);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        fromEmail = PersonAPI.getInstance().getEmail();
        toEmail = getIntent().getStringExtra("toEmail");

        Init();

    }

    private void Init() {
        chats = new ArrayList<>();
        recyclerView = findViewById(R.id.chatRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatRecyclerView(this, chats);
        recyclerView.setAdapter(adapter);
        edText = findViewById(R.id.edChat);
        btnSend = findViewById(R.id.btnSendChat);
        tvNamePerson = findViewById(R.id.tvNameChat);
        imgBack = findViewById(R.id.imgBackChat);
        historyChat = new HistoryChat();
        imgAvatar = findViewById(R.id.imgPersonChat);
        tvNamePerson.setText(getIntent().getStringExtra("toName"));
        imgBack.setOnClickListener(v -> {
            onBackPressed();
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        GetPath();
        SendChat();
    }

    private void GetPath() {
        FindPath(path -> {
            collectionReference = db.collection(path);

            collectionReference.orderBy("id_chat").limitToLast(10).addSnapshotListener((value, error) -> {
                chats.clear();
                if (error == null) {
                    for (QueryDocumentSnapshot query : value) {
                        chats.add(query.toObject(Chat.class));
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        });
    }


    private void FindPath(final FireStoreCallBack fireStoreCallBack) {
        db.collection("Chat(" + fromEmail + "-" + toEmail + ")").
                addSnapshotListener((value, error) -> {
                    path = new ArrayList<>();
                    if (error == null) {
                        for (QueryDocumentSnapshot query : value) {
                            path.add(query.toObject(Chat.class));
                        }

                        if (path.size() != 0)
                            fireStoreCallBack.onCallBack("Chat(" + fromEmail + "-" + toEmail + ")");
                        else
                            fireStoreCallBack.onCallBack("Chat(" + toEmail + "-" + fromEmail + ")");
                    }
                });
    }

    private void SendChat() {
        btnSend.setOnClickListener(v -> {
            String text = edText.getText().toString();

            if (!text.equals("")) {
                history_chat_path = db.collection("History-chat");
                Chat chat;
                myObject = new HashMap<String, Object>();
                historyChat.setPathChat(collectionReference.getPath());
                historyChat.setChatAdded(new Timestamp(new Date()));
                historyChat.setLastChat(text);
                historyChat.setFromATo(tvNamePerson.getText().toString()+"-"+PersonAPI.getInstance().getName());
                if (chats.isEmpty()) {
                    chat = new Chat(1, text, fromEmail, toEmail);
                    history_chat_path.add(historyChat);
                } else {
                    chat = new Chat(chats.get(chats.size() - 1).getId_chat() + 1, text, fromEmail, toEmail);
                    myObject.put("lastChat", historyChat.getLastChat());
                    myObject.put("chatAdded", new Timestamp(new Date()));
                    history_chat_path.whereEqualTo("pathChat", historyChat.getPathChat())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                    history_chat_path.document(documentSnapshot.getId())
                                            .update(myObject);
                                }
                            }
                        }
                    });
                }
                collectionReference.add(chat);
                edText.setText("");
            }

        });
    }
}