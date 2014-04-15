package com.hufeng.droidtools;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.hufeng.droidtool.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class MainActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        if (position == 0) {
            replaceFragment(/*ModelFragment.class*/position, ModelFragment.FRAGMENT_TAG);
        } else if (position == 1) {
            replaceFragment(/*ScreenFragment.class*/position, ScreenFragment.FRAGMENT_TAG);
        } else if (position == 2) {
            replaceFragment(/*StorageFragment.class*/position, StorageFragment.FRAGMENT_TAG);
        } else if (position == 3) {
            replaceFragment(/*FontFragment.class*/position, FontFragment.FRAGMENT_TAG);
        } else if (position == 4) {
            replaceFragment(/*DrawableFragment.class*/position, DrawableFragment.FRAGMENT_TAG);
        } else if (position == 5) {
            replaceFragment(/*FragmentDemoFragment.class*/position, FragmentDemoFragment.FRAGMENT_TAG);
        } else if (position == 6) {
            replaceFragment(/*ServiceDemoFragment.class*/position, ServiceDemoFragment.FRAGMENT_TAG);
        } else if (position == 7) {
            replaceFragment(/*ContentProviderDemoFragment.class*/position, ContentProviderDemoFragment.FRAGMENT_TAG);
        }
    }

    private void replaceFragment(int pos, String TAG) {
        Fragment fragment = getFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            switch (pos) {
                case 0:
                    fragment = ModelFragment.newFragmentInstance();
                    break;
                case 1:
                    fragment = ScreenFragment.newFragmentInstance();
                    break;
                case 2:
                    fragment = StorageFragment.newFragmentInstance();
                    break;
                case 3:
                    fragment = FontFragment.newFragmentInstance();
                    break;
                case 4:
                    fragment = DrawableFragment.newFragmentInstance();
                    break;
                case 5:
                    fragment = FragmentDemoFragment.newFragmentInstance();
                    break;
                case 6:
                    fragment = ServiceDemoFragment.newFragmentInstance();
                    break;
                case 7:
                    fragment = ContentProviderDemoFragment.newFragmentInstance();
                    break;
            }
        }
        if (fragment != null) {
            getFragmentManager().beginTransaction().replace(R.id.container, fragment, TAG).commit();
        }
    }

    private void replaceFragment(Class<?> fragment_cls, String TAG) {
        Fragment fragment = getFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            Method newFragmentInstance = null;
            try {
                newFragmentInstance = fragment_cls.getMethod("newFragmentInstance", new Class[]{});
                fragment = (Fragment)newFragmentInstance.invoke(fragment_cls, new Objects[]{});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (fragment != null) {
            getFragmentManager().beginTransaction().replace(R.id.container, fragment, TAG).commit();
        }
    }


    public void onFragmentAttached(String fragment_title) {
        mTitle = fragment_title;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id ==R.id.action_example) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
