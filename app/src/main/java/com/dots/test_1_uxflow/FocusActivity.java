package com.dots.test_1_uxflow;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.dots.test_1_uxflow.app.MainApplication;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

/**
 * Created by AdrianHsu on 15/8/14.
 */
public class FocusActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_focus);

    // Handle Toolbar
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Create the AccountHeader
    buildHeader(false, savedInstanceState);
    buildDrawer(toolbar, savedInstanceState);
  }

  private void buildDrawer(Toolbar toolbar, Bundle savedInstanceState) {

    //Create the drawer
    MainApplication.result = new DrawerBuilder()
      .withActivity(this)
      .withToolbar(toolbar)
      .withAccountHeader(MainApplication.headerResult) //set the AccountHeader we created earlier
      // for the header
      .addDrawerItems(
        new PrimaryDrawerItem().withName(R.string.drawer_item_dashboard).withIcon(FontAwesome
          .Icon.faw_home).withIdentifier(0),
        new PrimaryDrawerItem().withName(R.string.drawer_item_focus).withIcon(FontAwesome.Icon
          .faw_check_circle_o).withIdentifier(1),
        new PrimaryDrawerItem().withName(R.string.drawer_item_kick).withIcon(FontAwesome.Icon
          .faw_users).withIdentifier(2),
        new PrimaryDrawerItem().withName(R.string.drawer_item_timeline).withIcon(FontAwesome.Icon
          .faw_clock_o).withIdentifier(3),
        new PrimaryDrawerItem().withName(R.string.drawer_item_overview).withIcon(FontAwesome.Icon
          .faw_eye).withIdentifier(4),
        new PrimaryDrawerItem().withName(R.string.drawer_item_profile).withIcon(FontAwesome.Icon
          .faw_user).withIdentifier(5),
        new PrimaryDrawerItem().withName(R.string.drawer_item_logout).withIcon(FontAwesome.Icon
          .faw_sign_out).withIdentifier(6)
      ) // add the items we want to use with our Drawer
//      .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
//        @Override
//        public boolean onNavigationClickListener(View clickedView) {
//          //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
//          //if the back arrow is shown. close the activity
//          MainActivity.this.finish();
//          //return true if we have consumed the event
//          return true;
//        }
//      })
      .addStickyDrawerItems(
        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome
          .Icon.faw_cog).withIdentifier(7)
      )
      .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
        @Override
        public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
          //check if the drawerItem is set.
          //there are different reasons for the drawerItem to be null
          //--> click on the header
          //--> click on the footer
          //those items don't contain a drawerItem

          if (drawerItem != null) {
            Intent intent = null;
            if (drawerItem.getIdentifier() == 0) {
              intent = new Intent(FocusActivity.this, DashboardActivity.class);
            } else if (drawerItem.getIdentifier() == 1) {
//              intent = new Intent(FocusActivity.this, FocusActivity.class);
            } else if (drawerItem.getIdentifier() == 2) {
              intent = new Intent(FocusActivity.this, KickActivity.class);
            } else if (drawerItem.getIdentifier() == 3) {
              intent = new Intent(FocusActivity.this, TimelineActivity.class);
            } else if (drawerItem.getIdentifier() == 4) {
              intent = new Intent(FocusActivity.this, OverviewActivity.class);
            } else if (drawerItem.getIdentifier() == 5) {
              intent = new Intent(FocusActivity.this, ProfileActivity.class);
            } else if (drawerItem.getIdentifier() == 6) {
//              intent = new Intent(DashboardActivity.this, MainActivity.class);
            } else if (drawerItem.getIdentifier() == 7) {
              intent = new LibsBuilder()
                .withFields(R.string.class.getFields())
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                .intent(FocusActivity.this);
            }
            if (intent != null) {
              FocusActivity.this.startActivity(intent);
            }
          }


          return false;
        }
      })
      .withAnimateDrawerItems(true)
      .withSavedInstance(savedInstanceState)
      .withSelectedItem(1)
      .build();
  }
  public void onTimelineClick(View view) {
    Intent it = new Intent(this, TimelineActivity.class);
    startActivity(it);
  }

  /**
   * small helper method to reuse the logic to build the AccountHeader
   * this will be used to replace the header of the drawer with a compact/normal header
   *
   * @param compact
   * @param savedInstanceState
   */
  private void buildHeader(boolean compact, Bundle savedInstanceState) {
    // Create the AccountHeader
    MainApplication.headerResult = new AccountHeaderBuilder()
      .withActivity(this)
      .withHeaderBackground(R.drawable.header)
      .withCompactStyle(compact)
      .addProfiles(
        MainApplication.profile
      )
      .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
        @Override
        public boolean onProfileChanged(View view, IProfile profile, boolean current) {

          //false if you have not consumed the event and it should close the drawer
          return false;
        }
      })
      .withSavedInstance(savedInstanceState)
      .build();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    //add the values which need to be saved from the drawer to the bundle
    outState = MainApplication.result.saveInstanceState(outState);
    //add the values which need to be saved from the accountHeader to the bundle
    outState = MainApplication.headerResult.saveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onBackPressed() {
    //handle the back press :D close the drawer first and if the drawer is closed close the activity
    if (MainApplication.result != null && MainApplication.result.isDrawerOpen()) {
      MainApplication.result.closeDrawer();
    } else {
      Intent intent = new Intent(FocusActivity.this, DashboardActivity.class);
      FocusActivity.this.startActivity(intent);
    }
  }
}
