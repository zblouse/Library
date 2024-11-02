package com.zblouse.library;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private final List<Book> books;

    public BookAdapter(List<Book> books){
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_display,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = books.get(position);

        holder.authorTextView.setText(book.getAuthor());
        holder.titleTextView.setText(book.getTitle());
        if(book.hasRead()){
            holder.readTextView.setText("Read");
        } else {
            holder.readTextView.setText("Not Read");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Clicked BOOK: " + book.getTitle());
                ((UserHomeActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new BookViewFragment(book)).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView authorTextView;
        public TextView titleTextView;
        public TextView readTextView;
        public boolean selected;

        public ViewHolder(View itemView){
            super(itemView);
            authorTextView = itemView.findViewById(R.id.book_author);
            titleTextView = itemView.findViewById(R.id.book_title);
            readTextView = itemView.findViewById(R.id.book_read);
        }
    }
}
