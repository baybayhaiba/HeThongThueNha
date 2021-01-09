package com.example.hethongthuenha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hethongthuenha.API.PersonAPI;
import com.example.hethongthuenha.Adapter.ChatRecyclerView;
import com.example.hethongthuenha.Model.Chat;
import com.example.hethongthuenha.Model.HistoryChat;
import com.example.hethongthuenha.Model.Notification;
import com.example.hethongthuenha.Model.Person;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

public class ActivityChat extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText edText;
    private Button btnSend;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String fromEmail;
    private String toEmail;
    private List<Chat> chats;
    private CollectionReference refChat;
    private ChatRecyclerView adapter;
    private TextView tvNamePerson;
    private List<Chat> path;
    private ImageView imgAvatar, imgBack;
    private HistoryChat historyChat;
    private final CollectionReference refHistoryChat = db.collection("History-chat");
    private final CollectionReference refNotification = db.collection("Notification");


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
        GetPath();
        LoadInformPerson();
        GetInFormRoomDetail();
        btnSend.setOnClickListener(v -> {
            SendChat(HandleChat(edText.getText().toString(), ""));
        });
    }

    private void LoadInformPerson() {
        db.collection("User").whereEqualTo("email", toEmail)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Person person = documentSnapshot.toObject(Person.class);

                    if (!person.getUrl().equals("")) {
                        Picasso.with(this)
                                .load(person.getUrl()).placeholder(R.drawable.ic_baseline_person_24)
                                .into(imgAvatar);
                    }

                    tvNamePerson.setText(person.getFullName());

                    imgAvatar.setOnClickListener(v -> {
                        Intent intent = new Intent(ActivityChat.this, ActivityPerson.class);
                        intent.putExtra("id_person", person.getUid());
                        startActivity(intent);
                    });
                }
            }
        });
    }


    //I dont wanna see >.<
//    private void GetInformRequirement() {
//
//        String description = getIntent().getStringExtra("description");
//        if (description != null) {
//            //add last chat
//            FindPath(path -> {
//                refChat = db.collection(path);
//                refChat.orderBy("id_chat").limitToLast(10).get()
//                        .addOnCompleteListener(task -> {
//                            List<Chat> chats1 = new ArrayList<>();
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot query : task.getResult())
//                                    chats1.add(query.toObject(Chat.class));
//                                if (task.isComplete()) {
//                                    if (description != null)
//                                        SendChat(chats1, description, "");
//                                }
//                            }
//                        });
//            });
//            //add notification
//            String uid = getIntent().getStringExtra("toId");
//            Timestamp notificationAdded = new Timestamp(new Date());
//            Notification notification = new Notification(PersonAPI.getInstance().getUid(), uid, description, 1, notificationAdded);
//
//            refNotification.add(notification);
//
//        }
//
//
//        //add notification
//    }

    private void GetInFormRoomDetail() {
        //add last chat
        FindPath(path -> {
            String description = getIntent().getStringExtra("description_room");
            String url = getIntent().getStringExtra("url");
            if (description != null) {
                SendChat(HandleChat(description, url));

                String uid = getIntent().getStringExtra("toId");
                Timestamp notificationAdded = new Timestamp(new Date());
                Notification notification = new Notification(PersonAPI.getInstance().getUid(), uid, description, 1, notificationAdded);

                refNotification.add(notification);
            }
        });
    }

    private void GetPath() {
        FindPath(path -> {
            refChat = db.collection(path);

            refChat.orderBy("chatAdded").limitToLast(10).addSnapshotListener((value, error) -> {
                chats.clear();
                if (error == null) {
                    for (QueryDocumentSnapshot query : value) {
                        chats.add(query.toObject(Chat.class));
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chats.size() - 1);
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

    private Chat HandleChat(String text, String url) {

        if (TextUtils.isEmpty(text))
            return null;

        Chat chat = new Chat();
        chat.setText(text);
        chat.setUrl(url);
        chat.setFrom_email_person(fromEmail);
        chat.setTo_email_person(toEmail);
        chat.setChatAdded(new Timestamp(new Date()));

        return chat;
    }

    private void SendChat(Chat chat) {
        if (chat != null) {

            historyChat.setPathChat(refChat.getPath());
            historyChat.setFromATo(tvNamePerson.getText().toString() + "-" + PersonAPI.getInstance().getName());
            if (chats.isEmpty()) {
                historyChat.setChatAdded(new Timestamp(new Date()));
                historyChat.setLastChat(chat.getText());
                refHistoryChat.add(historyChat);
            } else {
                historyChat.setChatAdded(new Timestamp(new Date()));
                historyChat.setLastChat(chat.getText());
                refHistoryChat.whereEqualTo("pathChat", historyChat.getPathChat())
                        .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            refHistoryChat.document(documentSnapshot.getId())
                                    .set(historyChat);
                        }
                    }
                });
            }

            refChat.add(chat);
            edText.setText("");
        }
    }
}

