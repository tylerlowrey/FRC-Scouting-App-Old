package com.tylerlowrey.frcscoutingapp.views;

import android.content.Context;
import android.content.res.Resources;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylerlowrey.frcscoutingapp.R;

public class TextInputView extends FormInputView
{
    private Context context;
    private String title;
    private String hint;
    private String inputType;
    private EditText editText;

    public TextInputView(Context context, String title, String hint, String inputType)
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

        int horizontalPixels = (int) convertDisplayPixelToPixel(25.0f);

        int verticalPixels = (int) convertDisplayPixelToPixel(15.0f);

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
        int titlePadding = (int) convertDisplayPixelToPixel(5.0f);
        inputBoxTitle.setPadding(titlePadding, titlePadding, titlePadding, titlePadding);


        //-- Create Text Input (EditText --
        EditText inputBoxText = new EditText(context);

        inputBoxText.setHint(hint);
        inputBoxText.setTextColor(resources.getColor(R.color.form_input_body_text_color));
        inputBoxText.setTextSize(24);
        inputBoxText.setBackground(context.getDrawable(R.drawable.form_element_body_bg));
        inputBoxText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        int textInputPadding = (int) convertDisplayPixelToPixel(5.0f);
        inputBoxText.setPadding(textInputPadding,
                                textInputPadding,
                                textInputPadding,
                                textInputPadding);
        if(inputType.equals("number"))
            inputBoxText.setInputType(InputType.TYPE_CLASS_NUMBER);
        else
            inputBoxText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        inputBoxText.setTag(R.id.input_type, "text");

        this.editText = inputBoxText;

        this.addView(inputBoxTitle);
        this.addView(inputBoxText);

    }

    @Override
    public String getInputName()
    {
        return (String) editText.getTag();
    }

    @Override
    public String getInputValue()
    {
        return editText.getText().toString();
    }

    @Override
    public String getInputType()
    {
        return inputType;
    }


}
