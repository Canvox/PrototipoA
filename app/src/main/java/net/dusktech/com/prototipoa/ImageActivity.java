package net.dusktech.com.prototipoa;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ImageActivity extends AppCompatActivity {

    private Questions mQuestionLibrary = new Questions();

    private TextView mScoreView, mQuestionView;
    private ImageView mImageView;
    private ImageButton mImageButton;
    private Button mButtonChoice1, mButtonChoice2, mButtonChoice3, mButtonChoice4;

    private String mAnswer;

    private int score = 0;

    private int mScoreUnlockable, lessonNumberUnlock, tempLessonNumberUnlock, contador;

    int mQuestionNumber = 0;

    private int mShortAnimationDuration;

    boolean isImageFitToScreen;

    DatabaseReference mDatabase;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mScoreView = (TextView)findViewById(R.id.questionNumberTextView);
        mQuestionView = (TextView)findViewById(R.id.questionTextView);

        mImageView = (ImageView)findViewById(R.id.imageView);

        mImageButton = (ImageButton)findViewById(R.id.imageButton) ;

        mButtonChoice1 = (Button)findViewById(R.id.button);
        mButtonChoice2 = (Button)findViewById(R.id.button2);
        mButtonChoice3 = (Button)findViewById(R.id.button3);
        mButtonChoice4 = (Button)findViewById(R.id.button4);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Intent intent = getIntent();

        mQuestionNumber = intent.getIntExtra("passedQuestionQuiz",0);

        if(mQuestionNumber >= 6){
            end();
        }


        updateScore();
        updateQuestion(); //Setea los views a [0] y aumenta QuestionNumber para QuizActivity


        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                mImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isImageFitToScreen) {
                            isImageFitToScreen=false;
                            mImageButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            mImageButton.setAdjustViewBounds(true);
                        }else{
                            isImageFitToScreen=true;
                            mImageButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                            mImageButton.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                    }
                });

                if (mButtonChoice1.getText() == mAnswer){
                    score++;
                    mDatabase.child(user.getUid()).child("score").setValue(score);
                    updateScore();
                    Toast.makeText(ImageActivity.this, "Correct", Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(ImageActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(!check()){

                    launchActivity();
                }
            }
        });

        mButtonChoice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (mButtonChoice2.getText() == mAnswer){
                    score++;
                    mDatabase.child(user.getUid()).child("score").setValue(score);
                    updateScore();
                    Toast.makeText(ImageActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(ImageActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(!check()){
                    launchActivity();
                }

            }
        });

        mButtonChoice3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (mButtonChoice3.getText() == mAnswer){
                    score++;
                    mDatabase.child(user.getUid()).child("score").setValue(score);
                    updateScore();
                    Toast.makeText(ImageActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(ImageActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(!check()){
                    launchActivity();
                }
            }
        });

        mButtonChoice4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (mButtonChoice4.getText() == mAnswer){
                    score++;
                    mDatabase.child(user.getUid()).child("score").setValue(score);
                    updateScore();
                    Toast.makeText(ImageActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(ImageActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(!check()){
                    launchActivity();
                }
            }
        });
    }

    private boolean check() {
        boolean op = false;
        if (mQuestionNumber == (mQuestionLibrary.mQuestions.length)) {
            op = true;
            end();
        }
        return op;
    }

    void updateQuestion(){

        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
        mButtonChoice3.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
        mButtonChoice4.setText(mQuestionLibrary.getChoice4(mQuestionNumber));

        mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
        mQuestionNumber++;
        /*if (mQuestionNumber >= mQuestionLibrary.mQuestions.length-1){
            gameOver();
        }*/
    }

    public void updateScore() {

        mDatabase.child(user.getUid()).child("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                score = Integer.valueOf(dataSnapshot.getValue().toString());

                mScoreView.setText("" + String.valueOf(score));


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateImage(){

        Drawable test;
        switch (mQuestionNumber-1){
            case 1:
                test = getResources().getDrawable(R.drawable.ico_android);
                mImageView.setImageDrawable(test);
                mImageButton.setImageDrawable(test);
                break;
            case 2:
                test = getResources().getDrawable(R.drawable.dusk);
                mImageView.setImageDrawable(test);
                mImageButton.setImageDrawable(test);
                break;
            case 3:
                test = getResources().getDrawable(R.drawable.ico_android);
                mImageView.setImageDrawable(test);
                mImageButton.setImageDrawable(test);
                break;
            case 4:
                test = getResources().getDrawable(R.drawable.dusk                                                                                                      );
                mImageView.setImageDrawable(test);
                mImageButton.setImageDrawable(test);
                break;
            case 5:
                test = getResources().getDrawable(R.drawable.ico_android);
                mImageView.setImageDrawable(test);
                mImageButton.setImageDrawable(test);
                break;
        }
    }

    private void launchActivity() {
        Intent intent = new Intent(ImageActivity.this, QuizActivity.class);
        intent.putExtra("passedQuestionImage", mQuestionNumber);
        startActivity(intent);
    }
    private void end(){
        contador++;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ImageActivity.this);
        alertDialogBuilder
                .setMessage("You've reached " + mQuestionNumber + " questions. Sorry!")
                .setCancelable(false)
                .setPositiveButton("New Game",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ImageActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                .setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ImageActivity.this, UnlockableActivity.class);
                                intent.putExtra("passedLessonNumberUnlockable", lessonNumberUnlock);
                                intent.putExtra("passedContadorImage", contador);
                                startActivity(intent);
                                finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private boolean contador(){
        boolean resp = false;
        if (contador == 1){
            resp = true;
        }
        return resp;
    }


}
