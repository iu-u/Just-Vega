package com.example.android.vegaapp.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.android.vegaapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipepreperationMethodActivity extends AppCompatActivity {
    private static String TAG = RecipepreperationMethodActivity.class.getName();
    private ImageView mRecipeImage;
    private Button mGoToRecipe;
    private Button mGoToIngredients;
    private TextView mCategory;
    private TextView mRecipeName;
    private TextView mAllergies;
    private TextView mPreparation;
    private LinearLayout mLinearLayout;
    private TextView mToFName;
    private ProgressBar progressBar;
    RelativeLayout relativeLayout;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    ValueAnimator animator;

    //Timer
    private long mStartTimeInMillis;
    private boolean mTimeRunning;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private CountDownTimer mCountdownTimer;
    TextView textView;
    private long i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_preperationmethod);
        Log.i(TAG, "Activity is started");
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("name");
        String category = intent.getExtras().getString("category");
        String image = intent.getExtras().getString("image");
        ArrayList<String> allergieList = intent.getStringArrayListExtra("allergies");
        HashMap<String, List<String>> mPrepMap = (HashMap<String, List<String>>)intent.getSerializableExtra("prepMap");

        mRecipeImage = findViewById(R.id.recipeimageview);
        mGoToRecipe = findViewById(R.id.btn_back_to_recipe);
        mGoToIngredients = findViewById(R.id.btn_go_to_ingredients);
        mCategory = findViewById(R.id.category);
        mRecipeName = findViewById(R.id.recipeName);
        mAllergies = findViewById(R.id.allergies);
        mLinearLayout = findViewById(R.id.prepLayout);


        mGoToRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mGoToIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Log.d(TAG, "title: " + title);
        mRecipeName.setText(title);
        mCategory.setText(category);
        Glide.with(getApplicationContext())
                .asBitmap().load(image)
                .into(mRecipeImage);
        StringBuilder sb = new StringBuilder("");
        for(String s: allergieList){
            sb.append(s).append(", ");
        }
        mAllergies.setText(sb.toString());

        setElements(mPrepMap);


    }

    public void setElements(HashMap<String, List<String>> map){
        int currentNumber = 1;

        for(String s: map.keySet()){
            boolean timer = false;
            Log.d(TAG, "key: " + s);

            //Make Horizontal LinearLayout
            LinearLayout wrapperLayout = new LinearLayout(this);
            wrapperLayout.setOrientation(LinearLayout.HORIZONTAL);

            //Make TextView currentNumber for wrapperLayout
            TextView currentToF = new TextView(this);
            currentToF.setTextColor(getResources().getColor(R.color.colorTextGreen));
            currentToF.setText(currentNumber + ".");
            currentToF.setGravity(Gravity.RIGHT);
            currentNumber++;

            //Make vertical LinearLayout for Preparation TextViews
            LinearLayout prepLayout = new LinearLayout(this);
            prepLayout.setBackgroundColor(getResources().getColor(R.color.colorBlack));
            prepLayout.setOrientation(LinearLayout.VERTICAL);


            //Make Title TextView for vertical LinearLayout
            TextView tof = new TextView(this);
            tof.setTextColor(getResources().getColor(R.color.colorTextWhite));
            tof.setTypeface(null, Typeface.BOLD);
            tof.setText(s);

            //Make Preperation Instructions TextView for vertical LinearLayout
            TextView prep = new TextView(this);
            prep.setTextColor(getResources().getColor(R.color.colorTextLightGrey));
            StringBuilder sb = new StringBuilder("");
            for(String string: map.get(s)){
                sb.append(string).append('\n');
                if(string.contains("minuten")){
                    getAmountOfMinutes(string);
                    timer = true;
                    Log.d(TAG, "StartAmountMillis: " + mStartTimeInMillis);
                }
            }
            prep.setText(sb.toString());

            //Make LinearLayout for button for wrapperLayout
            LinearLayout buttonLayout = new LinearLayout(this);
            buttonLayout.setMinimumWidth(30);
            buttonLayout.setMinimumHeight(30);


            //Make button for LinearLayout
            final ImageView checkButton = new ImageView(this);
            checkButton.setBackground(getResources().getDrawable(R.drawable.circle_fave_icon));
//            checkButton.setHeight(20);
//            checkButton.setWidth(20);
            final Drawable img = ContextCompat.getDrawable(this, R.drawable.ic_check_green_small);

            checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   checkButton.setImageDrawable(img);
                }
            });

            final CheckBox checkBox = new CheckBox(this, null, R.style.preparationCheckBox);

            buttonLayout.addView(checkButton);

            //Add Textviews to prepLayout
            prepLayout.addView(tof);
            prepLayout.addView(prep);

            //Add params to layouts
            LinearLayout.LayoutParams paramsWrapper =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            paramsWrapper.setMargins(0,40,20,20);
            wrapperLayout.setLayoutParams(paramsWrapper);

            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 0.76f;
            params.setMargins(20,0,20,0);
            prepLayout.setLayoutParams(params);

            LinearLayout.LayoutParams paramsSmall =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            paramsSmall.weight = 0.12f;
            paramsSmall.setMargins(0,0,10,0);
            currentToF.setLayoutParams(paramsSmall);

            LinearLayout.LayoutParams paramsButton =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            paramsButton.weight = 0.12f;
            buttonLayout.setLayoutParams(paramsButton);

            View border = new View(this);
            border.setBackgroundColor(getResources().getColor(R.color.colorVagueGreen));
            border.setMinimumHeight(1);

            LinearLayout.LayoutParams paramsBorder =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            border.setLayoutParams(paramsBorder);

            wrapperLayout.setWeightSum(1f);

            //Add Elements to wrapperLayout
            wrapperLayout.addView(currentToF);
            wrapperLayout.addView(prepLayout);
            wrapperLayout.addView(buttonLayout);

            mLinearLayout.addView(wrapperLayout);
            if(timer){
                mLinearLayout.addView(getTimerLayout());
            }
            mLinearLayout.addView(border);
        }
    }

    public View getTimerLayout(){
        LinearLayout lnWrapper = new LinearLayout(this);
        lnWrapper.setMinimumHeight(100);
        LinearLayout lnLeft = new LinearLayout(this);
        LinearLayout lnRight = new LinearLayout(this);

        LinearLayout.LayoutParams paramsLeft =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsLeft.weight = 0.25f;
        lnLeft.setLayoutParams(paramsLeft);

        LinearLayout.LayoutParams paramsRight =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsRight.weight = 0.75f;
        lnRight.setLayoutParams(paramsRight);


        //RelativeLayout
        RelativeLayout timerLayout = new RelativeLayout(this);
        LinearLayout.LayoutParams paramsTimer =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        timerLayout.setLayoutParams(paramsTimer);
        timerLayout.setBackgroundColor(getResources().getColor(R.color.fui_transparent));

        //ProgressBar
        final ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(paramsTimer);
        progressBar.isIndeterminate();
        progressBar.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.custom_progress_bar));

        textView = new TextView(this);
        int minutes = (int) (mStartTimeInMillis / 1000) / 60;
        int seconds = (int) (mStartTimeInMillis / 1000) % 60;

        final String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        textView.setText(timeLeftFormatted);
        textView.setTextColor(getResources().getColor(R.color.colorTextGreen));
        textView.setGravity(Gravity.CENTER);

        final Button playButton = new Button(this);
        playButton.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_play_arrow_green));
        animator = ValueAnimator.ofInt(0, progressBar.getMax());
        animator.setDuration(mStartTimeInMillis);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mTimeRunning){
                    Toast.makeText(RecipepreperationMethodActivity.this, "Timer reset", Toast.LENGTH_SHORT).show();
                    animator.cancel();
                    mTimeRunning = false;

                    playButton.setBackground(ContextCompat.getDrawable(RecipepreperationMethodActivity.this, R.drawable.ic_play_arrow_green));
                    textView.setText(timeLeftFormatted);
                } else{
                    Toast.makeText(RecipepreperationMethodActivity.this, "Timer started", Toast.LENGTH_SHORT).show();
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation){
                            progressBar.setProgress((Integer)animation.getAnimatedValue());
                        }
                    });

                    new CountDownTimer(mStartTimeInMillis, 1000) {

                        public void onTick(long millisUntilFinished) {
                           textView.setText("seconds remaining: " +new SimpleDateFormat("mm:ss").format(new Date( millisUntilFinished)));
                        }

                        public void onFinish() {
                            textView.setText("done!");
                        }
                    }.start();

                    animator.start();

                    textView.setText("Started" + textView);
                    mTimeRunning = true;
                    playButton.setBackground(ContextCompat.getDrawable(RecipepreperationMethodActivity.this, R.drawable.ic_close_green_small));
                }
            }
        });



        LinearLayout lnTime = new LinearLayout(this);

        LinearLayout.LayoutParams paramsButton =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsButton.weight = 0.30f;
        playButton.setLayoutParams(paramsButton);

        LinearLayout.LayoutParams paramsText =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsText.weight = 0.70f;
        textView.setLayoutParams(paramsText);

        lnTime.setWeightSum(1f);
        lnTime.addView(playButton);
        lnTime.addView(textView);


        timerLayout.addView(progressBar);
        timerLayout.addView(lnTime);

        lnLeft.addView(timerLayout);

        lnWrapper.setOrientation(LinearLayout.HORIZONTAL);
        lnWrapper.setWeightSum(1f);
        lnWrapper.addView(lnLeft);
        lnWrapper.addView(lnRight);


        return lnWrapper;
    }



    private void getAmountOfMinutes(String s){
        int minutes = 0;
        Pattern p = Pattern.compile("(\\w+)\\W+minuten\\W+(\\w+)");
        Matcher m = p.matcher(s);
        if(m.find());
            System.out.printf("'%s', '%s'", m.group(1), m.group(2));
            minutes = (Integer.parseInt(m.group(1))) * 60000;
            long l = minutes;
            mStartTimeInMillis = l;
    }

}
