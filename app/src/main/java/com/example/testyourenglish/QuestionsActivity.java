package com.example.testyourenglish;

import static com.example.testyourenglish.SetsActivity.category_id;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView question,qCount,timer;
    private Button  option1,option2,option3,option4;
    private List<QuestionC> questionList; //lista de intrebari pe care am creat-o in clasa QuestionC
    private int quesNum;
    private CountDownTimer countDown;
    private int score;
    private FirebaseFirestore firestore;
    private int setNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        //setam valorile pt variabile
        question = findViewById(R.id.question);
        qCount = findViewById(R.id.quest_num);
        timer = findViewById(R.id.countdown);
        //setam valorile pt butoane
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        //setam clicklistener pe butoane
        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        setNo = getIntent().getIntExtra("SETNO", 1);
        firestore = FirebaseFirestore.getInstance();

        getQestyionsList(); //cream metoda pt intrebari
        score = 0;
    }
        private void getQestyionsList()
        {
            questionList = new ArrayList<>();


            firestore.collection("TestyourEnglish").document("CAT" + String.valueOf(category_id))
                    .collection("SET" + String.valueOf(setNo))
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                QuerySnapshot questions = task.getResult();

                                for(QueryDocumentSnapshot doc : questions)
                                {
                                    questionList.add(new QuestionC(doc.getString("QUESTION"),
                                            doc.getString("A"),
                                            doc.getString("B"),
                                            doc.getString("C"),
                                            doc.getString("D"),
                                            Integer.valueOf(doc.getString("ANSWER"))

                                            ));
                                }

                                setQuestions();

                            } else {
                                Toast.makeText(QuestionsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            /* questionList.add(new QuestionC("Question 1", "A","B","C","D",2));
            questionList.add(new QuestionC("Question 2", "B","A","D","C",2));
            questionList.add(new QuestionC("Question 3", "D","C","B","A",2));
            questionList.add(new QuestionC("Question 4", "A","C","D","B",2));
            questionList.add(new QuestionC("Question 5", "D","B","C","A",2));*/


        }

        private void setQuestions() //cream o functie pt intrebari
        {
            timer.setText(String.valueOf(10)); //start cronometru

            question.setText(questionList.get(0).getQuestion()); //am asignat valoarea question la textv
            option1.setText(questionList.get(0).optionA);        //si la optiuni
            option2.setText(questionList.get(0).optionB);
            option3.setText(questionList.get(0).optionC);
            option4.setText(questionList.get(0).optionD);

            qCount.setText(String.valueOf(1) + "/" + String.valueOf(questionList.size()));

            startTimer(); //functie pentru cronometru sa porneasca
            quesNum = 0;
        }

        private void startTimer()
        {
             countDown = new CountDownTimer(12000, 1000) {
                @Override
                public void onTick(long l) {  //l = milisecunde pana termina
                    if(l < 10000)
                    timer.setText(String.valueOf(l / 1000));
                }

                @Override
                public void onFinish() {

                    changeQuestion();

                }
            };

            countDown.start();
        }

    @Override
    public void onClick(View v) {

        int selectedOption = 0; //raspunsul pe care il clickuie utilizatorul este in aceasta variabila
        switch (v.getId())
        {
            case R.id.option1:
                selectedOption = 1;
                break;

            case R.id.option2:
                selectedOption = 2;
                break;

            case R.id.option3:
                selectedOption = 3;
                break;

            case R.id.option4:
                selectedOption = 4;
                break;

            default:
        }
        countDown.cancel();
        checkAnswer(selectedOption, v);
    }

    private void checkAnswer(int selectedOption, View view)
    {
        if (selectedOption == questionList.get(quesNum).getCorrectAns())
        {
            //raspuns corect
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score++;
        }
        else
        {
            //raspuns gresit

            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));

            switch(questionList.get(quesNum).getCorrectAns())
            {
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;


            }
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        },2000);


    }

    private void changeQuestion()
    {

        if(quesNum < questionList.size() -1)
        {
            //schimbam intrebarea cu o animatie

            quesNum++;

            playAnim(question,0,0);
            playAnim(option1,0,1);
            playAnim(option2,0,2);
            playAnim(option3,0,3);
            playAnim(option4,0,4);

            qCount.setText(String.valueOf(quesNum+1) + "/" + String.valueOf(questionList.size()));

            timer.setText(String.valueOf(10));
            startTimer();
        }
        else
        {
            //Go to Score Activity
            Intent intent = new Intent(QuestionsActivity.this,ScoreActivity.class);
            intent.putExtra("SCORE", String.valueOf(score) + "/" + String.valueOf(questionList.size()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //this flag will clear all the previous activities
            startActivity(intent);
            //QuestionsActivity.this.finish();
        }
    }

    private void playAnim(View view, final int value, int viewNum)
    {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (value ==0)
                {
                    switch (viewNum)
                    {
                        case 0:
                            ((TextView)view).setText(questionList.get(quesNum).getQuestion());
                            break;
                        case 1:
                            ((Button)view).setText(questionList.get(quesNum).getOptionA());
                            break;
                        case 2:
                            ((Button)view).setText(questionList.get(quesNum).getOptionB());
                            break;
                        case 3:
                            ((Button)view).setText(questionList.get(quesNum).getOptionC());
                            break;
                        case 4:
                            ((Button)view).setText(questionList.get(quesNum).getOptionD());
                            break;
                    }


                    if(viewNum !=0)
                        ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E99C03")));

                    playAnim(view,1,viewNum);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
        //anulam Timer-ul cand dam back de la test
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        countDown.cancel();
    }
}