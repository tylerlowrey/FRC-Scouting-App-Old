package com.tylerlowrey.frcscoutingapp.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.InputType;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylerlowrey.frcscoutingapp.R;

public class TextInputView extends LinearLayout
{
    private Context context;
    private String title;
    private String hint;
    private int inputType;

    public TextInputView(Context context, String title, String hint, int inputType)
    {
        super(context);

        this.context = context;
        this.title = title;
        this.hint = hint;
        this.inputType = inputType;

        init();
    }

    private void init()
    {

        //-- Create Linear Layout --

        Resources resources = context.getResources();

        this.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        int horizontalPixels = (int) convertPixelToDisplayPixel(25.0f);

        int verticalPixels = (int) convertPixelToDisplayPixel(15.0f);

        layoutParams.setMargins(horizontalPixels, verticalPixels, horizontalPixels, verticalPixels);
        this.setLayoutParams(layoutParams);


        //-- Create Title Box (TextView) --
        TextView inputBoxTitle = new TextView(context);
        inputBoxTitle.setText(title);
        inputBoxTitle.setTextColor(resources.getColor(R.color.form_input_title_text_color));
        inputBoxTitle.setTextSize(20);
        inputBoxTitle.setBackground(context.getDrawable(R.drawable.form_element_title_bg));
        inputBoxTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        int titlePadding = (int) convertPixelToDisplayPixel(5.0f);
        inputBoxTitle.setPadding(titlePadding, titlePadding, titlePadding, titlePadding);


        //-- Create Text Input (EditText --
        EditText inputBoxText = new EditText(context);

        inputBoxText.setHint(hint);
        inputBoxText.setTextColor(resources.getColor(R.color.form_input_body_text_color));
        inputBoxText.setTextSize(24);
        inputBoxText.setBackground(context.getDrawable(R.drawable.form_element_body_bg));
        inputBoxText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        int textInputPadding = (int) convertPixelToDisplayPixel(5.0f);
        inputBoxText.setPaddingRelative(textInputPadding,
                                        textInputPadding,
                                        textInputPadding,
                                        textInputPadding);

        inputBoxText.setInputType(inputType);

        this.addView(inputBoxTitle);
        this.addView(inputBoxText);

    }

    private float convertPixelToDisplayPixel(float pixelVal)
    {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                pixelVal,
                context.getResources().getDisplayMetrics()
        );
    }


}
