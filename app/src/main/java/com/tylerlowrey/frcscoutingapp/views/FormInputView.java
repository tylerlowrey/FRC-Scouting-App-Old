package com.tylerlowrey.frcscoutingapp.views;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;

public abstract class FormInputView extends LinearLayout
{
    private Context context;

    public FormInputView(Context context)
    {
        super(context);
        this.context = context;
    }

    protected float convertDisplayPixelToPixel(float pixelVal)
    {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                pixelVal,
                context.getResources().getDisplayMetrics()
        );
    }

    public abstract String getTitle();

    public abstract String getInputType();

    public abstract String getInputValue();

    public abstract String getFieldName();
}
