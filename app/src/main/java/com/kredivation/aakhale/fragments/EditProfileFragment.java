package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.components.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    CircleImageView profileImg;
    ImageView dateIcon;
    Spinner experienceSpinner, genderSpinner, roleSpinner;
    LinearLayout selectSports, selctSportsLayout;
    ASTEditText fullName, emailid, contactNo, dob;
    View view;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        init();
        return view;
    }


    public void init() {
        profileImg = view.findViewById(R.id.profileImg);
        dateIcon = view.findViewById(R.id.dateIcon);
        experienceSpinner = view.findViewById(R.id.experienceSpinner);
        genderSpinner = view.findViewById(R.id.genderSpinner);
        roleSpinner = view.findViewById(R.id.roleSpinner);
        selectSports = view.findViewById(R.id.selectSports);
        selctSportsLayout = view.findViewById(R.id.selctSportsLayout);
        fullName = view.findViewById(R.id.fullName);
        emailid = view.findViewById(R.id.emailid);
        contactNo = view.findViewById(R.id.contactNo);
        dob = view.findViewById(R.id.dob);
    }

}
