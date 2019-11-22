package com.tylerlowrey.frcscoutingapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tylerlowrey.frcscoutingapp.R;

/**
 * A singleton class that handles the navigation of the application. This class relies on the single
 * activity, multiple fragments design of the app.
 */
public class NavigationManager
{
    private FragmentManager fragmentManager;

    private static NavigationManager navManager;

    public static NavigationManager getInstance()
    {
        if(navManager != null)
            return navManager;

        navManager = new NavigationManager();
        return navManager;
    }

    public void init(FragmentManager fragmentMgr)
    {
        this.fragmentManager = fragmentMgr;
    }

    /**
     * Replaces the currently displayed fragment with a new fragment (passed in as a parameter)
     *
     * @param fragment - The new fragment to display to the user
     */
    public void navigateToFragment(Fragment fragment)
    {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment).addToBackStack(null);
        transaction.commit();
    }

    /**
     * Navigates the user to the main menu of the app
     *
     * @param clearBackStack - a boolean that determines whether or not to clear the back stack
     *                         before navigating to the main menu. If set true, this will prevent
     *                         the user from using the Android back button to return to the previous
     *                         fragment
     */
    public void navigateToMainMenu(boolean clearBackStack)
    {
        if (clearBackStack)
        {
            //Remove the back stack so that the back button will exit the app instead of returning
            //to the fragment that was just replaced
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        navigateToFragment(MenuFragment.newInstance());

    }
}
