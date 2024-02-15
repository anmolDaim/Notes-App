package com.example.nb;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nb.Database.myDBHelper;


public class TextFragment extends Fragment {
AppCompatButton saveButton;
EditText editTextText;
    myDBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_text, container, false);
        editTextText = view.findViewById(R.id.editTextText);
        saveButton = view.findViewById(R.id.saveButton);
        dbHelper = new myDBHelper(requireContext());
        // Retrieve data from bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String noteText = bundle.getString("noteText", "");
            editTextText.setText(noteText);
        }

        saveButton=view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNoteToDatabase();
            }
        });
        return view;
    }

    private void saveNoteToDatabase() {
        String noteText = editTextText.getText().toString().trim();
        if (!noteText.isEmpty()) {
            dbHelper.insertNote(noteText);
            clearEditText();
            Toast.makeText(getContext(), "successfully added", Toast.LENGTH_SHORT).show();
            moveActivity();
        }
    }

    private void moveActivity() {
        MainFragment mainFragment=new MainFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,mainFragment)
                .commit();
    }

    private void clearEditText() {
        editTextText.getText().clear();
    }
}