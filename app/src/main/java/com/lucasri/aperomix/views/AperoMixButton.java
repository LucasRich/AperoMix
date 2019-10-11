package com.lucasri.aperomix.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.lucasri.aperomix.R;

public class AperoMixButton extends AppCompatButton {
    public AperoMixButton(Context context) {
        super(context);
    }

    public AperoMixButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.applyStyle(context, attrs);
    }

    public AperoMixButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.applyStyle(context, attrs);
    }

    private void applyStyle(Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AperoMixFont);
        int cf = a.getInteger(R.styleable.AperoMixFont_fontName, 0);
        int fontName;
        switch (cf) {
            case 1:
                fontName = R.string.Boogaloo_Regular;
                break;
            default:
                fontName = R.string.Boogaloo_Regular;
                break;
        }

        String customFont = getResources().getString(fontName);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + customFont + ".ttf");
        setTypeface(tf);
        a.recycle();
    }
}
