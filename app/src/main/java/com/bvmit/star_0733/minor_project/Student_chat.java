package com.bvmit.star_0733.minor_project;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shrikanthravi.chatview.data.Message;

import java.util.ArrayList;
import java.util.List;

public class Student_chat extends AppCompatActivity {
    ListView lst;
    Message message;
    List<Chat_model> list;
    FirebaseAuth auth;
    DatabaseReference ref,ref1;
    FirebaseUser user;
    EditText msg;
    ImageView send;
    ChatAdapter adapter;
    String enroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.bvmit.star_0733.minor_project.R.layout.activity_student_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat");

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("chat");
        ref1 = FirebaseDatabase.getInstance().getReference("student_login");
        user = auth.getCurrentUser();
        lst = findViewById(R.id.lst);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo networkInfo1 = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean wifi = networkInfo.isConnected();
        boolean data = networkInfo1.isConnected();
        if(!wifi && !data)
        {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot obj : dataSnapshot.getChildren())
                {
                    Stu_Reg_model model = obj.getValue(Stu_Reg_model.class);
                    if(model.getEmail().equals(user.getEmail()))
                    {
                        enroll = model.getEnroll();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        send= findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                EditText input=(EditText)findViewById(R.id.msg);
                String msg = input.getText().toString().trim();
            if(msg.length() > 0) {
                ref.push().setValue(new Chat_model(msg.trim(),
                        enroll));
                input.setText("");
                displayChatMessages();
                }
            }
        });
        list = new ArrayList<>();
        displayChatMessages();
    }
    private void displayChatMessages() {
        lst = findViewById(R.id.lst);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot chat : dataSnapshot.getChildren()) {
                    Chat_model info = chat.getValue(Chat_model.class);
                    list.add(info);
                }
                adapter = new ChatAdapter(Student_chat.this, list, enroll);
                lst.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
