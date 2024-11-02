package com.example.dssmv_projectdroid_1221432_1231479.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dssmv_projectdroid_1221432_1231479.R;

// LibraryDetailActivity.java
public class LibraryDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);

        // Get data from the intent
        String libraryName = getIntent().getStringExtra("library_name");
        String libraryAddress = getIntent().getStringExtra("library_address");
        boolean isOpen = getIntent().getBooleanExtra("is_open", false);
        String openDays = getIntent().getStringExtra("open_days");
        String openStatement = getIntent().getStringExtra("open_statement");

        // Display the library data (assuming you have corresponding TextViews in activity_library_detail.xml)
        TextView nameTextView = findViewById(R.id.tvLibraryName);
        TextView addressTextView = findViewById(R.id.tvLibraryAddress);
        TextView openStatusTextView = findViewById(R.id.tvOpenStatus);
        TextView openDaysTextView = findViewById(R.id.tvOpenDays);
        TextView statementTextView = findViewById(R.id.tvOpenStatement);

        nameTextView.setText(libraryName);
        addressTextView.setText(libraryAddress);
        openStatusTextView.setText(isOpen ? "Open" : "Closed");
        openDaysTextView.setText(openDays);
        statementTextView.setText(openStatement);
    }
}

