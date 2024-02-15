package com.example.nb;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nb.Adapter.notesAdapter;
import com.example.nb.DataModel.itemDataModel;
import com.example.nb.Database.myDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainFragment extends Fragment {

RecyclerView notesRecyclerView;
notesAdapter adapter;
FloatingActionButton floatingBtn;
    myDBHelper dbhelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main, container, false);
        notesRecyclerView=view.findViewById(R.id.notesRecyclerView);
        floatingBtn=view.findViewById(R.id.floatingBtn);

        dbhelper=new myDBHelper(getContext());

        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTextFragment();
            }
        });
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        updateRecyclerView();

        return view;
    }

    private void updateRecyclerView() {
        ArrayList<itemDataModel> array=dbhelper.fetchNotes();
        adapter=new notesAdapter(getContext(),array,new notesAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
                itemDataModel clickedItem = array.get(position);
                TextFragment textFragment = new TextFragment();
                Bundle bundle = new Bundle();
                bundle.putString("noteText", clickedItem.getText());
                textFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, textFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        notesRecyclerView.setAdapter(adapter);
    }

    private void openTextFragment() {
        TextFragment textFragment=new TextFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, textFragment)
                .commit();
    }

}