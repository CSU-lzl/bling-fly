package com.example.blingfly.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.example.blingfly.R;

public class CircleBarView extends View {
    private Paint progressPaint;//绘制圆弧的画笔
    private Paint bgPaint;
    private float progressSweepAngle;//进度条圆弧扫过的角度
    private int sweepAngle;//背景圆弧扫过的角度
    private int startAngle;//背景圆弧的起始角度
    private int progressNum;//可以更新的进度条数值
    private int maxNum;
    private boolean touchOf; //触发
    private CircleBarAnim anim;

    public CircleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    @SuppressLint("ResourceAsColor")
    private void init(Context context, AttributeSet attrs){
        int color = getResources().getColor(R.color.colorAccent);
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);//只描边，不填充
        progressPaint.setColor(color);
        progressPaint.setAntiAlias(true);//设置抗锯齿
        progressPaint.setStrokeWidth(10);

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.STROKE);//只描边，不填充
        bgPaint.setColor(Color.GRAY);
        bgPaint.setAntiAlias(true);//设置抗锯齿
        bgPaint.setStrokeWidth(10);

        progressNum = 0;
        maxNum = 100;
        startAngle = 0;
        sweepAngle = 360;
        touchOf = false;

        anim = new CircleBarAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = 10;
        float y = 10;
        RectF rectF = new RectF(x,y,x+144,y+144);//建一个大小为300 * 300的正方形区域

        canvas.drawArc(rectF,startAngle,sweepAngle,false,bgPaint);
        canvas.drawArc(rectF,startAngle,progressSweepAngle,false,progressPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setProgressNum(100,2000);
                break;
            case MotionEvent.ACTION_UP:
                setProgressNum(0,500);
                break;
        }
        postInvalidate();
        return true;
    }

    public class CircleBarAnim extends Animation {

        public CircleBarAnim(){
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            progressSweepAngle = interpolatedTime * sweepAngle * progressNum / maxNum;
            postInvalidate();
        }
    }

    //写个方法给外部调用，用来设置动画时间
    public void setProgressNum(int progressNum, int time) {
        anim.setDuration(time);
        this.progressNum = progressNum;
        this.startAnimation(anim);
    }

    public float getProgressSweepAngle() {
        return progressSweepAngle;
    }
}
