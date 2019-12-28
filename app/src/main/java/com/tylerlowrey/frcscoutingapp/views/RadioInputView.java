package com.tylerlowrey.frcscoutingapp.views;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;

import java.util.Map;

public class RadioInputView extends LinearLayout
{
    private Context context;
    private String title;
    private Map<String, ?> radioButtons;
    private String defaultRadioButton;

    /**
     * Creates a Form input box that contains radio buttons that can be selected
     *
     * @param context The context, should come from the root view that this view is being added to
     * @param title  The title of the radio input view
     * @param radioButtons A map of key value pairs where the key is the name attribute (which is
     *                     what will be displayed to the user) and the value is the underlying value
     *                     that the name represents.
     * @param valueType The String representing what the type the radioButtons value represents.
     *                    valid options are: integer, float, string
     * @param defaultRadioButton The name attribute of the radio button that will be selected by
     *                           default. If you do not want any of the radio buttons selected,
     *                           input null
     */
    public RadioInputView(Context context, String title, Map<String, String> radioButtons,
                          String valueType, String defaultRadioButton)
    {
        super(context);

        this.context = context;
        this.title = title;
        this.radioButtons = radioButtons;
        this.defaultRadioButton = defaultRadioButton;

        init();
    }

    private void init()
    {

    }

    /**
     * Converts a dp (Display Pixel) value to its pixel equivalent
     *
     * @param pixelVal The display pixel value
     *
     * @return float - The equivalent pixel value converted from display pixels
     */
    private float convertDisplayPixelToPixel(float pixelVal)
    {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                pixelVal,
                context.getResources().getDisplayMetrics()
        );
    }
}
