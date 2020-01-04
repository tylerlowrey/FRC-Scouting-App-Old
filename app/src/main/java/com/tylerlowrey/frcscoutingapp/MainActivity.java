package com.tylerlowrey.frcscoutingapp;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


/**
 * The primary and only Activity for the App. The Main Activity handles the interaction with the
 * Android back button, Inflates the AppBar options menu, and provides functionality for the
 * AppBar options menu
 */
public class MainActivity extends AppCompatActivity
{

    public static final String TAG = "MAIN_ACTIVITY";
    public static final String DEFAULT_USERNAME = "Default_User";

    /**
     * Initializes the singleton instance of the NavigationManager class and sets the activity
     * layout for the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationManager navManager = NavigationManager.getInstance();
        navManager.init(getSupportFragmentManager());

        navManager.navigateToFragment(LoginScreenFragment.newInstance());
    }

    /**
     * Makes sure that the Android back button pops the back stack so that the user can properly
     * navigate back to older fragments
     *
     * @post The size of the backstack will decrease by one (unless it is already 1)
     */
    @Override
    public void onBackPressed()
    {
        if(getSupportFragmentManager().getBackStackEntryCount() > 1)
        {
            getSupportFragmentManager().popBackStack();
        }
        else
        {
            this.finish();
        }
    }


    /**
     * Inflates the AppBar options menu (defined in res/menu/optionsmenu.xml)
     */
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;
    }

    /**
     * Navigates the user based on what App option menu item they selected
     *
     * @param item - The menu item that has been selected by the user
     *
     * @pre item must be a valid menu item as defined in res/menu/optionsmenu.xml
     * @post The user will be navigated to the fragment corresponding to the menu item
     * Code adapted from Zybooks Figure 4.1.3
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_main_menu:
                NavigationManager.getInstance().navigateToFragment(MenuFragment.newInstance());
                return true;
            case R.id.action_change_user:
                NavigationManager.getInstance().navigateToFragment(LoginScreenFragment.newInstance());
                return true;
            case R.id.action_settings:
                NavigationManager.getInstance().navigateToFragment(SettingsFragment.newInstance());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Displays a Toast to the user to provide feedback
     */
    public static void makeToast(Context context, String message, int duration)
    {
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    /**
     * Displays an error dialog box
     */
    public static void displayErrorDialog(AppCompatActivity activity, String message)
    {

        FragmentManager manager = activity.getSupportFragmentManager();
        ErrorDialogFragment dialog = new ErrorDialogFragment(message);
        dialog.show(manager, "errorDialog");
    }
}
