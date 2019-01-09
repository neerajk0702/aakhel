package com.kredivation.aakhale.fragments;


import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.RecycleViewAdapter;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTournament extends Fragment implements View.OnClickListener {

    View view;
    ASTEditText tournamentName, venueAddress, zipCode, noofOver, enteryFee, aboutTournament;

    Spinner stateSpinner, citySpinner, formateSpinner, startdateSpinner, enddateSpinner, statusSpinner;
    LinearLayout container_add_facilities, container_add_rule_regulation, container_add_rule_price;
    TextView addsisplayPicture, addMoreprice, addMoreFacilites, addMorerule_regulation;
    RecyclerView addImageView;
    private RecycleViewAdapter imageAdapater;

    ASTEditText addviewedtxt;

    public AddTournament() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_tournament, container, false);

        getActivity().setTitle("Add Tournament");
        init();
        return view;
    }

    public void init() {
        tournamentName = view.findViewById(R.id.tournamentName);
        venueAddress = view.findViewById(R.id.venueAddress);
        zipCode = view.findViewById(R.id.zipCode);
        noofOver = view.findViewById(R.id.noofOver);
        enteryFee = view.findViewById(R.id.enteryFee);
        aboutTournament = view.findViewById(R.id.aboutTournament);
        addMoreFacilites = view.findViewById(R.id.addMoreFacilites);
        addMorerule_regulation = view.findViewById(R.id.addMorerule_regulation);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        citySpinner = view.findViewById(R.id.citySpinner);
        formateSpinner = view.findViewById(R.id.formateSpinner);
        startdateSpinner = view.findViewById(R.id.startdateSpinner);
        enddateSpinner = view.findViewById(R.id.enddateSpinner);
        statusSpinner = view.findViewById(R.id.statusSpinner);
        container_add_facilities = view.findViewById(R.id.container_add_facilities);
        container_add_rule_regulation = view.findViewById(R.id.container_add_rule_regulation);
        container_add_rule_price = view.findViewById(R.id.container_add_rule_price);
        addsisplayPicture = view.findViewById(R.id.addsisplayPicture);
        addMoreprice = view.findViewById(R.id.addMoreprice);
        addMoreFacilites = view.findViewById(R.id.addMoreFacilites);
        addImageView = view.findViewById(R.id.addImageView);
        imageAdapater = new RecycleViewAdapter(getContext(), R.layout.image_item_layout, getData());
        addImageView.setAdapter(imageAdapater);
        setLinearLayoutManager(addImageView);
        addImageView.setNestedScrollingEnabled(false);
        addImageView.setHasFixedSize(false);
        addsisplayPicture.setOnClickListener(this);
        addMoreprice.setOnClickListener(this);
        addMoreFacilites.setOnClickListener(this);
        addMorerule_regulation.setOnClickListener(this);
        addMoreFacilites("Facilites Name");
        addMorerule_regulation("Rule Regulation Name");
        addMoreprice("Price");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMoreFacilites:
                addMoreFacilites("Facilites Name");
                break;
            case R.id.addMorerule_regulation:
                addMorerule_regulation("Rule Regulation Name");
                break;
            case R.id.addMoreprice:
                addMoreprice("Price");
                break;


        }
    }


    public void addMoreFacilites(String lblName) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.add_row, null);
        addviewedtxt = inflatedLayout.findViewById(R.id.addviewedtxt);
        TextView labelName = inflatedLayout.findViewById(R.id.labelName);
        labelName.setText(lblName);
        container_add_facilities.addView(inflatedLayout);
    }


    public void addMorerule_regulation(String lblName) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.add_row, null);
        addviewedtxt = inflatedLayout.findViewById(R.id.addviewedtxt);
        TextView labelName = inflatedLayout.findViewById(R.id.labelName);
        labelName.setText(lblName);
        container_add_rule_regulation.addView(inflatedLayout);
    }


    public void addMoreprice(String lblName) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.add_row, null);
        addviewedtxt = inflatedLayout.findViewById(R.id.addviewedtxt);
        TextView labelName = inflatedLayout.findViewById(R.id.labelName);
        labelName.setText(lblName);
        container_add_rule_price.addView(inflatedLayout);
    }

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
           // imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }


    private void setLinearLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}