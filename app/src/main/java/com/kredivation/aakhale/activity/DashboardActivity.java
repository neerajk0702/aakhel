package com.kredivation.aakhale.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


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
}
