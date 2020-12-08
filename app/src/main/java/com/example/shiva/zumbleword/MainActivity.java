package com.example.shiva.zumbleword;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    String[] words_arr = {"hello","among","wise","pride","between","nest","pot","top","mango"};
    int point[]={10,10,20,10,20,10,5,10,5};
    TextView tv_result;
    int count=0;
    int letter_num=0;
    String word="";
    int index=0;
    Context c;
    int myscore=0;
    int lenth=0;
    TextView score;
    data current;
    int sum=0;
    int j=0;
    int total_score=0;
    int point_new=0;
    Button btn_cancel;

    ArrayList<data> list=new ArrayList<>();
   // ArrayList<String> list2=new ArrayList<>();
   private static final long START_TIME_IN_MILLIS=60000;
   TextView tvCountDown;
    private CountDownTimer countDownTimer;
    private boolean TimerRunning;
    private long timeLeftInMili=START_TIME_IN_MILLIS;
    String timeLeftFormated;
    int seconds;
    int minutes;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main);

        //list.add(new )
        c = this;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.jumble_layout);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.jumble_layout2);
        tv_result = (TextView) findViewById(R.id.Result);
        btn_cancel = (Button) findViewById(R.id.btn_cancl);
        tvCountDown = (TextView) findViewById(R.id.tv_count);

        score = (TextView) findViewById(R.id.score);
        // int random_index = this.getRandomIndex(words_arr.length-1);
        // Bundle extras = getIntent().getExtras();
        Intent intent2 = getIntent();
        index = intent2.getIntExtra("index", 0);
        sum = intent2.getIntExtra("sum", 0);
        myscore = intent2.getIntExtra("score", 0);
        // minutes=intent2.getIntExtra("minute",0);
        // seconds=intent2.getIntExtra("second",0);


     /*  if (extras != null) {
           index = extras.getInt("index",0);
          sum=extras.getInt("sum",0);
          myscore=extras.getInt("score",0);
           minutes=extras.getInt("minute",0);
           seconds=extras.getInt("second",0);
         //  timeLeftInMili=extras.getLong("timeleft",0);
         //  timeLeftFormated=extras.getString("timeLeftFormat");
         //  TimerRunning=extras.getBoolean("timeRunning",false);
            //The key argument here must match that used in the other activity
        }*/


        for (int i = 0; i < words_arr.length; i++) {
            current = new data(words_arr[i], point[i]);
            list.add(current);
        }
        if (index != list.size()) {
            startTimer();
            updateCountDownText();
            score.setText("score:" + myscore + "/" + list.size());
            word = list.get(index).getWord1();
            // point_new = list.get(index).getPoints();
            char[] ch = word.toCharArray();
            char[] suffele_arr = shuffleArray(ch);
            letter_num = suffele_arr.length;
            final MediaPlayer mp = MediaPlayer.create(c, R.raw.btn_click_sound);
            for (int i = 0; i < suffele_arr.length; i++) {
                final Button textView = new Button(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(1, 0, 1, 0);
                textView.setLayoutParams(lp);
                //lp.setMargins(25,25,25,25);
                textView.setGravity(Gravity.CENTER);
                textView.setAllCaps(true);
                textView.setBackgroundColor(Color.parseColor("#F1C40F"));////////
                textView.setTextColor(Color.parseColor("#DAF7A6"));
                textView.setBackgroundResource(R.drawable.buttonstyle);

                //  textView.setTextSize(getResources().getDimension(R.dimen.textsize));
                textView.setText("" + suffele_arr[i]);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count++;
                        Log.e("Click", "" + textView.getText());
                        mp.start();
                        final String old_text = (String) tv_result.getText();
                        tv_result.setText(old_text + textView.getText());
                        textView.setEnabled(false);
                        textView.setBackgroundResource(R.drawable.btn_inactive);
                        //  textView.setPaintFlags(textView.getPaintFlags() |Paint.STRIKE_THRU_TEXT_FLAG);
                        Log.e("Count", "" + count + " Letters" + letter_num);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                tv_result.setText(old_text);
                                textView.setEnabled(true);
                                textView.setBackgroundResource(R.drawable.buttonstyle);
                                mp.start();
                                count--;
                            }
                        });


                        if (count == letter_num) {

                            String final_text = (String) tv_result.getText();
                            final_text = final_text.trim();

                            Log.e("Final Text", final_text);
                            Log.e("Actual word", word);

                            if (final_text.equalsIgnoreCase(word)) {
                                Toast t = Toast.makeText(c, "Correct", Toast.LENGTH_LONG);
                                t.show();
                                myscore++;
                                sum += list.get(index).getPoints();
                                // score.setText(sum);
                                //  updatescore(sum);
                            } else {

                                Toast t = Toast.makeText(c, "Wrong", Toast.LENGTH_LONG);
                                t.show();

                            }

                            // j++;
                            //  count2++;
                            index++;


                            //continue restart;
                            // onCreate(Bundle savedInstanceState);
                            Intent intent = new Intent(c, MainActivity.class);
                            intent.putExtra("index", index);
                            intent.putExtra("sum", sum);
                            intent.putExtra("score", myscore);
                            // intent.putExtra("value",""+timeLeftFormated);
                            //  intent.putExtra("minute",minutes);
                            //  intent.putExtra("second",seconds);
                            intent.putExtra("timeleft", timeLeftInMili);
                            //  intent.putExtra("timeRunning",TimerRunning);
                            //  intent.putExtra("timeLeftFormat",timeLeftFormated);
                            // intent.putExtra("count",""+count);
                            startActivity(intent);
                            finish();
                        }
                        //
                        //
                        // v.setClickable(false);
                    }
                });

                Log.e("Letter", "" + suffele_arr[i]);
                if (i < 7) {
                    linearLayout.addView(textView);
                } else {
                    linearLayout2.addView(textView);
                }


            }


        } else {
            Intent intent1 = new Intent(getApplicationContext(), MyScore.class);
            intent1.putExtra("total", sum);
            startActivity(intent1);
            Toast t = Toast.makeText(c, "no word in library", Toast.LENGTH_LONG);

        }
    }

    private void startTimer()
    {
        countDownTimer=new CountDownTimer(timeLeftInMili,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMili=l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                TimerRunning=false;
                countDownTimer.cancel();
              startActivity(new Intent(getApplicationContext(),GameOver.class));
              //  Toast.makeText(getApplicationContext(),"timer finish",Toast.LENGTH_SHORT).show();
               finish();

            }
        }.start();
        TimerRunning=true;
    }
    private void updateCountDownText() {
         minutes=(int) (timeLeftInMili/1000)/60;
         seconds=(int) (timeLeftInMili/1000)%60;
         timeLeftFormated=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        tvCountDown.setText(timeLeftFormated);

    }
 /*  public void updatescore(int point)
   {
        score.setText("score:"+sum);
    }
*/
   /* public void updateword()
    {
        if (j !=list.size()) {
            word = list.get(index).getWord1();
            j++;
        }else {
            Toast.makeText(getApplicationContext(),"no word",Toast.LENGTH_LONG).show();
        }
    }*/
    public int getRandomIndex(int size)
    {
        Random rand = new Random();

        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(size);
        return rand_int1;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static char[] shuffleArray(char[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            char a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }

        return ar;
    }

}
