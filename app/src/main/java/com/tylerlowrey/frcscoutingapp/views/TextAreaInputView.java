package com.tylerlowrey.frcscoutingapp.views;

import android.content.Context;
import android.content.res.Resources;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylerlowrey.frcscoutingapp.R;

public class TextAreaInputView extends FormInputView
{
    private Context context;
    private String fieldName;
    private String title;
    private String hint;
    private int numLinesToShow;
    private EditText editText;

    public TextAreaInputView(Context context, String fieldName, String title, String hint, int numLinesToShow)
    {
        super(context);

        this.context = context;
        this.fieldName = fieldName;
        this.title = title;
        this.hint = hint;
        this.numLinesToShow = numLinesToShow;

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


        //-- Create TextArea (EditText) --
        EditText inputTextArea = new EditText(context);
        inputTextArea.setHint(hint);
        inputTextArea.setTextColor(resources.getColor(R.color.form_input_body_text_color));

        inputTextArea.setTextSize(24);
        inputTextArea.setBackground(context.getDrawable(R.drawable.form_element_body_bg));
        inputTextArea.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        inputTextArea.setGravity(Gravity.TOP | Gravity.START);
        int textInputPadding = (int) convertDisplayPixelToPixel(5.0f);
        inputTextArea.setPadding(textInputPadding,
                textInputPadding,
                textInputPadding,
                textInputPadding);

        inputTextArea.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        inputTextArea.setSingleLine(false);

        inputTextArea.setVerticalScrollBarEnabled(true);
        inputTextArea.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        inputTextArea.setMinLines(numLinesToShow);
        inputTextArea.setMaxLines(numLinesToShow);

        inputTextArea.setTag(R.id.input_type, "textarea");

        this.editText = inputTextArea;
        this.addView(inputBoxTitle);
        this.addView(inputTextArea);
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public String getFieldName()
    {
        return fieldName;
    }

    @Override
    public String getInputValue()
    {
        return editText.getText().toString();
    }

    @Override
    public String getInputType()
    {
        return "text";
    }

}
