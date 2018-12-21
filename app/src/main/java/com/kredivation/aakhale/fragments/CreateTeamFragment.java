package com.kredivation.aakhale.fragments;


import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.GridViewAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.dialog.SearchDialog;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTeamFragment extends Fragment implements View.OnClickListener {
    View view;
    ASTEditText teameName, aboutTeam, zipCode;
    Spinner stateSpinner, citySpinner;
    TextView addMoreView, addMoreViewImage,addplayelbl;
    ListView AddPlayerLIst;
    ASTButton continuebtn;
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    public CreateTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_team, container, false);
        init();
        getActivity().setTitle("Create Team");
        return view;
    }

    public void init() {
        teameName = view.findViewById(R.id.teameName);
        aboutTeam = view.findViewById(R.id.aboutTeam);
        zipCode = view.findViewById(R.id.zipCode);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        citySpinner = view.findViewById(R.id.citySpinner);
        addMoreView = view.findViewById(R.id.addMoreView);
        addMoreViewImage = view.findViewById(R.id.addMoreViewImage);
        AddPlayerLIst = view.findViewById(R.id.AddPlayerLIst);
        continuebtn = view.findViewById(R.id.continuebtn);
        addplayelbl= view.findViewById(R.id.addplayelbl);
        addMoreView.setOnClickListener(this);

        gridView = (GridView) view.findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getContext(), R.layout.image_item_layout, getData());
        gridView.setAdapter(gridAdapter);


        String s= "Add Player(Enter Unique Id)";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1f), 0,10, 0); // set size
     //   ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
        addplayelbl.setText(ss1);
    }


    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }


    SearchDialog fnNewDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMoreView:
                fnNewDialog = new SearchDialog(getContext()) {
                    @Override
                    public void actionPerform() {

                    }
                };

                fnNewDialog.show();

                break;

            case R.id.addMoreViewImage:
                break;
        }
    }
}
