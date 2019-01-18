package com.kredivation.aakhale.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.adapter.AddChatAdapter;
import com.kredivation.aakhale.adapter.AddTeamsAdapter;
import com.kredivation.aakhale.components.ASTButton;
import com.kredivation.aakhale.components.ASTEditText;
import com.kredivation.aakhale.model.Team;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    ASTEditText umpreSearchTxt;
    RecyclerView teamList;
    ASTButton saveBtn;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        init();
        getActivity().setTitle("Chat");
        return view;
    }

    public void init() {
        teamList = view.findViewById(R.id.teamList);
        teamList.setLayoutManager(new LinearLayoutManager(getContext()));
        umpreSearchTxt = view.findViewById(R.id.umpreSearchTxt);
        saveBtn = view.findViewById(R.id.saveBtn);
        addChatListAdapter();
    }


    //set data into recycle view
    private void addChatListAdapter() {
        ArrayList<Team> chatList = new ArrayList<>();
        Team data = new Team();
        for (int i = 1; i <= 5; i++) {
            data.setName("Ram Singh");
            chatList.add(data);
        }

        AddChatAdapter addChatAdapter = new AddChatAdapter(getContext(), chatList);
        teamList.setAdapter(addChatAdapter);
    }
}