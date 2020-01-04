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
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tylerlowrey.frcscoutingapp.views.CheckboxInputView;
import com.tylerlowrey.frcscoutingapp.views.DropdownInputView;
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

            StringBuilder stackTraceBuilder = new StringBuilder();
            for(StackTraceElement stackTraceElement : e.getStackTrace())
            {
                stackTraceBuilder.append(stackTraceElement.toString());
            }

            Log.e(TAG, "Unable to load XML file for Match Scouting Form" + e + "\n" + stackTraceBuilder.toString());

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

            StringBuilder stackTraceBuilder = new StringBuilder();
            for(StackTraceElement stackTraceElement : e.getStackTrace())
            {
                stackTraceBuilder.append(stackTraceElement.toString());
            }

            Log.e(TAG, "Unable to load XML file for Pit Scouting Form" + e + "\n" + stackTraceBuilder.toString());

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
        String fieldName;
        switch(type)
        {

            case "text":
                fieldName = element.getAttribute("name");
                String textInputTitle = element.getAttribute("title");
                String textInputHint = element.getAttribute("hint");

                TextInputView textInputElement = new TextInputView(context,
                                                                   fieldName,
                                                                   textInputTitle,
                                                                   textInputHint,
                                                                   element.getAttribute("inputType"));



                rootView.addView(textInputElement);
                break;
            case "textarea":
                fieldName = element.getAttribute("name");
                String textAreaInputTitle = element.getAttribute("title");
                String textAreaInputHint = element.getAttribute("hint");

                //Number of lines to show before scrolling
                String textAreaNumberOfLinesStr = element.getAttribute("numLinesToShow");
                int textAreaNumberOfLines = 1;

                if(!textAreaNumberOfLinesStr.equals(""))
                    textAreaNumberOfLines = Integer.parseInt(textAreaNumberOfLinesStr);

                TextAreaInputView textAreaInputElement = new TextAreaInputView(rootView.getContext(),
                                                                                fieldName,
                                                                                textAreaInputTitle,
                                                                                textAreaInputHint,
                                                                                textAreaNumberOfLines);

                rootView.addView(textAreaInputElement);
                break;

            case "radio":
                fieldName = element.getAttribute("name");
                String radioInputTitle = element.getAttribute("title");
                String valueType = element.getAttribute("valueType");
                String radioButtonsOrientationStr = element.getAttribute("orientation");
                int radioButtonsOrientation;

                if (radioButtonsOrientationStr.equals("") || radioButtonsOrientationStr.equals("vertical"))
                    radioButtonsOrientation = RadioGroup.VERTICAL;
                else
                    radioButtonsOrientation = RadioGroup.HORIZONTAL;

                NodeList radioButtons = element.getElementsByTagName("RADIOBUTTON");

                Map<String, String> radioButtonsMap;

                radioButtonsMap = new LinkedHashMap<>();

                for(int nodeIndex = 0; nodeIndex < radioButtons.getLength(); ++nodeIndex)
                {
                    Element radioButton = (Element) radioButtons.item(nodeIndex);

                    radioButtonsMap.put(radioButton.getAttribute("name"), radioButton.getAttribute("value"));
                }

                RadioInputView radioInputView = new RadioInputView(context, fieldName, radioInputTitle,
                                                                    radioButtonsMap, valueType,
                                                                    radioButtonsOrientation);

                rootView.addView(radioInputView);

                break;

            case "checkbox":
                fieldName = element.getAttribute("name");
                String checkboxInputTitle = element.getAttribute("title");
                String checkboxValueType = element.getAttribute("valueType");

                NodeList checkboxes = element.getElementsByTagName("CHECKBOX");

                Map<String, String> checkboxesMap;

                checkboxesMap = new LinkedHashMap<>();

                for(int nodeIndex = 0; nodeIndex < checkboxes.getLength(); ++nodeIndex)
                {
                    Element radioButton = (Element) checkboxes.item(nodeIndex);

                    checkboxesMap.put(radioButton.getAttribute("name"), radioButton.getAttribute("value"));
                }

                CheckboxInputView checkboxInputView = new CheckboxInputView(context, fieldName, checkboxInputTitle,
                                                                            checkboxesMap, checkboxValueType);

                rootView.addView(checkboxInputView);
                break;
            case "dropdown":
                fieldName = element.getAttribute("name");
                String dropdownInputTitle = element.getAttribute("title");
                String dropdownInputType = element.getAttribute("inputType");

                if(dropdownInputType.equals(""))
                    dropdownInputType = "text";

                String defaultDropdownItem = element.getAttribute("defaultDropdownItem");

                NodeList dropdownItems = element.getElementsByTagName("ITEM");

                Map<String, String> dropdownItemsMap;

                dropdownItemsMap = new LinkedHashMap<>();

                for(int nodeIndex = 0; nodeIndex < dropdownItems.getLength(); ++nodeIndex)
                {
                    Element dropdownItem = (Element) dropdownItems.item(nodeIndex);

                    dropdownItemsMap.put(dropdownItem.getAttribute("name"), dropdownItem.getAttribute("value"));
                }

                DropdownInputView dropdownInputView = new DropdownInputView(context, fieldName, dropdownInputTitle,
                                                                            dropdownItemsMap, dropdownInputType,
                                                                            defaultDropdownItem);

                rootView.addView(dropdownInputView);
                break;

            default:
                throw new Exception("Invalid XML markup (XML Element did not have a valid type attribute)");
        }
    }


}
