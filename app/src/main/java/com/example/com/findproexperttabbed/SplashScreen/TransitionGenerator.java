package com.example.com.findproexperttabbed.SplashScreen;



import android.graphics.RectF;

public interface TransitionGenerator {


    public Transition generateNextTransition(RectF drawableBounds, RectF viewport);

}
