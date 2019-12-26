package com.tylerlowrey.frcscoutingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.Calendar;

/**
 * Provides logic and the Fragment implementation for the Match Scouting Form
 * Adds event listeners to the buttons of the Match Scouting Fragment
 */
public class MatchScoutingFragment extends Fragment
{

    public static final String TAG = "MATCH_SCOUTING_FRAGMENT";
    private LinearLayout formContainer;
    private EditText teamNumberEditText;

    public MatchScoutingFragment()
    {
        // Required empty public constructor
    }

    /**
     * Returns a new instance of the PitScoutingFragment class
     * @return MatchScoutingFragment - A new instance of the MatchScoutingFragment class
     */
    public static MatchScoutingFragment newInstance()
    {
        MatchScoutingFragment fragment = new MatchScoutingFragment();
        return fragment;
    }

    /**
     * Inflates the fragment and makes sure the AppBar is shown to the user
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_scouting, container, false);
    }


    /**
     * This function sets up all of the button click event handler functions and stores
     * references to view objects that are used in other functions in order to grab data
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        formContainer = view.findViewById(R.id.match_form_root_linear_layout);
        teamNumberEditText = view.findViewById(R.id.match_form_team_number);

        Button submitFormBtn = view.findViewById(R.id.match_scouting_submit_button);
        submitFormBtn.setOnClickListener(onSubmitForm);

    }

    /**
     * Iterates through form data and saves the data onto external storage
     *
     * @pre All form data should be valid input
     * @post A new file with the format USERNAME_TEAMNUMBER_DATETIME.csv will be stored on the external
     *       storage
     */
    private View.OnClickListener onSubmitForm = (View view) -> {
        StringBuilder headerStr = new StringBuilder();
        StringBuilder dataStr = new StringBuilder();

        for(int i = 0; i < formContainer.getChildCount(); ++i)
        {
            if(formContainer.getChildAt(i) instanceof LinearLayout)
            {
                LinearLayout formElement = (LinearLayout) formContainer.getChildAt(i);
                TextView formElementTitle = (TextView) formElement.getChildAt(0);
                headerStr.append(formElementTitle.getText().toString()).append(",");
                dataStr.append(getStringDataFromView(formElement.getChildAt(1))).append(",");

            }
        }

        dataStr.replace(dataStr.length() - 1, dataStr.length(), "\n");
        headerStr.replace(headerStr.length() - 1, headerStr.length(), "\n");

        FileSaver fileSaver = FileSaver.getInstance();

        SharedPreferences sharedPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        String username = sharedPrefs.getString(getString(R.string.shared_prefs_current_user),
                MainActivity.DEFAULT_USERNAME);


        try
        {
            String nameOfCSVFile = String.format("%s_%s_%s.csv", username, teamNumberEditText.getText(), Calendar.getInstance().getTime());
            if(fileSaver.hasFilePermissions(getActivity()))
                fileSaver.saveTextFileLocally(nameOfCSVFile, headerStr.toString() + dataStr.toString());

            MainActivity.makeToast(getContext(), "Form Saved Successfully", Toast.LENGTH_LONG);
        }
        catch (IOException e)
        {
            Log.d(TAG, "Error while trying to save file to external storage. Error details: " + e.toString());
            MainActivity.makeToast(getContext(), "ERROR: Unable to Save File", Toast.LENGTH_LONG);
        }

    };

    /**
     * Takes in a view and returns the data that is contained within the view
     *
     * @param view - a valid View object.
     * @return String - If view is an EditText or RadioGroup View, Returns the data contained within the view
     */
    private String getStringDataFromView(View view)
    {
        if(view instanceof EditText)
        {
            return ((EditText)view).getText().toString();
        }
        else if (view instanceof RadioGroup)
        {
            RadioGroup radioGroup = (RadioGroup) view;
            RadioButton selectedButton = getActivity().findViewById(radioGroup.getCheckedRadioButtonId());
            return selectedButton.getText().toString();
        }

        return "";
    }


}
