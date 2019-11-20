package com.tylerlowrey.frcscoutingapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tylerlowrey.frcscoutingapp.R;

public class NavigationManager
{
    FragmentManager fragmentManager;

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

        //set addOnBackStackChangedListener
    }

    public void navigateToFragment(Fragment fragment)
    {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment).addToBackStack(null);
        transaction.commit();
    }

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
