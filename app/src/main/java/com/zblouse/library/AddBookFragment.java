package com.zblouse.library;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class AddBookFragment extends Fragment {
    public AddBookFragment() {
        super(R.layout.fragment_new_book);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText titleText = getActivity().findViewById(R.id.title_edit_text);
        EditText authorText = getActivity().findViewById(R.id.author_edit_text);
        Button addBookButton = getActivity().findViewById(R.id.add_book_button);


    }
}
