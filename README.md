# FRC-Scouting-App
An Android application intended for use by FRC Teams in order to scout during competitions

## Modifying the App

The app currently provides example forms for both Match and Pit scouting. In order to edit these forms you need to modify the match_scouting_form.xml and pit_scouting_form.xml respectively. These files are located in the `app/src/main/res/raw` folder

Both forms have examples for every supported form type (Text fields, Text Area fields, Selection/Radio Buttons, Checkboxes, and Dropdowns) . Once you have made your changes you can deploy them to your device (using Android Studio) by following these instructions from Google: https://developer.android.com/studio/run/device

## Upcoming Features

Right now the app only supports basic uploading of files in a CSV format. The roadmap for the future of this project is the following:
- Create a web application that serves as a scouting dashboard that the data can be uploaded to and displayed on
- Transition from using xml files for creating forms to using the scouting dashboard. Devices that are subscribed to a specific dashboard server can sync their form.
- Provide multiple options for storing scouting data, including: locally storing CSV files, uploading CSV files to the server, uploading form data using JSON to be stored in a database on the server


