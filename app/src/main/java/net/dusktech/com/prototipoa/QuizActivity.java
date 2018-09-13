package net.dusktech.com.prototipoa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizActivity extends AppCompatActivity {

    int score = 0;

    int questionNumber = 0;

    private String mAnswer;

    private TextView mTimer;


    private CountDownTimer countDownTimer;
    private long timeMilliseconds = 10000;


    private Questions mQuestionLibrary = new Questions();
    TextView mScoreView, mQuestionView;
    Button mButtonChoice1,mButtonChoice2, mButtonChoice3, mButtonChoice4;

    private int testValue, testScore, tempScore, lessonNumberQuizUnlock;

    DatabaseReference mDatabase;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    }

    protected void onStart(){
        super.onStart();

        startTimer();

        mTimer = (TextView)findViewById(R.id.timerText);

        mScoreView = (TextView)findViewById(R.id.score);
        mQuestionView = (TextView)findViewById(R.id.question);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonChoice3 = (Button)findViewById(R.id.choice3);
        mButtonChoice4 = (Button)findViewById(R.id.choice4);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Intent intent = getIntent();
        testValue = intent.getIntExtra("passedQuestionImage",0);

        if(testValue >= 6){
            end();
        }

        getScore();
        updateQuestion(testValue);

        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if (mButtonChoice1.getText() == mAnswer) {
                    score++;
                    mDatabase.child(user.getUid()).child("score").setValue(score);
                    getScore();

                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(check(testValue) == 0){
                    testValue = increment(testValue);
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
                    getScore();

                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(check(testValue) == 0){
                    testValue = increment(testValue);
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
                    getScore();

                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(check(testValue) == 0){
                    testValue = increment(testValue);
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
                    getScore();

                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(check(testValue) == 0){
                    testValue = increment(testValue);
                    launchActivity();
                }
            }
        });

    }

    private void updateQuestion(int value){
        mQuestionView.setText(mQuestionLibrary.getQuestion(value));
        mButtonChoice1.setText(mQuestionLibrary.getChoice1(value));
        mButtonChoice2.setText(mQuestionLibrary.getChoice2(value));
        mButtonChoice3.setText(mQuestionLibrary.getChoice3(value));
        mButtonChoice4.setText(mQuestionLibrary.getChoice4(value));

        mAnswer = mQuestionLibrary.getCorrectAnswer(value);

    }

    private void getScore() {
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


    private void launchActivity() {

        Intent intent = new Intent(QuizActivity.this, ImageActivity.class);

        intent.putExtra("passedQuestionQuiz", testValue);

        startActivity(intent);
    }

    private int increment(int value){
        value++;
        return value;
    }

    private int check(int value) {
        int resp = 0;
        if (value == (mQuestionLibrary.mQuestions.length)) {
            resp = 1;
            end();
        }
        return resp;
    }

    private void end(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
        alertDialogBuilder
                .setMessage("You've reached questions: " + testValue + "Sorry!")
                .setCancelable(false)
                .setPositiveButton("New Game",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                .setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeMilliseconds = millisUntilFinished;
                updateTimer();

            }

            @Override
            public void onFinish() {

            }
        }.start();


    }

    public void updateTimer(){
        int seconds = (int) timeMilliseconds % 60000/ 1000;

        String timeText;

        timeText = "";
        //timeText += ":";

        if(seconds <= 1){
            //this.finishAffinity();
            end();
        }

        timeText += seconds;

        mTimer.setText(timeText);
    }

}
