package com.example.hethongthuenha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.example.hethongthuenha.Model.Notification;
import com.example.hethongthuenha.Model.Person;
import com.example.hethongthuenha.Notification.Client;
import com.example.hethongthuenha.Notification.Data;
import com.example.hethongthuenha.Notification.MyResponse;
import com.example.hethongthuenha.Notification.Sender;
import com.example.hethongthuenha.Notification.Token;
import com.example.hethongthuenha.Retrofit.APIService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityChat extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText edText;
    private Button btnSend;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String fromEmail;
    private String toEmail;
    private List<Chat> chats;
    private CollectionReference refChat;
    private ChatRecyclerView adapter;
    private TextView tvNamePerson;
    private List<Chat> path;
    private ImageView imgAvatar, imgBack;
    private HistoryChat historyChat;
    private CollectionReference history_chat_path;
    private CollectionReference refNotification;

    private APIService apiService;

    public interface FireStoreCallBack {
        void onCallBack(String path);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        fromEmail = PersonAPI.getInstance().getEmail();
        toEmail = getIntent().getStringExtra("toEmail");
        updateToken(FirebaseInstanceId.getInstance().getToken());
        Init();


        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
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
        GetInformRequirement();
        GetInFormRoomDetail();
        LoadInformPerson();
        btnSend.setOnClickListener(v -> {
            SendChat(chats, edText.getText().toString(), "");
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


    private void updateToken(String token) {
        CollectionReference reference = FirebaseFirestore.getInstance().collection("Tokens");
        Token token1 = new Token(token);
        reference.document(PersonAPI.getInstance().getUid()).set(token1);
    }


    private void GetPath() {
        FindPath(path -> {
            refChat = db.collection(path);

            refChat.orderBy("id_chat").limitToLast(10).addSnapshotListener((value, error) -> {
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

    //I dont wanna see >.<
    private void GetInformRequirement() {

        String description = getIntent().getStringExtra("description");
        if (description != null) {
            //add last chat
            FindPath(path -> {
                refChat = db.collection(path);
                refChat.orderBy("id_chat").limitToLast(10).get()
                        .addOnCompleteListener(task -> {
                            List<Chat> chats1 = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot query : task.getResult())
                                    chats1.add(query.toObject(Chat.class));
                                if (task.isComplete()) {
                                    if (description != null)
                                        SendChat(chats1, description, "");
                                }
                            }
                        });
            });
            //add notification
            String uid = getIntent().getStringExtra("toId");
            Timestamp notificationAdded = new Timestamp(new Date());
            refNotification = db.collection("Notification");
            Notification notification = new Notification(PersonAPI.getInstance().getUid(), uid, description, 1, notificationAdded);

            refNotification.add(notification);

        }


        //add notification
    }

    private void GetInFormRoomDetail() {
        //add last chat
        FindPath(path -> {
            String description = getIntent().getStringExtra("description_room");
            String url = getIntent().getStringExtra("url");
            if (description != null) {
                refChat = db.collection(path);

                refChat.orderBy("id_chat").limitToLast(10).get()
                        .addOnCompleteListener(task -> {
                            List<Chat> chats1 = new ArrayList<>();

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot query : task.getResult())
                                    chats1.add(query.toObject(Chat.class));
                                if (task.isComplete()) {
                                    if (description != null)
                                        SendChat(chats1, description, url);
                                }
                            }
                        });

                String uid = getIntent().getStringExtra("toId");
                Timestamp notificationAdded = new Timestamp(new Date());
                refNotification = db.collection("Notification");
                Notification notification = new Notification(PersonAPI.getInstance().getUid(), uid, description, 1, notificationAdded);

                refNotification.add(notification);
            }
        });
    }

    private void SendChat(List<Chat> chats, String text, String url) {
        if (!text.equals("")) {
            history_chat_path = db.collection("History-chat");
            Chat chat;
            historyChat.setPathChat(refChat.getPath());
            historyChat.setChatAdded(new Timestamp(new Date()));
            historyChat.setLastChat(text);
            historyChat.setFromATo(tvNamePerson.getText().toString() + "-" + PersonAPI.getInstance().getName());
            if (chats.isEmpty()) {
                chat = new Chat(1, text, fromEmail, toEmail, url);
                history_chat_path.add(historyChat);
            } else {
                chat = new Chat(chats.get(chats.size() - 1).getId_chat() + 1, text, fromEmail, toEmail, url);
                historyChat.setChatAdded(new Timestamp(new Date()));
                historyChat.setLastChat(text);
                history_chat_path.whereEqualTo("pathChat", historyChat.getPathChat())
                        .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            history_chat_path.document(documentSnapshot.getId())
                                    .set(historyChat);
                        }
                    }
                });
            }

            db.collection("User").whereEqualTo("uid", PersonAPI.getInstance().getUid())
                    .get().addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty())
                            for (QueryDocumentSnapshot value : queryDocumentSnapshots) {
                                Person person = value.toObject(Person.class);
                                SendNotification(tvNamePerson.getText().toString(), PersonAPI.getInstance().getName(),
                                        text);
                            }
                    });
            refChat.add(chat);
            edText.setText("");
        }
    }

    private void SendNotification(String toName, String name, String text) {
        db.collection("User").whereEqualTo("fullName", toName)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty())
                for (QueryDocumentSnapshot value : queryDocumentSnapshots) {
                    Person person = value.toObject(Person.class);
                    db.collection("Tokens").document(person.getUid())
                            .get().addOnSuccessListener(documentSnapshot -> {
                                Token token = documentSnapshot.toObject(Token.class);
                                Data data = new Data(PersonAPI.getInstance().getUid(), R.mipmap.ic_launcher, PersonAPI.getInstance().getName()
                                        + ":" + text, "New Message", person.getUid());

                                Sender sender = new Sender(data, token.getToken());

                        Toast.makeText(this, token.getToken(), Toast.LENGTH_SHORT).show();

                                apiService.sendNotification(sender)
                                        .enqueue(new Callback<MyResponse>() {
                                            @Override
                                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                                if (response.code() == 200) {
                                                    if (response.body().success != 1) {
                                                        Toast.makeText(ActivityChat.this, "Failed", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<MyResponse> call, Throwable t) {

                                            }
                                        });
                            });
                }
        });
    }
}
