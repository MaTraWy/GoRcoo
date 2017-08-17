package com.matrawy.a7oda.gorcoo.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 7oda on 7/29/2017.
 */

public class Custom_Font_Data extends TextView {

    public Custom_Font_Data(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Custom_Font_Data(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Custom_Font_Data(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Chonburi-Regular.ttf");
        setTypeface(tf ,1);

    }
}
