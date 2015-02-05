package com.noprom.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
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

    private int mAlpha; // 透明度
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


}
