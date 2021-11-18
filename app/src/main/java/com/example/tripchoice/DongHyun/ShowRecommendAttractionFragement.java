package com.example.tripchoice.DongHyun;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripchoice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShowRecommendAttractionFragement extends Fragment {
    private ArrayList<attraction> list = new ArrayList<attraction>();
    private SharedViewModel sharedViewModel;
    private RecyclerView recyclerView;
    private ShowRecommendAttractionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_show_recommend_attraction_fragement, container, false);
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.attractionlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ShowRecommendAttractionAdapter();
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),  DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(adapter);


        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        list = sharedViewModel.getList();

        for(attraction a : list){
            adapter.addItem(a);
        }



        return viewGroup;
    }
}