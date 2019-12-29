package com.tylerlowrey.frcscoutingapp.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.LinearLayout;

import java.util.Map;

public class DropdownInputView extends FormInputView
{
    private Context context;
    private String title;
    private String valueType;
    private String defaultDropdownItem;
    private Map<String, String> dropdownItems;

    public DropdownInputView(Context context, String title, Map<String,String> dropdownItems,
                             String valueType, String defaultDropdownItem)
    {
        super(context);

        this.context = context;
        this.title = title;
        this.dropdownItems = dropdownItems;
        this.valueType = valueType;
        this.defaultDropdownItem = defaultDropdownItem;

        init();
    }

    private void init()
    {
        Resources resources = context.getResources();
    }

    @Override
    public String getInputName()
    {
        throw new UnsupportedOperationException("CheckBox values and names should be retrieved by using the getCheckBoxValuesandTags");
    }

    @Override
    public String getInputValue()
    {
        throw new UnsupportedOperationException("CheckBox values and names should be retrieved by using the getCheckBoxValuesandTags");
    }

    @Override
    public String getInputValueType()
    {
        return valueType;
    }

}
