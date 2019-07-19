package com.example.sensorbanjir;

import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;



public class MainActivity extends AppCompatActivity {
    //    TextView hasil;
//    Button on;
//    Button off;
    public static TextView jarak, status, ip;
    FirebaseDatabase database;
    DatabaseReference myRef, myReff, myRefff;
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("tag", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Log.d("tokennya ni", token);
                    }
                });
        jarak = findViewById(R.id.jarak);
        status = findViewById(R.id.status);

//        hasil = findViewById(R.id.hasil);
//        on = findViewById(R.id.on);
//        off = findViewById(R.id.off);

        database = FirebaseDatabase.getInstance();

        myReff = database.getReference("jarak");

        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jarak.setText(dataSnapshot.getValue().toString());
                Log.d("jarak", jarak.getText().toString());
                if (Integer.parseInt(jarak.getText().toString()) <= 10) {
                    status.setText("Awas Siaga Banjir");
                } else {
                    status.setText("Aman Geng");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

