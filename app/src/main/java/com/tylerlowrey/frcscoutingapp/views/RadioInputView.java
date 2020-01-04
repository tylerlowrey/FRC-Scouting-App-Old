package com.tylerlowrey.frcscoutingapp.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.tylerlowrey.frcscoutingapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RadioInputView extends FormInputView
{
    private static final String TAG = "RADIO_INPUT_VIEW";
    private Context context;
    private String fieldName;
    private String title;
    private Map<String, String> radioButtonsMap;
    private String defaultRadioButton;
    private String inputType;
    private int radioButtonsOrientation;
    private RadioGroup radioGroup;

    /**
     * Creates a Form input box that contains radio buttons that can be selected
     *
     * @param context The context, should come from the root view that this view is being added to
     * @param title  The title of the radio input view
     * @param radioButtonsMap A map of key value pairs where the key is the name attribute (which is
     *                     what will be displayed to the user) and the value is the underlying value
     *                     that the name represents.
     * @param inputType The String representing what the type the radioButtons value represents.
     *                    valid options are: integer, float, text
     * @param orientation The orientation of the radio buttons. Valid input is RadioGroup.VERTICAL
     *                    or RadioGroup.HORIZONTAL
     */
    public RadioInputView(Context context, String fieldName, String title, Map<String, String> radioButtonsMap,
                          String inputType, int orientation)
    {
        super(context);

        this.context = context;
        this.fieldName = fieldName;
        this.title = title;
        this.inputType = inputType;
        this.radioButtonsMap = radioButtonsMap;
        this.radioButtonsOrientation = orientation;

        init();
    }

    private void init()
    {
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


        //-- Create RadioGroup for holding RadioButtons --
        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(radioButtonsOrientation);
        radioGroup.setBackground(context.getDrawable(R.drawable.form_element_body_bg));
        int radioGroupPadding = (int) convertDisplayPixelToPixel(2.0f);
        radioGroup.setPadding(radioGroupPadding, radioGroupPadding, radioGroupPadding, radioGroupPadding);
        RadioGroup.LayoutParams radioGroupLayoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.MATCH_PARENT);
        radioGroup.setLayoutParams(radioGroupLayoutParams);
        radioGroup.setTag(inputType);

        radioGroup.setTag(R.id.input_type, "radio");

        //-- Create RadioButtons --
        for(Map.Entry<String, String> radioButtonKeyVal : radioButtonsMap.entrySet())
        {
            RadioButton radioButton = new RadioButton(context);
            RadioGroup.LayoutParams radioButtonLayoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            int marginVal = (int) convertDisplayPixelToPixel(4.0f);
            radioButtonLayoutParams.setMargins(marginVal, marginVal, marginVal, marginVal);
            radioButton.setLayoutParams(radioButtonLayoutParams);
            radioButton.setEnabled(true);
            radioButton.setClickable(true);
            radioButton.setText(radioButtonKeyVal.getKey());
            radioButton.setTextColor(resources.getColor(R.color.form_input_body_text_color));
            radioButton.setButtonTintList(AppCompatResources.getColorStateList(context, R.color.form_radio_button_tint));
            radioButton.setTag(radioButtonKeyVal.getValue());
            radioButton.setTextSize(24);


            radioGroup.addView(radioButton);

        }

        this.radioGroup = radioGroup;

        this.addView(inputBoxTitle);
        this.addView(radioGroup);
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

        RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());

        if (selectedRadioButton == null)
            return null;

        return radioButtonsMap.get(selectedRadioButton.getText().toString());

    }

    @Override
    public String getInputType()
    {
        return inputType;
    }

}
