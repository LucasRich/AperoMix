package com.lucasri.aperomix.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.lucasri.aperomix.R;

public class AperoMixTextView extends AppCompatTextView {

    public AperoMixTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.applyStyle(context, attrs);
    }

    public AperoMixTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.applyStyle(context, attrs);
    }

    // ---

    private void applyStyle(Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AperoMixTextView);
        int cf = a.getInteger(R.styleable.AperoMixTextView_fontName, 0);
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
