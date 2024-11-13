package com.example.dssmv_projectdroid_1221432_1231479.ui;
import android.content.Intent;
import com.example.dssmv_projectdroid_1221432_1231479.R;
import com.example.dssmv_projectdroid_1221432_1231479.api.LibraryApi;
import com.example.dssmv_projectdroid_1221432_1231479.api.RetrofitClient;
import com.example.dssmv_projectdroid_1221432_1231479.model.Library;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

import java.util.List;

public class LibrariesActivity extends AppCompatActivity {
    private List<Library> libraries;
    private static final String TAG = "LibrariesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries);

        fetchLibraries();
        FloatingActionButton fabAddLibrary = findViewById(R.id.fab_add_library);
        fabAddLibrary.setOnClickListener(v -> {
            // Call addLibrary() when the button is clicked
            addLibrary();
        });
    }

    private void fetchLibraries() {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);
        Call<List<Library>> call = api.getLibraries();

        call.enqueue(new Callback<List<Library>>() {
            @Override
            public void onResponse(Call<List<Library>> call, Response<List<Library>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    libraries = response.body();
                    displayLibraries(libraries);
                } else {
                    showError("Erro: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Library>> call, Throwable throwable) {
                showError("Erro: " + throwable.getMessage());
            }
        });
    }

    private void displayLibraries(List<Library> libraries) {
        LinearLayout container = findViewById(R.id.containerLibraryData);
        container.removeAllViews();

        for (Library library : libraries) {
            TextView libraryView = new TextView(this);
            libraryView.setTextSize(16);
            libraryView.setTextColor(getResources().getColor(android.R.color.black));
            libraryView.setBackgroundResource(R.drawable.library_item_background);
            libraryView.setPadding(16, 16, 16, 16);

            // Create SpannableStringBuilder to format the display text
            SpannableStringBuilder data = new SpannableStringBuilder();

            // Check each field for null before appending
            String nameText = "Library Name: " + (library.getName() != null ? library.getName() : "Unknown") + "\n";
            data.append(nameText);
            data.setSpan(new StyleSpan(Typeface.BOLD), 0, nameText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            data.append("Address: ").append(library.getAddress() != null ? library.getAddress() : "Unknown").append("\n")
                    .append("Open Status: ").append(library.isOpen() ? "Open" : "Closed").append("\n")
                    .append("Open Days: ").append(library.getOpenDays() != null ? library.getOpenDays() : "N/A").append("\n")
                    .append("Statement: ").append(library.getOpenStatement() != null ? library.getOpenStatement() : "N/A").append("\n");

            libraryView.setText(data);

            // Set click listener to navigate to LibraryDetailActivity with the library's ID
            libraryView.setOnClickListener(v -> {
                Intent intent = new Intent(this, LibraryDetailActivity.class);
                intent.putExtra("library_id", library.getId() != null ? library.getId().toString() : ""); // Pass the ID as a String
                startActivity(intent);
            });

            container.addView(libraryView);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) libraryView.getLayoutParams();
            params.setMargins(0, 0, 0, 16);
            libraryView.setLayoutParams(params);
        }
    }

    private void addLibrary() {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);

        Library newLibrary = new Library("New Library", "123 Library St", true, "Monday to Friday", "Open to the public");
        Call<Library> call = api.addLibrary(newLibrary);

        call.enqueue(new Callback<Library>() {
            @Override
            public void onResponse(Call<Library> call, Response<Library> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LibrariesActivity.this, "Library added successfully", Toast.LENGTH_SHORT).show();
                    fetchLibraries(); // Refresh the list
                } else {
                    showError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Library> call, Throwable t) {
                showError("Failure: " + t.getMessage());
            }
        });
    }


    private void showError(String message) {
        Log.e(TAG, message);
        Toast.makeText(LibrariesActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}