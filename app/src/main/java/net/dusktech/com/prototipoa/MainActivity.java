package net.dusktech.com.prototipoa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    Button mPlayButton, mScoreButton;
    TextView mScore;
    int score;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

    }

    protected void onStart(){
        super.onStart();
        mPlayButton = (Button) findViewById(R.id.playButton);
        mScoreButton = (Button) findViewById(R.id.scoreButton);

        mScore = (TextView) findViewById(R.id.scoreView);

        getScore();
        update();

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();

                mDatabase.child(user.getUid()).child("score").setValue(score);

                Intent intent = new Intent(MainActivity.this, UnlockableActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getScore(){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        mDatabase.child(user.getUid()).child("score").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    score = Integer.valueOf(dataSnapshot.getValue().toString());
                }else{
                    score = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score++;
                mDatabase.child(user.getUid()).child("score").setValue(score);
                update();
            }
        });


    }

    public void update(){

        mDatabase.child(user.getUid()).child("score").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    score = Integer.valueOf(dataSnapshot.getValue().toString());
                    mScore.setText(String.valueOf(score));

                }else{
                    mScore.setText(String.valueOf(score));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}