package com.kredivation.aakhale.activity;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.utility.FontManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreCardActivity extends AppCompatActivity implements View.OnClickListener {
    View view;


    public ScoreCardActivity() {
        // Required empty public constructor
    }
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_score_card);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }
    public void init() {
        Typeface materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(this, "fonts/materialdesignicons-webfont.otf");
        TextView back = toolbar.findViewById(R.id.back);
        back.setTypeface(materialdesignicons_font);
        back.setText(Html.fromHtml("&#xf30d;"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //  teame1 = view.findViewById(R.id.teame1);
        // teame2 = view.findViewById(R.id.teame2);
    }
    @Override
    public void onClick(View view) {

    }
}
