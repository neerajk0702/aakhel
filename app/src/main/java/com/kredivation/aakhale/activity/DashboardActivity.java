package com.kredivation.aakhale.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.fragments.AddScoreFragment;
import com.kredivation.aakhale.fragments.AddSportsFragment;
import com.kredivation.aakhale.fragments.AddUmpiresFragment;
import com.kredivation.aakhale.fragments.ChatFragment;
import com.kredivation.aakhale.fragments.ChatListFragment;
import com.kredivation.aakhale.fragments.CreateMatchFragment;
import com.kredivation.aakhale.fragments.CreatePostFragment;
import com.kredivation.aakhale.fragments.CreateScoreCardFragment;
import com.kredivation.aakhale.fragments.CreateTeamFragment;
import com.kredivation.aakhale.fragments.HomeFragment;
import com.kredivation.aakhale.fragments.MyProfileFragment;
import com.kredivation.aakhale.fragments.NotificationListFragment;
import com.kredivation.aakhale.fragments.PostItemFragment;
import com.kredivation.aakhale.fragments.ScheduleFragment;
import com.kredivation.aakhale.fragments.ScoreCardFragment;
import com.kredivation.aakhale.runtimepermission.PermissionResultCallback;
import com.kredivation.aakhale.runtimepermission.PermissionUtils;
import com.kredivation.aakhale.utility.Contants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {

    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    private int REQUEST_CODE_GPS_PERMISSIONS = 2;

    TextView createTeame, aaAcademics, addTournament, addScore, createMatch, createGround, cretescorecard,
            scoreCard, createPost, schedule, Notification, postItem, sghareApp, rateApp, privacy,
            termUSe, name;
    MaterialCardView profile, setting, logout;
    LinearLayout createTeamlayout, addacadmiclayout, addTournamentlayout, addmatchlayout, addGroundlayout, addscorecardlayout, scoreCardlayout, createPostlayout, Notificationlayout, postItemlayout;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
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
        init();
    }

    public void init() {
        name = findViewById(R.id.name);
        imageView = findViewById(R.id.imageView);
        createTeame = findViewById(R.id.createTeame);
        aaAcademics = findViewById(R.id.aaAcademics);
        addTournament = findViewById(R.id.addTournament);
        addScore = findViewById(R.id.addScore);
        createMatch = findViewById(R.id.createMatch);
        createGround = findViewById(R.id.createGround);
        cretescorecard = findViewById(R.id.cretescorecard);
        scoreCard = findViewById(R.id.scoreCard);
        createPost = findViewById(R.id.createPost);
        schedule = findViewById(R.id.schedule);
        Notification = findViewById(R.id.Notification);
        postItem = findViewById(R.id.postItem);
        sghareApp = findViewById(R.id.sghareApp);
        rateApp = findViewById(R.id.rateApp);
        privacy = findViewById(R.id.privacy);
        termUSe = findViewById(R.id.termUSe);
        profile = findViewById(R.id.profile);
        setting = findViewById(R.id.setting);
        logout = findViewById(R.id.logout);

        createTeamlayout = findViewById(R.id.createTeamlayout);
        addacadmiclayout = findViewById(R.id.addacadmiclayout);
        addTournamentlayout = findViewById(R.id.addTournamentlayout);
        addmatchlayout = findViewById(R.id.addmatchlayout);
        addGroundlayout = findViewById(R.id.addGroundlayout);
        addscorecardlayout = findViewById(R.id.addscorecardlayout);
        scoreCardlayout = findViewById(R.id.scoreCardlayout);
        createPostlayout = findViewById(R.id.createPostlayout);
        Notificationlayout = findViewById(R.id.Notificationlayout);
        postItemlayout = findViewById(R.id.postItemlayout);


        createTeamlayout.setOnClickListener(this);
        addacadmiclayout.setOnClickListener(this);
        addTournamentlayout.setOnClickListener(this);
        addScore.setOnClickListener(this);
        addmatchlayout.setOnClickListener(this);
        addGroundlayout.setOnClickListener(this);
        addscorecardlayout.setOnClickListener(this);
        scoreCardlayout.setOnClickListener(this);
        createPostlayout.setOnClickListener(this);
        schedule.setOnClickListener(this);
        Notificationlayout.setOnClickListener(this);
        postItemlayout.setOnClickListener(this);
        sghareApp.setOnClickListener(this);
        rateApp.setOnClickListener(this);
        privacy.setOnClickListener(this);
        termUSe.setOnClickListener(this);
        profile.setOnClickListener(this);
        setting.setOnClickListener(this);
        logout.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserValue();
    }

    private void setUserValue() {
        SharedPreferences UserInfo = getSharedPreferences("UserInfoSharedPref", MODE_PRIVATE);
        Contants.auth_token = UserInfo.getString("auth_token", "");
        int role = UserInfo.getInt("role", 0);
        String email = UserInfo.getString("email", "");
        String full_name = UserInfo.getString("full_name", "");
        String mobile = UserInfo.getString("mobile", "");
        String profile_picture = UserInfo.getString("profile_picture", "");
        String users_sports = UserInfo.getString("users_sports", "");
        String unique_id = UserInfo.getString("unique_id", "");
        Picasso.with(DashboardActivity.this).load(Contants.BASE_URL + profile_picture).resize(60, 60).placeholder(R.drawable.ic_uuuser).into(imageView);
        name.setText(full_name + "");
        if (role == 1) {
            createTeamlayout.setVisibility(View.VISIBLE);
            addacadmiclayout.setVisibility(View.GONE);
            addGroundlayout.setVisibility(View.GONE);
            addTournamentlayout.setVisibility(View.VISIBLE);
            addmatchlayout.setVisibility(View.VISIBLE);
            addscorecardlayout.setVisibility(View.VISIBLE);
            scoreCardlayout.setVisibility(View.VISIBLE);
            createPostlayout.setVisibility(View.VISIBLE);
            Notificationlayout.setVisibility(View.VISIBLE);
            postItemlayout.setVisibility(View.VISIBLE);
        } else if (role == 2 || role == 3) {
            createTeamlayout.setVisibility(View.GONE);
            addacadmiclayout.setVisibility(View.GONE);
            addGroundlayout.setVisibility(View.GONE);
            addTournamentlayout.setVisibility(View.GONE);
            addmatchlayout.setVisibility(View.GONE);
            addscorecardlayout.setVisibility(View.VISIBLE);
            scoreCardlayout.setVisibility(View.VISIBLE);
            createPostlayout.setVisibility(View.VISIBLE);
            Notificationlayout.setVisibility(View.VISIBLE);
            postItemlayout.setVisibility(View.VISIBLE);
        } else if (role == 4) {
            createTeamlayout.setVisibility(View.VISIBLE);
            addacadmiclayout.setVisibility(View.VISIBLE);
            addGroundlayout.setVisibility(View.GONE);
            addTournamentlayout.setVisibility(View.VISIBLE);
            addmatchlayout.setVisibility(View.VISIBLE);
            addscorecardlayout.setVisibility(View.VISIBLE);
            scoreCardlayout.setVisibility(View.VISIBLE);
            createPostlayout.setVisibility(View.VISIBLE);
            Notificationlayout.setVisibility(View.VISIBLE);
            postItemlayout.setVisibility(View.VISIBLE);
        } else if (role == 5) {
            createTeamlayout.setVisibility(View.GONE);
            addacadmiclayout.setVisibility(View.GONE);
            addGroundlayout.setVisibility(View.VISIBLE);
            addTournamentlayout.setVisibility(View.GONE);
            addmatchlayout.setVisibility(View.GONE);
            addscorecardlayout.setVisibility(View.GONE);
            scoreCardlayout.setVisibility(View.GONE);
            createPostlayout.setVisibility(View.VISIBLE);
            Notificationlayout.setVisibility(View.VISIBLE);
            postItemlayout.setVisibility(View.VISIBLE);
        } else if (role == 6 || role == 7 || role == 8 || role == 9 || role == 10 || role == 11) {
            createTeamlayout.setVisibility(View.VISIBLE);
            addacadmiclayout.setVisibility(View.GONE);
            addGroundlayout.setVisibility(View.GONE);
            addTournamentlayout.setVisibility(View.VISIBLE);
            addmatchlayout.setVisibility(View.VISIBLE);
            addscorecardlayout.setVisibility(View.VISIBLE);
            scoreCardlayout.setVisibility(View.VISIBLE);
            createPostlayout.setVisibility(View.VISIBLE);
            Notificationlayout.setVisibility(View.VISIBLE);
            postItemlayout.setVisibility(View.VISIBLE);
        }
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

        } else if (id == R.id.nav_slideshow) {
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

        } else if (id == R.id.profile) {
            MyProfileFragment profileFragment = new MyProfileFragment();
            updateFragment(profileFragment, null);

        }

        if (id == R.id.nav_share) {

            ChatFragment chatFragment = new ChatFragment();
            updateFragment(chatFragment, null);
        } else if (id == R.id.nav_send) {
            NotificationListFragment notificationListFragment = new NotificationListFragment();
            updateFragment(notificationListFragment, null);
        } else if (id == R.id.postItem) {
            PostItemFragment postItemFragment = new PostItemFragment();
            updateFragment(postItemFragment, null);
        } else if (id == R.id.chatList) {
            ChatListFragment chatListFragment = new ChatListFragment();
            updateFragment(chatListFragment, null);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.createTeamlayout) {
            CreateTeamFragment createTeamFragment = new CreateTeamFragment();
            updateFragment(createTeamFragment, null);
        } else if (v.getId() == R.id.addacadmiclayout) {
            Intent accintent = new Intent(DashboardActivity.this, AddAcademicsActivity.class);
            startActivity(accintent);
        } else if (v.getId() == R.id.addTournamentlayout) {
            Intent intentto = new Intent(DashboardActivity.this, AddTournament.class);
            startActivity(intentto);
        } else if (v.getId() == R.id.addScore) {
            AddScoreFragment addScoreFragment = new AddScoreFragment();
            updateFragment(addScoreFragment, null);

        } else if (v.getId() == R.id.addmatchlayout) {
            CreateMatchFragment createTeamFragment = new CreateMatchFragment();
            updateFragment(createTeamFragment, null);

        } else if (v.getId() == R.id.addGroundlayout) {
            Intent intent1 = new Intent(DashboardActivity.this, CreateGroundActivity.class);
            startActivity(intent1);
        } else if (v.getId() == R.id.addscorecardlayout) {
            CreateScoreCardFragment createScoreCardFragment = new CreateScoreCardFragment();
            updateFragment(createScoreCardFragment, null);

        } else if (v.getId() == R.id.scoreCardlayout) {
            ScoreCardFragment scoreCardFragment = new ScoreCardFragment();
            updateFragment(scoreCardFragment, null);

        } else if (v.getId() == R.id.createPostlayout) {
            CreatePostFragment createPostFragment = new CreatePostFragment();
            updateFragment(createPostFragment, null);

        } else if (v.getId() == R.id.adumpire) {
            AddUmpiresFragment addUmpiresFragment = new AddUmpiresFragment();
            updateFragment(addUmpiresFragment, null);

        } else if (v.getId() == R.id.Schedule) {
            ScheduleFragment scheduleFragment = new ScheduleFragment();
            updateFragment(scheduleFragment, null);

        } else if (v.getId() == R.id.profile) {
            MyProfileFragment profileFragment = new MyProfileFragment();
            updateFragment(profileFragment, null);

        } else if (v.getId() == R.id.Notificationlayout) {
            NotificationListFragment notificationListFragment = new NotificationListFragment();
            updateFragment(notificationListFragment, null);
        } else if (v.getId() == R.id.postItemlayout) {
            PostItemFragment postItemFragment = new PostItemFragment();
            updateFragment(postItemFragment, null);
        } else if (v.getId() == R.id.chat) {
            ChatListFragment chatListFragment = new ChatListFragment();
            updateFragment(chatListFragment, null);
        } else if (v.getId() == R.id.profile) {
            MyProfileFragment chatListFragment = new MyProfileFragment();
            updateFragment(chatListFragment, null);
        } else if (v.getId() == R.id.setting) {

        } else if (v.getId() == R.id.logout) {
            SharedPreferences UserInfo = getSharedPreferences("UserInfoSharedPref", MODE_PRIVATE);
            UserInfo.edit().clear().commit();
            Contants.auth_token = "";
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }


    public void updateFragment(Fragment pageFragment, Bundle bundle) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        pageFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.mainView, pageFragment)
                .addToBackStack(null)
                .commit();
    }

    private void runTimePermission() {
        permissionUtils = new PermissionUtils(DashboardActivity.this);

        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.CAMERA);
        permissions.add(android.Manifest.permission.WAKE_LOCK);
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

    //for hid keyboard when tab outside edittext box
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
