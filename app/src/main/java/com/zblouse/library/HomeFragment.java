package com.zblouse.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HomeFragment extends Fragment implements FirestoreCallback{
    private LinearLayout layout;
    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = (LinearLayout) inflater.inflate(R.layout.fragment_home,container,false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        BookDatabaseHandler.getBooksForUser(user.getUid(), this);
        return layout;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void dataReturned(List<Book> books) {
        System.out.println("Books count: " + books.size());
        BookAdapter bookAdapter = new BookAdapter(books);
        RecyclerView recyclerView = layout.findViewById(R.id.book_list);
        recyclerView.setAdapter(bookAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(layout.getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layout.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
    }
}
