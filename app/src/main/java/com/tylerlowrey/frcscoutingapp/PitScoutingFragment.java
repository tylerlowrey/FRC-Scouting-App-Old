package com.tylerlowrey.frcscoutingapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class PitScoutingFragment extends Fragment
{

    public static final String TAG = "PIT_SCOUTING_FRAGMENT";
    private LinearLayout formContainer;
    public static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    public static final int CAMERA_PERMISSION_CODE = 100;
    private EditText teamNumberEditText;

    public PitScoutingFragment()
    {
        // Required empty public constructor
    }

    public static PitScoutingFragment newInstance()
    {
        PitScoutingFragment fragment = new PitScoutingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        return inflater.inflate(R.layout.fragment_pit_scouting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        formContainer = view.findViewById(R.id.pit_form_root_linear_layout);
        Button takePictureBtn = view.findViewById(R.id.take_picture_button);
        takePictureBtn.setOnClickListener(onTakePictureClick);
        Button submitFormBtn = view.findViewById(R.id.pit_scouting_submit_button);
        submitFormBtn.setOnClickListener(onSubmitForm);
        imageView = view.findViewById(R.id.imageView);
        teamNumberEditText = view.findViewById(R.id.pit_form_team_number);
    }

    private View.OnClickListener onTakePictureClick = view -> {
        if (Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    };

    // This function presents the user with a permission request to use the camera
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getContext(), "Permission For Camera Use Has Been Granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getContext(), "Permission For Camera Use Has Been Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    // this function updates the previewed photo on the screen and proceeds to save the picture file in the SD card
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {

            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imageView.setImageBitmap(photo);

            // This updates the image uri to later be saved
            Uri picURI = data.getData();
            imageView.setImageURI(picURI);

            savePicture();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // This function serves to saves the imageview into the SD card under as 'user.jpg' with 'user' being the name of the user
    private void savePicture() {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();


        FileOutputStream outStream ;

        // Write to External Storage
        try
        {
            File fileStorageRootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File appRootStorageDir = new File(fileStorageRootDir + "/FRC Scouting App");

            if(!appRootStorageDir.exists())
                appRootStorageDir.mkdirs();

            File appLocalStorageDir = new File(appRootStorageDir, "local");

            if(!appLocalStorageDir.exists())
                appLocalStorageDir.mkdirs();

            SharedPreferences sharedPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
            String username = sharedPrefs.getString(getString(R.string.shared_prefs_current_user),
                    MainActivity.DEFAULT_USERNAME);

            String fileName = String.format("%s_%s_%s.jpg", username, teamNumberEditText.getText(), Calendar.getInstance().getTime());
            File outFile = new File(appLocalStorageDir, fileName);

            outStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();

        }
        catch (IOException e)
        {
            MainActivity.makeToast(getContext(), "ERROR: Unable to Save Picture", Toast.LENGTH_LONG);
        }
    }

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

        FileUploader fileUploader = FileUploader.getInstance();

        SharedPreferences sharedPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        String username = sharedPrefs.getString(getString(R.string.shared_prefs_current_user),
                                                MainActivity.DEFAULT_USERNAME);


        try
        {
            String nameOfCSVFile = String.format("%s_%s_%s.csv", username, teamNumberEditText.getText(), Calendar.getInstance().getTime());
            if(fileUploader.hasFilePermissions(getActivity()))
                fileUploader.saveTextFileLocally(nameOfCSVFile, headerStr.toString() + dataStr.toString());

            MainActivity.makeToast(getContext(), "Form Saved Successfully", Toast.LENGTH_LONG);
        }
        catch (IOException e)
        {
            Log.d(TAG, "Error while trying to save file to external storage. Error details: " + e.toString());
            MainActivity.makeToast(getContext(), "ERROR: Unable to Save File", Toast.LENGTH_LONG);
        }

    };

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
