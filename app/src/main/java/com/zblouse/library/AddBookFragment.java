package com.zblouse.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Fragment shown from the UserHomeActivity that allows the user to add a book to their list
 */
public class AddBookFragment extends Fragment {
    public AddBookFragment() {
        super(R.layout.fragment_new_book);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_new_book,container,false);
        EditText titleText = layout.findViewById(R.id.title_edit_text);
        EditText authorText = layout.findViewById(R.id.author_edit_text);
        Button addBookButton = layout.findViewById(R.id.add_book_button);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book(titleText.getText().toString(),authorText.getText().toString(),
                        user.getUid(),false);
                BookDatabaseHandler.addBookToDatabase(book);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment()).commit();
            }
        });
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
}
