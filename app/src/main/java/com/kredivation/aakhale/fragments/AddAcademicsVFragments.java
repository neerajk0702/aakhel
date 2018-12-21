package com.kredivation.aakhale.fragments;


import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.activity.LoginActivity;
import com.kredivation.aakhale.activity.ResetPasswordActivity;
import com.kredivation.aakhale.adapter.GridViewAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAcademicsVFragments extends Fragment implements View.OnClickListener {

    View view;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    LinearLayout container_moreadd;
    ImageView sortiMG, acadmiciMG;
    ASTEditText acdName, teameName, zipcode, description, managerfullName, manageremail, mamangercontactno;
    ASTEditText coachfullName, coachemail, coachcontactno, OwnerfullName, Owneremail, Ownercontactno;
    Spinner stateSpinner, sitySpinner;
    TextView addMoreViewmember, addMoreViewImage;
    LinearLayout academiesMemberLayout;
    ASTEditText memberfullName, memberemail, membercontactno;
    ASTButton continuebtn;
    TextView nooftemMember;
    int count = 0;
    LinearLayout acadmicViewinfoLayout, acadmicinfoLayout, acadmicmemberinfoLayout;
    int numberclick = 1;
    int academiesMemberLayoutclick = 1;

    public AddAcademicsVFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_academics_vfragments, container, false);
        init();
        getActivity().setTitle("Add Academics");
        return view;
    }

    public void init() {

        gridView = view.findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getContext(), R.layout.image_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        container_moreadd = view.findViewById(R.id.container_moreadd);
        addMoreViewmember = view.findViewById(R.id.addMoreViewmember);

        sortiMG = view.findViewById(R.id.sortiMG);
        acadmiciMG = view.findViewById(R.id.acadmiciMG);
        acdName = view.findViewById(R.id.acdName);
        teameName = view.findViewById(R.id.teameName);
        zipcode = view.findViewById(R.id.zipcode);
        description = view.findViewById(R.id.description);
        managerfullName = view.findViewById(R.id.managerfullName);
        manageremail = view.findViewById(R.id.manageremail);
        mamangercontactno = view.findViewById(R.id.mamangercontactno);
        coachfullName = view.findViewById(R.id.coachfullName);
        coachemail = view.findViewById(R.id.coachemail);
        coachcontactno = view.findViewById(R.id.coachcontactno);
        OwnerfullName = view.findViewById(R.id.OwnerfullName);
        Owneremail = view.findViewById(R.id.Owneremail);
        Ownercontactno = view.findViewById(R.id.Ownercontactno);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        sitySpinner = view.findViewById(R.id.sitySpinner);
        addMoreViewmember = view.findViewById(R.id.addMoreViewmember);
        addMoreViewImage = view.findViewById(R.id.addMoreViewImage);
        academiesMemberLayout = view.findViewById(R.id.academiesMemberLayout);
        continuebtn = view.findViewById(R.id.continuebtn);
        addMoreViewmember.setOnClickListener(this);
        acadmicViewinfoLayout = view.findViewById(R.id.acadmicViewinfoLayout);
        acadmicinfoLayout = view.findViewById(R.id.acadmicinfoLayout);
        acadmicViewinfoLayout.setOnClickListener(this);
        academiesMemberLayout.setOnClickListener(this);
        acadmicmemberinfoLayout = view.findViewById(R.id.acadmicmemberinfoLayout);
        addMoreMember();

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


    public void addMoreMember() {
        count++;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflatedLayout = inflater.inflate(R.layout.add_more_row, null);
        memberfullName = inflatedLayout.findViewById(R.id.memberfullName);
        memberemail = inflatedLayout.findViewById(R.id.memberemail);
        membercontactno = inflatedLayout.findViewById(R.id.membercontactno);
        nooftemMember = inflatedLayout.findViewById(R.id.nooftemMember);
        nooftemMember.setText("Team Member\t" + count);
        container_moreadd.addView(inflatedLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMoreViewmember:
                addMoreMember();
                break;
            case R.id.acadmicViewinfoLayout:
                if ((numberclick % 2) == 0) {
                    sortiMG.setImageResource(R.drawable.ic_up_arrow);
                    acadmicinfoLayout.setVisibility(View.VISIBLE);
                } else {
                    sortiMG.setImageResource(R.drawable.ic_angle_arrow_down);
                    acadmicinfoLayout.setVisibility(View.GONE);
                }
                numberclick++;
                break;
            case R.id.academiesMemberLayout:
                if ((academiesMemberLayoutclick % 2) == 0) {
                    acadmiciMG.setImageResource(R.drawable.ic_up_arrow);
                    acadmicmemberinfoLayout.setVisibility(View.VISIBLE);
                } else {
                    acadmiciMG.setImageResource(R.drawable.ic_angle_arrow_down);
                    acadmicmemberinfoLayout.setVisibility(View.GONE);
                }
                academiesMemberLayoutclick++;
                break;
        }
    }
}
