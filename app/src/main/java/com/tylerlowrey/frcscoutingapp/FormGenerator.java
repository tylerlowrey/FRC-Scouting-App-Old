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

import com.tylerlowrey.frcscoutingapp.views.RadioInputView;
import com.tylerlowrey.frcscoutingapp.views.TextAreaInputView;
import com.tylerlowrey.frcscoutingapp.views.TextInputView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
            Log.e(TAG, "Unable to load XML file for Pit Scouting Form " + e);
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


    private static void addViewElementByType(Element element, ViewGroup rootView) throws Exception
    {

        Context context = rootView.getContext();

        String type = element.getAttribute("type");

        switch(type)
        {

            case "text":
                String textInputTitle = element.getAttribute("title");
                String textInputHint = element.getAttribute("hint");

                TextInputView textInputElement;

                if(element.getAttribute("inputType").equals("number"))
                    textInputElement = new TextInputView(context, textInputTitle, textInputHint,
                                                            InputType.TYPE_CLASS_NUMBER);
                else
                    textInputElement = new TextInputView(context, textInputTitle, textInputHint,
                                                            InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);


                rootView.addView(textInputElement);
                break;
            case "textarea":
                String textAreaInputTitle = element.getAttribute("title");
                String textAreaInputHint = element.getAttribute("hint");

                //Number of lines to show before scrolling
                String textAreaNumberOfLinesStr = element.getAttribute("numLinesToShow");
                int textAreaNumberOfLines = 1;

                if(!textAreaNumberOfLinesStr.equals(""))
                    textAreaNumberOfLines = Integer.parseInt(textAreaNumberOfLinesStr);

                TextAreaInputView textAreaInputElement = new TextAreaInputView(rootView.getContext(),
                                                                                textAreaInputTitle,
                                                                                textAreaInputHint,
                                                                                textAreaNumberOfLines);

                rootView.addView(textAreaInputElement);
                break;

            case "radio":
                String radioGroupTitle = element.getAttribute("title");
                String valueType = element.getAttribute("valueType");
                String defaultRadioButton = element.getAttribute("defaultRadioButton");

                NodeList radioButtons = element.getElementsByTagName("RADIOBUTTON");

                Map<String, String> radioButtonsMap;

                radioButtonsMap = new LinkedHashMap<>();

                for(int nodeIndex = 0; nodeIndex < radioButtons.getLength(); ++nodeIndex)
                {
                    Element radioButton = (Element) radioButtons.item(nodeIndex);

                    radioButtonsMap.put(radioButton.getAttribute("name"), radioButton.getNodeValue());
                }

                RadioInputView radioInputView = new RadioInputView(context, radioGroupTitle,
                                                                    radioButtonsMap, valueType,
                                                                    defaultRadioButton);

                rootView.addView(radioInputView);
                break;
                /*
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
