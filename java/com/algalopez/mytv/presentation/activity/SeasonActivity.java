package com.algalopez.mytv.presentation.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.algalopez.mytv.R;
import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.domain.model.EpisodeEntity;
import com.algalopez.mytv.presentation.fragment.SeasonFragment;

public class SeasonActivity extends AppCompatActivity implements IActivity {

    private final static String LOGTAG = "SeasonActivity";


    private boolean mTwoPanes = false;

    public static final String PARAM_SHOWID = "PARAM_SHOWID";
    public static final String PARAM_SEASON = "PARAM_SEASON";
    public static final String PARAM_EPISODE = "PARAM_EPISODE";

    private Dialog mOverflowDialog;


    // ---------------------------------------------------------------------------------------------
    // ACTIVITY LIFECYCLE
    // ---------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        if (myToolbar == null){
            return;
        }

        TextView tv = (TextView) myToolbar.findViewById(R.id.toolbar_title);
        ImageButton settingsButton = (ImageButton) myToolbar.findViewById(R.id.toolbar_settings);
        ImageButton searchButton = (ImageButton) myToolbar.findViewById(R.id.toolbar_search);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Lobster.otf");
        tv.setTypeface(tf);
        tv.setText(getResources().getString(R.string.activity_season));


        searchButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                startLookActivity();
            }
        });


        settingsButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                onOverflowClick();
            }
        });

    }


    // ---------------------------------------------------------------------------------------------
    // DIALOGS
    // ---------------------------------------------------------------------------------------------


    private void onOverflowClick(){
        mOverflowDialog = new Dialog(this);
        mOverflowDialog.setContentView(R.layout.overflow_dialog);

        TextView overflowSettings = (TextView)mOverflowDialog.findViewById(R.id.overflow_dialog_settings);
        overflowSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSettingsActivity();
            }
        });

        TextView overflowAbout = (TextView)mOverflowDialog.findViewById(R.id.overflow_dialog_about);
        overflowAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAboutActivity();
            }
        });

        mOverflowDialog.show();
    }


    // ---------------------------------------------------------------------------------------------
    // METHODS AND ACTIVITY CHANGES
    // ---------------------------------------------------------------------------------------------


    public void startLookActivity(){
        Intent intent = new Intent(this, LookActivity.class);
        this.startActivity(intent);
    }


    public void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        this.startActivity(intent);
    }


    private void startAboutActivity(){
        Intent intent = new Intent(this, AboutActivity.class);
        this.startActivity(intent);
    }


    // ---------------------------------------------------------------------------------------------
    // ACTIVITY CALLBACK
    // ---------------------------------------------------------------------------------------------


    @Override
    public void setLocalToolbar() {

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (myToolbar == null) { return; }

        // Set favourite filled button. Click to remove show
        ImageButton favouriteButton = (ImageButton) myToolbar.findViewById(R.id.toolbar_favourite);
        favouriteButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_delete_white_36dp, null));
        favouriteButton.setVisibility(View.VISIBLE);
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeasonFragment mainFragment = (SeasonFragment) getSupportFragmentManager().findFragmentById(R.id.activity_season_seasonfragment);
                mainFragment.onUnFavouriteClick();
            }
        });

        // Set refresh button
        ImageButton refreshButton = (ImageButton) myToolbar.findViewById(R.id.toolbar_refresh);
        refreshButton.setVisibility(View.VISIBLE);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeasonFragment mainFragment = (SeasonFragment) getSupportFragmentManager().findFragmentById(R.id.activity_season_seasonfragment);
                mainFragment.onRefreshClick();
            }
        });
    }


    @Override
    public void setNetworkToolbar() {

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (myToolbar == null) { return; }

        // Set favourite empty button. Click to save show
        ImageButton favouriteButton = (ImageButton) myToolbar.findViewById(R.id.toolbar_favourite);
        favouriteButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_favorite_white_36dp, null));
        favouriteButton.setVisibility(View.VISIBLE);
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeasonFragment mainFragment = (SeasonFragment) getSupportFragmentManager().findFragmentById(R.id.activity_season_seasonfragment);
                mainFragment.onFavouriteClick();
            }
        });

        // Hide refresh button
        ImageButton refreshButton = (ImageButton) myToolbar.findViewById(R.id.toolbar_refresh);
        refreshButton.setVisibility(View.GONE);
    }


    @Override
    public void onItemSelected(AEntity entity) {
        //Log.d(LOGTAG, "onItemSelected");
        switch (entity.getAction()){
            case EPISODE:
                if (mTwoPanes){
                    Log.d(LOGTAG, "Two panes");

                } else {
                    String mShowID = ((EpisodeEntity)entity).getSeriesID();
                    String mSeason = ((EpisodeEntity)entity).getSeason();
                    String mEpisode = ((EpisodeEntity)entity).getEpisode();
                    Bundle args = new Bundle();
                    args.putString(EpisodeActivity.PARAM_SHOWID, mShowID);
                    args.putString(EpisodeActivity.PARAM_SEASON, mSeason);
                    args.putString(EpisodeActivity.PARAM_EPISODE, mEpisode);
                    Intent intent = new Intent(this, EpisodeActivity.class);
                    intent.putExtras(args);
                    startActivity(intent);
                }
        }
    }
}
