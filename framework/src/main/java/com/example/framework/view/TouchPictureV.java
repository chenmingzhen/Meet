package com.example.framework.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.framework.R;

import androidx.annotation.Nullable;

/**
 * FileName:TouchPictureV
 * Create Date:2020/1/11 13:14
 * Profile:
 */
public class TouchPictureV extends View {

    //背景
    private Bitmap bgBitmap;
    //背景画笔
    private Paint mPaintbg;

    //空白块
    private Bitmap mNullBitmap;
    //空白块画笔
    private Paint mPaintNull;

    //移动方块
    private Bitmap mMoveBitmap;
    //移动画笔
    private Paint mPaintMove;

    //View的宽高
    private int mWidth;
    private int mHeight;

    //方块的大小
    private int CARD_SIZE = 200;
    //方块的坐标
    private int LINE_W, LINE_H = 0;

    //移动方块横坐标
    private int moveX = 200;
    //误差值
    private int errorVales = 20;

    private OnViewResultListener viewResultListener;


    public void setViewResultListener(OnViewResultListener viewResultListener) {
        this.viewResultListener = viewResultListener;
    }

    public TouchPictureV(Context context) {
        super (context);
        init ();
    }

    public TouchPictureV(Context context, @Nullable AttributeSet attrs) {
        super (context, attrs);
        init ();
    }

    public TouchPictureV(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
        init ();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw (canvas);
        drawBg (canvas);
        drawNullCard (canvas);
        drawMoveCard (canvas);
    }

    /**
     * 绘制移动的方块
     *
     * @param canvas
     */
    private void drawMoveCard(Canvas canvas) {
        //截取空白块位置坐标的Bitmap图像
        mMoveBitmap = Bitmap.createBitmap (bgBitmap, LINE_W, LINE_H, CARD_SIZE, CARD_SIZE);
        //绘制在View上，不使用LINE_W，会于空白块重叠
        canvas.drawBitmap (mMoveBitmap, moveX, LINE_H, mPaintMove);
    }

    /**
     * 绘制空白块
     *
     * @param canvas
     */
    private void drawNullCard(Canvas canvas) {
        //1.获取图片
        mNullBitmap = BitmapFactory.decodeResource (getResources (), R.drawable.img_null_card);
        CARD_SIZE = mNullBitmap.getWidth ();

        //横坐标
        LINE_W = mWidth / 3 * 2;

        //纵坐标
        LINE_H = mHeight / 2 - (CARD_SIZE / 2);

        //3.绘制
        canvas.drawBitmap (mNullBitmap, LINE_W, LINE_H, mPaintNull);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged (w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 绘制背景
     *
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        //1.获取图片
        Bitmap mBitmap = BitmapFactory.decodeResource (getResources (), R.drawable.img_bg);
        //2.创建空的Bitmap Bitmap w h =View w h
        bgBitmap = Bitmap.createBitmap (mWidth, mHeight, Bitmap.Config.ARGB_8888);
        //3.将图片绘制到空的bitmap上
        Canvas bgCanvas = new Canvas (bgBitmap);
        bgCanvas.drawBitmap (mBitmap, null, new Rect (0, 0, mWidth, mHeight), mPaintbg);
        //4.将bgBitmap绘制到View上
        canvas.drawBitmap (bgBitmap, null, new Rect (0, 0, mWidth, mHeight), mPaintbg);

    }

    private void init() {
        mPaintbg = new Paint ();
        mPaintNull = new Paint ();
        mPaintMove = new Paint ();
    }

    private boolean isMove=false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //瞬移bug
        switch (event.getAction ()) {
            case MotionEvent.ACTION_MOVE:
                //防止越界
                if (event.getX () > 0 && event.getX () < (mWidth - CARD_SIZE)) {
                   if(isMove){
                       moveX = (int) event.getX ();
                       //更新调用onDraw方法
                       invalidate ();
                   }
                }
                break;
            case MotionEvent.ACTION_UP:
                //验证结果
                if (moveX > (LINE_W - errorVales) && moveX < (LINE_W + errorVales)) {
                    if (viewResultListener != null) {
                        viewResultListener.onResult ();
                        moveX = 200;
                        invalidate ();
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                //判断点击的坐标是否是方块的内部，如果是就可以拖动
                if(event.getX ()>moveX&&event.getX ()<(moveX+CARD_SIZE)
                        &&event.getY ()>LINE_H&&event.getY ()<(LINE_H+CARD_SIZE)){
                    isMove=true;
                }else{
                    isMove=false;
                }
                break;
        }
        return true;
    }

    public interface OnViewResultListener {
        void onResult();
    }
}
