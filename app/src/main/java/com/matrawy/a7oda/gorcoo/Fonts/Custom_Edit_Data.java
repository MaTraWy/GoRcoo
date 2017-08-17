package com.matrawy.a7oda.gorcoo.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by 7oda on 8/16/2017.
 */

public class Custom_Edit_Data extends EditText {
    public Custom_Edit_Data(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Custom_Edit_Data(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Custom_Edit_Data(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Chonburi-Regular.ttf");
        setTypeface(tf ,1);

    }
}
