package com.example.appproject.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.appproject.R;

public class RatingActivity extends AppCompatActivity {
    private float userRate=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        final AppCompatButton btnrateNow=findViewById(R.id.btnrateNow);
        final AppCompatButton btnlater=findViewById(R.id.btnlater);
        final RatingBar ratingBar= findViewById(R.id.Ratingbar);
        final ImageView imgRating=findViewById(R.id.imgRating);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating<=1){
                    imgRating.setImageResource(R.drawable.one_star);
                }
                else if(rating<=2){
                    imgRating.setImageResource(R.drawable.two_star);
                }
                else if(rating<=3){
                    imgRating.setImageResource(R.drawable.three_star);
                } else if (rating <= 4) {
                    imgRating.setImageResource(R.drawable.four_star);
                } else if (rating<=5) {
                    imgRating.setImageResource(R.drawable.five_star);
                }
                animateImage(imgRating);
                userRate=rating;
            }
        });

    }
    private void animateImage(ImageView ratingImage){
        ScaleAnimation scaleAnimation= new ScaleAnimation(0,1f,0,1f
                , Animation.RELATIVE_TO_SELF ,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);
    }
}