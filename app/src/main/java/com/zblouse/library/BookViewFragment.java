package com.zblouse.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class BookViewFragment extends Fragment {

    private Book book;

    public BookViewFragment(Book book){
        super(R.layout.fragment_view_book);
        this.book=book;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_view_book,container,false);
        TextView titleText = layout.findViewById(R.id.book_title);
        titleText.setText(book.getTitle());
        TextView authorText = layout.findViewById(R.id.book_author);
        authorText.setText(book.getAuthor());
        Button readBookButton = layout.findViewById(R.id.read_book_button);
        if(book.hasRead()) {
            readBookButton.setText("Mark as Unread");
        }else {
            readBookButton.setText("Mark as Read");
        }

        readBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                book.setRead(!book.hasRead());
                BookDatabaseHandler.addBookToDatabase(book);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment()).commit();
            }
        });
        return layout;
    }
}
