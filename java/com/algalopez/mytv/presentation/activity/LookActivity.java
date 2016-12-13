package com.algalopez.mytv.presentation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.algalopez.mytv.R;
import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.domain.model.SearchEntity;

public class LookActivity extends AppCompatActivity implements IActivity {

    private final static String LOGTAG = "LookActivity";

    private boolean mTwoPanes = false;


    // ---------------------------------------------------------------------------------------------
    // ACTIVITY LIFECYCLE
    // ---------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOGTAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_look);
        setSupportActionBar(myToolbar);

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
        Log.d(LOGTAG, "onItemSelected");
        switch (entity.getAction()){
            case SEARCH:
                if (mTwoPanes){
                    Log.d(LOGTAG, "Two panes");

                } else {
                    String searchTerm = ((SearchEntity) entity).getSearchTerm();
                    Bundle args = new Bundle();
                    args.putString(SearchActivity.PARAM_SEARCHTERM, searchTerm);
                    Intent intent = new Intent(this, SearchActivity.class);
                    intent.putExtras(args);
                    startActivity(intent);
                }
        }
    }


}
