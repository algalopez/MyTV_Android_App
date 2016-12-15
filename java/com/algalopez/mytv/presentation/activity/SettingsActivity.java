package com.algalopez.mytv.presentation.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.algalopez.mytv.R;
import com.algalopez.mytv.data.history.HistoryRepository;
import com.algalopez.mytv.data.history.database.HistoryDbDAO;
import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.interactor.history.RemoveAllHistoryInteractor;
import com.algalopez.mytv.domain.repository.IHistoryRepository;
import com.algalopez.mytv.domain.scheduler.IExecutor;
import com.algalopez.mytv.domain.scheduler.ThreadExecutor;

public class SettingsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, Button.OnClickListener{

    private static final String LOGTAG = "asd";


    // ---------------------------------------------------------------------------------------------
    // ACTIVITY LIFECYCLE
    // ---------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(myToolbar);

        if (myToolbar == null){
            return;
        }

        // Change title typeface
        TextView tv = (TextView) myToolbar.findViewById(R.id.toolbar_settings_title);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Lobster.otf");
        tv.setTypeface(tf);

        // Get order radiogroup and set listener
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.settings_radiogroup);
        if (radioGroup != null){
            radioGroup.setOnCheckedChangeListener(this);
            SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

            final String[] order_array = getResources().getStringArray(R.array.preference_order_values);
            String order_value = sharedPreferences.getString(getResources().getString(R.string.preference_order_name), order_array[1]);

            if (order_value.equals(order_array[0])){
                radioGroup.check(R.id.settings_radiogroup_name);
            } else if (order_value.equals(order_array[1])) {
                radioGroup.check(R.id.settings_radiogroup_rating);
            }
        }

        // Set Delete listener
        Button deleteHistoryButton = (Button) findViewById(R.id.settings_history_delete);
        if (deleteHistoryButton != null){
            deleteHistoryButton.setOnClickListener(this);
        }

    }


    // ---------------------------------------------------------------------------------------------
    // LISTENERS
    // ---------------------------------------------------------------------------------------------


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        // Check which radiogrup has changed
        if (radioGroup.getId() == R.id.settings_radiogroup){
            onCheckedChangeOrder(radioGroup, i);
        }
    }


    @Override
    public void onClick(View view) {

        // Check which button has been clicked
        if (view.getId() == R.id.settings_history_delete){

            // Confirmation dialog
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.dialog_remove_History))
                    //.setMessage("qweqwe2")
                    .setNegativeButton(getResources().getString(R.string.cancel), null)
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            onClickHistoryDelete();
                        }
                    })
                    .create();
            dialog.show();

        }
    }


    // ---------------------------------------------------------------------------------------------
    // ORDER
    // ---------------------------------------------------------------------------------------------


    // Radiogroup Order has changed
    public void onCheckedChangeOrder(RadioGroup radioGroup, int id){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor sEditor = sharedPreferences.edit();
        if (id == radioGroup.findViewById(R.id.settings_radiogroup_name).getId()){
            sEditor.putString(getResources().getString(R.string.preference_order_name), getResources().getStringArray(R.array.preference_order_values)[0]);
        } else if (id == radioGroup.findViewById(R.id.settings_radiogroup_rating).getId())
        {
            sEditor.putString(getResources().getString(R.string.preference_order_name), getResources().getStringArray(R.array.preference_order_values)[1]);
        }
        sEditor.apply();
    }


    // ---------------------------------------------------------------------------------------------
    // DELETE HISTORY
    // ---------------------------------------------------------------------------------------------


    // Delete button has been clicked
    public void onClickHistoryDelete(){

        IExecutor<CallbackInteractor> executor = ThreadExecutor.getInstance();
        IHistoryRepository historyRepository = new HistoryRepository(new HistoryDbDAO(this));

        CallbackInteractor interactor = new RemoveAllHistoryInteractor(historyRepository);

        executor.execute(interactor);

        Toast.makeText(this, "History Deleted", Toast.LENGTH_SHORT).show();
    }






}
