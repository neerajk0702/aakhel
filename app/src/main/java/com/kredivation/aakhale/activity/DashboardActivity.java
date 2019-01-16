package com.kredivation.aakhale.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kredivation.aakhale.Constants;
import com.kredivation.aakhale.R;
import com.kredivation.aakhale.fragments.AddAcademicsFragments;
import com.kredivation.aakhale.fragments.AddScoreFragment;
import com.kredivation.aakhale.fragments.AddSportsFragment;
import com.kredivation.aakhale.fragments.AddTeamFragment;
import com.kredivation.aakhale.fragments.AddTournament;
import com.kredivation.aakhale.fragments.AddUmpiresFragment;
import com.kredivation.aakhale.fragments.CreateGroundFragment;
import com.kredivation.aakhale.fragments.CreateMatchFragment;
import com.kredivation.aakhale.fragments.CreatePostFragment;
import com.kredivation.aakhale.fragments.CreateScoreCardFragment;
import com.kredivation.aakhale.fragments.CreateTeamFragment;
import com.kredivation.aakhale.fragments.HomeFragment;
import com.kredivation.aakhale.fragments.ScheduleFragment;
import com.kredivation.aakhale.fragments.ScoreCardFragment;
import com.kredivation.aakhale.runtimepermission.PermissionResultCallback;
import com.kredivation.aakhale.runtimepermission.PermissionUtils;
import com.kredivation.aakhale.utility.Contants;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {

    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    private int REQUEST_CODE_GPS_PERMISSIONS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        HomeFragment homeFragment = new HomeFragment();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.mainView, homeFragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        runTimePermission();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }*/


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_user) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            CreateTeamFragment createTeamFragment = new CreateTeamFragment();
            updateFragment(createTeamFragment, null);
        } else if (id == R.id.nav_gallery) {
            AddAcademicsFragments academicsFragment = new AddAcademicsFragments();
            updateFragment(academicsFragment, null);

        } else if (id == R.id.nav_slideshow) {
            AddTournament academicsFragment = new AddTournament();
            updateFragment(academicsFragment, null);

        } else if (id == R.id.nav_manage) {
            AddScoreFragment addScoreFragment = new AddScoreFragment();
            updateFragment(addScoreFragment, null);

        } else if (id == R.id.matchcreate) {
            CreateMatchFragment createTeamFragment = new CreateMatchFragment();
            updateFragment(createTeamFragment, null);

        } else if (id == R.id.addsports) {
            AddSportsFragment addSportsFragmentelse = new AddSportsFragment();
            updateFragment(addSportsFragmentelse, null);

        } else if (id == R.id.createground) {
            CreateGroundFragment createGroundFragment = new CreateGroundFragment();
            updateFragment(createGroundFragment, null);

        } else if (id == R.id.cretescorecard) {
            CreateScoreCardFragment createScoreCardFragment = new CreateScoreCardFragment();
            updateFragment(createScoreCardFragment, null);

        } else if (id == R.id.scoreCard) {
            ScoreCardFragment scoreCardFragment = new ScoreCardFragment();
            updateFragment(scoreCardFragment, null);

        } else if (id == R.id.createPost) {
            CreatePostFragment createPostFragment = new CreatePostFragment();
            updateFragment(createPostFragment, null);

        } else if (id == R.id.adumpire) {
            AddUmpiresFragment addUmpiresFragment = new AddUmpiresFragment();
            updateFragment(addUmpiresFragment, null);

        } else if (id == R.id.Schedule) {
            ScheduleFragment scheduleFragment = new ScheduleFragment();
            updateFragment(scheduleFragment, null);

        } else if (id == R.id.addTeam) {
            AddTeamFragment addTeamFragment = new AddTeamFragment();
            updateFragment(addTeamFragment, null);

        }


        if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {

    }


    public void updateFragment(Fragment pageFragment, Bundle bundle) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        pageFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.mainView, pageFragment);
        fragmentTransaction.commit();
    }

    private void runTimePermission() {
        permissionUtils = new PermissionUtils(DashboardActivity.this);

        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.CAMERA);
        permissions.add(android.Manifest.permission.WAKE_LOCK);
        permissions.add(Manifest.permission.SEND_SMS);
        permissions.add(Manifest.permission.ACCESS_NOTIFICATION_POLICY);


        permissionUtils.check_permission(permissions, "Location,Storage Services Permissions are required for this App.", REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
    }

    @Override
    public void PermissionGranted(int request_code) {
        checkGpsEnable();
        //startLocationAlarmService();
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY", "GRANTED");
        finish();
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION", "DENIED");
        finish();
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION", "NEVER ASK AGAIN");
        neverAskAgainAlert();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            permissionUtils.check_permission(permissions, "Location, Phone and Storage Services Permissions are required for this App.", REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
        if (requestCode == REQUEST_CODE_GPS_PERMISSIONS) {
            checkGpsEnable();
        }
    }

    private void neverAskAgainAlert() {
        //Previously Permission Request was cancelled with 'Dont Ask Again',
        // Redirect to Settings after showing Information about why you need the permission
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle("Need Multiple Permissions");
        builder.setCancelable(false);
        builder.setMessage("Location, Phone and Storage Services Permissions are required for this App.");
        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        builder.show();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_CODE_GPS_PERMISSIONS);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        checkGpsEnable();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    //    check gps enable in device or not
    private void checkGpsEnable() {
        try {
            boolean isGPSEnabled = false;
            boolean isNetworkEnabled = false;
            final LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                buildAlertMessageNoGps();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
