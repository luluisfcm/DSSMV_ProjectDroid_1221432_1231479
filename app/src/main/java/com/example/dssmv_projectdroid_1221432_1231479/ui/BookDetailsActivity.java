package com.example.dssmv_projectdroid_1221432_1231479.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
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

        // Find the views
        ImageView bookCoverImageView = findViewById(R.id.bookCoverImageView);
        TextView bookTitleTextView = findViewById(R.id.bookTitleTextView);
        TextView bookAuthorTextView = findViewById(R.id.bookAuthorTextView);
        TextView bookDescriptionTextView = findViewById(R.id.bookDescriptionTextView);
        TextView bookStockTextView = findViewById(R.id.bookStockTextView);

        // Get data from the Intent
        String isbn = getIntent().getStringExtra("isbn");
        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String description = getIntent().getStringExtra("description");
        int stock = getIntent().getIntExtra("stock", 0);

        // Set the data on views, including loading the image with Glide
        fetchBookCover(isbn, bookCoverImageView);

        bookTitleTextView.setText("Title: " + title);
        bookAuthorTextView.setText(getBoldText("Author: ", author));
        bookDescriptionTextView.setText(getBoldText("Description: ", description));
        bookStockTextView.setText(getBoldText("Stock: ", String.valueOf(stock)));
    }

    private SpannableStringBuilder getBoldText(String label, String value) {
        SpannableStringBuilder builder = new SpannableStringBuilder(label + value);
        builder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    private void fetchBookCover(String isbn, ImageView bookCoverImageView) {
        if (isbn != null && !isbn.isEmpty()) {
            // Construct the cover URL with the ISBN
            String coverUrl = "http://193.136.62.24/v1/" + "assets/cover/" + isbn + "-L.jpg";

            // Load the image using Glide, with a placeholder image if it fails
            Glide.with(this)
                    .load(coverUrl)
                    .placeholder(R.drawable.placeholder_image) // Image shown while loading
                    .error(R.drawable.placeholder_image)        // Image shown on load failure
                    .into(bookCoverImageView);
        } else {
            // Set a placeholder if ISBN is null or empty
            bookCoverImageView.setImageResource(R.drawable.placeholder_image);
        }
    }
}
