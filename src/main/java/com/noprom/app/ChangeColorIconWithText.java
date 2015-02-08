package com.noprom.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by noprom on 2015/2/5.
 */
public class ChangeColorIconWithText extends View {

    private int mColor = 0xFF45C01A;    // 颜色
    private Bitmap mIconBitmap;         // 图标
    private String mText = "微信";        //  文字
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());


    // 绘图相关的成员变量
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;

    private float mAlpha; // 透明度
    private Rect mIconRect;
    private Rect mTextBound;

    private Paint mTextPaint;


    public ChangeColorIconWithText(Context context) {
        this(context, null);
    }

    public ChangeColorIconWithText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 获取自定义属性的值
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ChangeColorIconWithText(Context context, AttributeSet attrs,
                                   int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 得到自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIconWithText);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.ChangeColorIconWithText_icon:
                    // 图标
                    BitmapDrawable drawable = (BitmapDrawable) array.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.ChangeColorIconWithText_color:
                    // 颜色,默认为 0xFF45C01A
                    mColor = array.getColor(attr, 0xFF45C01A);
                    break;
                case R.styleable.ChangeColorIconWithText_text:
                    // 文字内容
                    mText = array.getString(attr);
                    break;
                case R.styleable.ChangeColorIconWithText_text_size:
                    // 文字大小,默认为 12sp
                    mTextSize = (int) array.getDimension(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();

        // 初始化成员变量
        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xff555555);

        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // icon的宽度
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height());
        // 绘制的左边缘距离
        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        // 绘制的上边缘距离
        int top = (getMeasuredHeight() - mTextBound.height()) / 2 - iconWidth / 2;
        // 图标对应的矩形
        mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);

        int alpha = (int) Math.ceil(255 * mAlpha);
        // 在内存里面准备mBitmap,setAlpha,绘制纯色,xfermode,绘制图标
        setupTargetBitmap(alpha);

        // 绘制文本
        // 1.绘制原文本 2.绘制变色的文本

        drawSourceText(canvas, alpha);
        drawTargetText(canvas, alpha);

        // 绘制图标
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    /**
     * 绘制变色的文本
     *
     * @param canvas
     * @param alpha
     */
    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);

        // 距离左侧绘制坐标
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        // 距离上部绘制坐标
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 绘制原文本
     *
     * @param canvas
     * @param alpha
     */
    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255 - alpha);

        // 距离左侧绘制坐标
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        // 距离上部绘制坐标
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 在内存中绘制可变色的icon
     *
     * @param alpha
     */
    private void setupTargetBitmap(int alpha) {

        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        // 准备mPaint
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);

        // 绘制纯色
        mCanvas.drawRect(mIconRect, mPaint);

        // 设置xfermode
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        mPaint.setAlpha(255);
        // 绘制图标
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);

    }

    /**
     * 设置图标透明度
     *
     * @param alpha 透明度
     */
    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    /**
     * 重绘
     */
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            // UI线程
            invalidate();
        } else {
            // 非UI线程
            postInvalidate();
        }

    }
}
