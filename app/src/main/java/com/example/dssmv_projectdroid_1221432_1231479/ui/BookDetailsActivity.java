package com.example.dssmv_projectdroid_1221432_1231479.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.dssmv_projectdroid_1221432_1231479.R;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        // Find views
        ImageView bookCoverImageView = findViewById(R.id.bookCoverImageView);
        TextView bookTitleTextView = findViewById(R.id.bookTitleTextView);
        TextView bookAuthorTextView = findViewById(R.id.bookAuthorTextView);
        TextView bookDescriptionTextView = findViewById(R.id.bookDescriptionTextView);
        TextView bookStockTextView = findViewById(R.id.bookStockTextView);

        // Get data from the Intent
        String coverUrl = getIntent().getStringExtra("coverUrl");
        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String description = getIntent().getStringExtra("description");
        int stock = getIntent().getIntExtra("stock", 0);

        // Set data to views
        Glide.with(this)
                .load(coverUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(bookCoverImageView);

        bookTitleTextView.setText("Title: " + title);
        bookAuthorTextView.setText("Author: " + author);
        bookDescriptionTextView.setText("Description: " + description);
        bookStockTextView.setText("Stock: " + stock);
    }
}
