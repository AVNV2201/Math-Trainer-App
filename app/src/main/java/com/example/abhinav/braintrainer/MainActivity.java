package com.example.abhinav.braintrainer;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView score ;
    TextView timer ;
    TextView result ;
    TextView problemTextView ;
    Button startButton ;
    boolean isGameStarted = false ;
    int correctAnswer ;
    int totalScore ;
    int totalProblems ;
    int noOfCorrectAnswers ;
    int points = 1 ;

    public void checkAnswer( View v ){
        if( isGameStarted == true ){
            Button b = (Button)v ;
            int ans = Integer.parseInt( b.getText().toString() ) ;
            startButton.setVisibility(View.VISIBLE);
            if( ans == correctAnswer ){
                startButton.setText("CORRECT!!!");
                startButton.setBackgroundColor(Color.GREEN);
                noOfCorrectAnswers++ ;
                totalScore += points ;
                points++ ;
                score.setText(String.valueOf(totalScore));
            }
            else{
                startButton.setText("INCORRECT!!!");
                startButton.setBackgroundColor(Color.RED);
                points = 1 ;
            }
            generateProblem();
        }
    }

    private  void startTimer(){

        CountDownTimer counter = new CountDownTimer( 180000, 1000 ) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                String seconds = Long.toString( (millisUntilFinished/1000) % 60 );
                if( seconds.length() == 1 ){
                    seconds = "0" + seconds ;
                }
                timer.setText( Long.toString(millisUntilFinished/60000) + ":" + seconds );
            }

            @Override
            public void onFinish() {
                timer.setText("0:00");
                isGameStarted = false ;
                gameOver();
            }
        }.start() ;
    }

    private void gameOver() {
        isGameStarted = false ;
        startButton.setText("PLAY AGAIN!!");
        score.setText("0");
        result.setText( "Score\t: " + String.valueOf( totalScore ) + "\n"
                                + "Total\t: " + String.valueOf( totalProblems ) + "\n"
                                + "Correct\t: " + String.valueOf( noOfCorrectAnswers ) + "\n"
                                + "Incorrect\t: " + String.valueOf( totalProblems - noOfCorrectAnswers ) + "\n"
                                + "Accuracy\t: " + String.valueOf( noOfCorrectAnswers * 100 / totalProblems ) + "%" );
        result.setVisibility(View.VISIBLE);
    }

    public void startGame( View v ){

        if( isGameStarted == false ) {
            startTimer();
            result.setVisibility(View.INVISIBLE);
            totalScore = 0;
            noOfCorrectAnswers = 0;
            totalProblems = 0;
            points = 1 ;
            startButton.setVisibility(View.INVISIBLE);
            isGameStarted = true;
            generateProblem();
        }
    }

    private void generateProblem(){

        Button b1 = findViewById(R.id.option1) ;
        Button b2 = findViewById(R.id.option2) ;
        Button b3 = findViewById(R.id.option3) ;
        Button b4 = findViewById(R.id.option4) ;
        Random rand = new Random() ;

        int operand1 = rand.nextInt( 50 ) ;
        int operand2 = rand.nextInt( 50 ) ;

        int i = rand.nextInt(100) ;

        if( i%3 == 0 ){
            correctAnswer = operand1 + operand2 ;
            problemTextView.setText( Integer.toString(operand1) + " + " + Integer.toString(operand2));
        }
        else if( i%3 == 1 ){
            correctAnswer = operand1 - operand2 ;
            problemTextView.setText( Integer.toString(operand1) + " - " + Integer.toString(operand2));
        }
        else{
            correctAnswer = operand1 * operand2 ;
            problemTextView.setText( Integer.toString(operand1) + " X " + Integer.toString(operand2));
        }

        int ans1 = correctAnswer - 10 + rand.nextInt(20) ;
        int ans2 = ans1 ;
        while( ans2 == ans1 )
            ans2 = correctAnswer - 10 + rand.nextInt(20) ;
        int ans3 = ans1 ;
        while( ans3 == ans1 || ans3 == ans2 )
            ans3 = correctAnswer - 10 + rand.nextInt(20) ;
        int ans4 = ans1 ;
        while( ans4 == ans1 || ans4 == ans2 || ans4 == ans3 )
            ans4 = correctAnswer - 10 + rand.nextInt(20) ;

        if( ans1 != correctAnswer && ans2 != correctAnswer && ans3 != correctAnswer && ans4 != correctAnswer  ){
            i = rand.nextInt(200) ;
            i %= 4 ;
            if( i == 0 ) ans1 = correctAnswer ;
            else if( i == 1 ) ans2 = correctAnswer ;
            else if( i == 2 ) ans3 = correctAnswer ;
            else ans4 = correctAnswer ;
        }

        b1.setText( String.valueOf(ans1) );
        b2.setText( String.valueOf(ans2) );
        b3.setText( String.valueOf(ans3) );
        b4.setText( String.valueOf(ans4) );

        totalProblems++ ;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = findViewById(R.id.scoreTextView) ;
        timer = findViewById(R.id.timerTextView) ;
        startButton = findViewById(R.id.startButton) ;
        problemTextView = findViewById(R.id.problemTextView) ;
        result = findViewById(R.id.result) ;
    }
}
