package com.tylerlowrey.frcscoutingapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FormGenerator
{
    public static final String TAG = "FORM_GENERATOR";


    public static void generateMatchScoutingForm(AppCompatActivity activity, ViewGroup rootView)
    {
        Context context = activity.getApplicationContext();
        try
        {
            InputStream xmlFileInputStream = context.getResources().openRawResource(R.raw.match_scouting_form);

            buildScoutingFormFromXML(xmlFileInputStream, rootView);
        }
        catch (Exception e)
        {
            MainActivity.displayErrorDialog(activity, "Unable to load XML file for Match Scouting Form");
            Log.e(TAG, "Unable to load XML file for Match Scouting Form" + e);
            NavigationManager.getInstance().navigateToFragment(new MenuFragment());
        }


    }

    public static void generatePitScoutingForm(AppCompatActivity activity, ViewGroup rootView)
    {
        Context context = activity.getApplicationContext();
        try
        {
            InputStream xmlFileInputStream = context.getResources().openRawResource(R.raw.pit_scouting_form);

            buildScoutingFormFromXML(xmlFileInputStream, rootView);

        }
        catch (Exception e)
        {
            MainActivity.displayErrorDialog(activity, "Unable to load XML file for Pit Scouting Form");
            Log.e(TAG, "Unable to load XML file for Pit Scouting Form" + e);
            NavigationManager.getInstance().navigateToFragment(new MenuFragment());
        }
    }

    private static void buildScoutingFormFromXML(InputStream xmlFileInputStream, ViewGroup rootView)
                                                throws Exception
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFileInputStream);
        doc.getDocumentElement().normalize();

        NodeList inputElements = doc.getElementsByTagName("INPUT");

        for(int nodeIndex = 0; nodeIndex < inputElements.getLength(); ++nodeIndex)
        {
            Element inputElement = (Element) inputElements.item(nodeIndex);

            addViewElementByType(inputElement, rootView);
        }
    }


    //TODO: Convert all hardcoded pixel values to sp
    private static void addViewElementByType(Element element, ViewGroup rootView) throws Exception
    {

        Resources resources = rootView.getResources();

        String type = element.getAttribute("type");

        LinearLayout inputBoxHolder = new LinearLayout(rootView.getContext());
        inputBoxHolder.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        int horizontalPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                25,
                resources.getDisplayMetrics()
        );

        int verticalPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                15,
                resources.getDisplayMetrics()
        );

        layoutParams.setMargins(horizontalPixels, verticalPixels, horizontalPixels, verticalPixels);
        inputBoxHolder.setLayoutParams(layoutParams);


        String title;
        TextView inputBoxTitle;

        switch(type)
        {

            case "text":
                //TODO: Throw this into a function for creating the title part of the input box
                title = element.getAttribute("title");
                inputBoxTitle = new TextView(rootView.getContext());
                inputBoxTitle.setText(title);
                inputBoxTitle.setTextColor(Color.parseColor("#000000"));
                inputBoxTitle.setTextSize(20.0f);
                inputBoxTitle.setBackground(rootView.getContext().getDrawable(R.drawable.form_element_title_bg));
                inputBoxTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                inputBoxTitle.setPaddingRelative(5, 5, 5, 5);

                EditText textInput = new EditText(rootView.getContext());

                textInput.setHint(element.getAttribute("hint"));
                textInput.setTextColor(Color.parseColor("#000000"));
                textInput.setTextSize(20.0f);
                textInput.setBackground(rootView.getContext().getDrawable(R.drawable.form_element_body_bg));
                textInput.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                textInput.setPaddingRelative(2, 5, 2, 5);

                if(element.getAttribute("inputType").equals("number"))
                    textInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                else
                    textInput.setInputType(InputType.TYPE_CLASS_TEXT);

                inputBoxHolder.addView(inputBoxTitle);
                inputBoxHolder.addView(textInput);
                rootView.addView(inputBoxHolder);
                break;
                /*
            case "textarea":
                title = element.getAttribute("title");
                inputBoxTitle = new TextView(rootView.getContext());
                inputBoxTitle.setText(title);

                inputBoxHolder.addView(inputBoxTitle);
                break;
            case "select":
                title = element.getAttribute("title");
                inputBoxTitle = new TextView(rootView.getContext());
                inputBoxTitle.setText(title);

                inputBoxHolder.addView(inputBoxTitle);
                break;
            case "checkbox":
                title = element.getAttribute("title");
                inputBoxTitle = new TextView(rootView.getContext());
                inputBoxTitle.setText(title);

                inputBoxHolder.addView(inputBoxTitle);
                break;
            case "dropdown":
                title = element.getAttribute("title");
                inputBoxTitle = new TextView(rootView.getContext());
                inputBoxTitle.setText(title);

                inputBoxHolder.addView(inputBoxTitle);
                break;
                 */
            default:
                throw new Exception("Invalid XML markup (XML Element did not have a valid type attribute)");
        }
    }


}
