package com.tylerlowrey.frcscoutingapp.views;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tylerlowrey.frcscoutingapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DropdownInputView extends FormInputView
{
    private Context context;
    private String title;
    private String inputType;
    private String defaultDropdownItem;
    private Map<String, String> dropdownItems;
    private Spinner spinner;

    public DropdownInputView(Context context, String title, Map<String,String> dropdownItems,
                             String inputType, String defaultDropdownItem)
    {
        super(context);

        this.context = context;
        this.title = title;
        this.dropdownItems = dropdownItems;
        this.inputType = inputType;
        this.defaultDropdownItem = defaultDropdownItem;

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


        //-- Create Dropdown (Spinner) --
        List<String> dropdownNames = new ArrayList<>();

        for(Map.Entry<String,String> dropdownKeyVal: dropdownItems.entrySet())
        {
            dropdownNames.add(dropdownKeyVal.getKey());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                            R.layout.spinner_item, dropdownNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinner = new Spinner(context);
        spinner.setAdapter(adapter);

        if(!defaultDropdownItem.equals(""))
            spinner.setSelection(adapter.getPosition(defaultDropdownItem));

        //-- Put Spinner in LinearLayout in order to fix styling issues with dropdown arrow --
        LinearLayout spinnerHolder = new LinearLayout(context);
        spinnerHolder.setOrientation(LinearLayout.VERTICAL);
        layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);

        spinnerHolder.setLayoutParams(layoutParams);
        spinnerHolder.setBackground(context.getDrawable(R.drawable.form_element_body_bg));
        spinnerHolder.setTag(R.id.input_type, "dropdown");

        spinnerHolder.addView(spinner);


        this.addView(inputBoxTitle);
        this.addView(spinnerHolder);

        
    }

    @Override
    public String getInputName()
    {
        return spinner.getSelectedItem().toString();
    }

    @Override
    public String getInputValue()
    {
        return dropdownItems.get(spinner.getSelectedItem().toString());
    }

    @Override
    public String getInputType()
    {
        return inputType;
    }

}
