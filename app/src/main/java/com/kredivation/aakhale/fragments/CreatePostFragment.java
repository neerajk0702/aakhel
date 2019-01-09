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
import android.widget.GridView;
import android.widget.TextView;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.RecycleViewAdapter;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.model.ImageItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePostFragment extends Fragment {
    View view;
    ASTEditText postName, descriptionTxt;
    TextView addMoreViewImage;
    RecyclerView addImageView;
    private RecycleViewAdapter imageAdapater;

    public CreatePostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_post, container, false);
        getActivity().setTitle("Create Post");
        init();
        return view;
    }


    public void init() {
        addImageView = view.findViewById(R.id.addImageView);
        imageAdapater = new RecycleViewAdapter(getContext(), R.layout.image_item_layout, getData());
        addImageView.setAdapter(imageAdapater);
        setLinearLayoutManager(addImageView);
        addImageView.setNestedScrollingEnabled(false);
        addImageView.setHasFixedSize(false);
    }

    private void setLinearLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            ///imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }

}
