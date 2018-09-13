package net.dusktech.com.prototipoa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NombreActivity extends AppCompatActivity {


    private Button mButton;

    private EditText mEditText;

    private TextView mTextView;

    DatabaseReference mDatabase;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre);

        mButton = (Button)findViewById(R.id.mainButton);


        mEditText = (EditText)findViewById(R.id.nameEditText);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

       // mDatabase.child(user.getUid()).child("nombre").setValue(name);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditText.getText().toString();
                mDatabase.child(user.getUid()).child("nombre").setValue(name);

                Intent intent = new Intent(NombreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
