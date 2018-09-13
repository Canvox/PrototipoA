package net.dusktech.com.prototipoa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UnlockableActivity extends AppCompatActivity {

    private int firebaseContador;

    ImageButton lesson1Button, lesson2Button, lesson3Button;

    Button mButton, mNombreButton;

    DatabaseReference mDatabase;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlockable);
        Firebase.setAndroidContext(this);
    }

    protected void onStart(){
        super.onStart();

        mButton = (Button) findViewById(R.id.button3);

        mNombreButton = (Button)findViewById(R.id.nombreButton);

        lesson1Button = (ImageButton) findViewById(R.id.lesson1);
        lesson2Button = (ImageButton) findViewById(R.id.lesson2);
        lesson3Button = (ImageButton) findViewById(R.id.lesson3);

        lesson1Button.setImageResource(R.drawable.unlockedlesson);
        lesson2Button.setImageResource(R.drawable.locklesson);
        lesson3Button.setImageResource(R.drawable.locklesson);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnlockableActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        lesson1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnlockableActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        mNombreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnlockableActivity.this, NombreActivity.class);
                startActivity(intent);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mDatabase.child(user.getUid()).child("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String stringScore = dataSnapshot.getValue().toString();

                firebaseContador = Integer.parseInt(stringScore);


                switch (firebaseContador){
                    case 1:
                        lesson2Button.setImageResource(R.drawable.unlockedlesson);
                        break;

                    case 60:
                        lesson2Button.setImageResource(R.drawable.unlockedlesson);
                        break;

                    case 62:
                        //Arreglar el desbloqueo de las lecciones (cuando se desbloquee la 3 se deberia desbloquear todas las atneriores)
                        lesson2Button.setImageResource(R.drawable.unlockedlesson);
                        lesson3Button.setImageResource(R.drawable.unlockedlesson);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
