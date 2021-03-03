package com.example.blingfly.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.blingfly.R;

public class RemoteView extends View{

    private float controllerX;
    private float controllerY;
    private float centerX;
    private float centerY;
    private float bg_size;              //操控区域半径
    private float ctrller_size;         //触点半径
    private String mode;                //thr:油门    dir:方向
    private Paint paint;
    private Bitmap bitmap_bg;
    private Bitmap bitmap_ct;

        public RemoteView(Context context) {
            this(context,null);
            initView(context);
        }

        public RemoteView(Context context, AttributeSet attrs) {
            super(context,attrs);
            initView(context);
        }

        public void setMode(String s){
            mode = s;
            if (s.equals("thr"))  //油门
                controllerY = centerY+bg_size;
            else if (s.equals("dir"))     //方向
                controllerY = centerY;
        }
        @SuppressLint("UseCompatLoadingForDrawables")
        private void initView(Context context) {
            bg_size = 236;
            ctrller_size = 80;
            centerY = bg_size+ctrller_size;
            centerX = bg_size+ctrller_size;
            controllerX = centerX;
            controllerY = centerY;
            paint = new Paint();
            bitmap_bg = BitmapFactory.decodeResource(this.getResources(), R.drawable.circle_bg);
            bitmap_ct = BitmapFactory.decodeResource(this.getResources(), R.drawable.circle_ct);
        }

    public void limitController(){
        float angle = (float) Math.atan((controllerX-centerX)/(controllerY-centerY));
        float limitX = (float) ((bg_size-40)*Math.sin(angle));
        float limitY = (float) ((bg_size-40)*Math.cos(angle));

        if (controllerX > centerX){
            if (controllerY >= centerY)         //angle为正
                controllerX = Math.min(controllerX, centerX + limitX);
            else                                //angle为负
                controllerX = Math.min(controllerX, centerX - limitX);
        }
        else if (controllerX < centerX){
            if (controllerY >= centerY)         //angle为负
                controllerX = Math.max(controllerX, centerX + limitX);
            else                                //angle为正
                controllerX = Math.max(controllerX, centerX - limitX);
        }

        if (controllerY != centerY)
            controllerY = (controllerY>=(centerY+limitY))?(centerY+limitY):Math.max(controllerY,centerY-limitY);
    }

    public float getXPWM(){
            return (controllerX-centerX)/(bg_size-40);
    }

    public float getYPWM(){
        return (-(controllerY-centerY))/(bg_size-40);
    }

        @SuppressLint("ResourceAsColor")
        @Override
        protected void onDraw(Canvas canvas) {
            limitController();
            super.onDraw(canvas);
            canvas.drawBitmap(bitmap_bg,5,10,paint);
            canvas.drawBitmap(bitmap_ct,controllerX-ctrller_size,controllerY-ctrller_size,paint);
        }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x= event.getX();
        float y= event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                controllerX = x;
                controllerY = y;
                break;
            case MotionEvent.ACTION_UP:
                if (mode.equals("thr"))  //油门
                    controllerY = controllerY;
                else if (mode.equals("dir"))     //方向
                    controllerY = centerY;
                controllerX = centerX;
               break;
        }
        postInvalidate();
        return true;
    }

}
