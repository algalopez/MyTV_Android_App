package com.algalopez.mytv.presentation.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.algalopez.mytv.R;
import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.domain.model.ShowEntity;
import com.algalopez.mytv.presentation.fragment.SearchFragment;

public class SearchActivity extends AppCompatActivity implements IActivity {

    private final static String LOGTAG = "SearchActivity";

    private boolean mTwoPanes = false;


    public static final String PARAM_SEARCHTERM = "PARAM_SEARCHTERM";
    public static final String PARAM_SHOWID = "PARAM_SHOWID";

    private Dialog mOverflowDialog;


    // ---------------------------------------------------------------------------------------------
    // ACTIVITY LIFECYCLE
    // ---------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
        tv.setText(getResources().getString(R.string.activity_search));

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

    }


    @Override
    public void setNetworkToolbar() {

    }


    @Override
    public void onItemSelected(AEntity entity) {
        switch (entity.getAction()){
            case SHOW:
                if (mTwoPanes){
                    Log.d(LOGTAG, "Two panes");
                    //ShowFragment.newInstance(mShowID);
                    // Make transaction in secondary container
                } else {
                    String showID = ((ShowEntity) entity).getImdbID();
                    Bundle args = new Bundle();
                    args.putString(ShowActivity.PARAM_SHOWID, showID);
                    Intent intent = new Intent(this, ShowActivity.class);
                    intent.putExtras(args);
                    startActivity(intent);
                }
        }
    }


}
