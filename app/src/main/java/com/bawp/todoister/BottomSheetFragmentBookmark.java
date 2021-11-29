package com.bawp.todoister;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bawp.todoister.model.Bookmark;
import com.bawp.todoister.model.TaskViewModel;
import com.bawp.todoister.utils.LinkChecker;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetFragmentBookmark#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetFragmentBookmark extends BottomSheetDialogFragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText URL;
    private EditText Name;
    private Button savebutton;
    private Button testurl;
    private ImageButton goodurl;
    private ImageButton badurl;
    public BottomSheetFragmentBookmark() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BottomSheetFragmentBookmark.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomSheetFragmentBookmark newInstance(String param1, String param2) {
        BottomSheetFragmentBookmark fragment = new BottomSheetFragmentBookmark();
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
        View view=inflater.inflate(R.layout.fragment_bottom_sheet_bookmark, container, false);
        Name=view.findViewById(R.id.bookmark_name);
        URL=view.findViewById(R.id.url);
        savebutton=view.findViewById(R.id.savebutton);
        testurl=view.findViewById(R.id.test_url);
        goodurl=view.findViewById(R.id.good_url);
        badurl=view.findViewById(R.id.bad_url);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        testurl.setOnClickListener(view12 -> {
            String url=URL.getText().toString();
            Log.d("Url", url);
            try {
                if (LinkChecker.executeLink(new URL(url))==200){
                    goodurl.setVisibility(View.VISIBLE);
                    badurl.setVisibility(View.GONE);
                }
                else{
                    badurl.setVisibility(View.VISIBLE);
                    goodurl.setVisibility(View.GONE);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        savebutton.setOnClickListener(view1 -> {
            String name=Name.getText().toString();
            String url=URL.getText().toString();
            Bookmark bookmark=new Bookmark(name, url);
            TaskViewModel.insert(bookmark);
            dismiss();
        });
    }
}