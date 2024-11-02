package com.example.dssmv_projectdroid_1221432_1231479;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dssmv_projectdroid_1221432_1231479.api.LibraryApi;
import com.example.dssmv_projectdroid_1221432_1231479.api.RetrofitClient;
import com.example.dssmv_projectdroid_1221432_1231479.model.Library;
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
        // Find the LinearLayout container for adding each library entry as a separate view
        LinearLayout container = findViewById(R.id.containerLibraryData);
        container.removeAllViews(); // Clear existing entries

        for (Library library : libraries) {
            // Create a new TextView for each library entry
            TextView libraryView = new TextView(this);
            libraryView.setTextSize(16);
            libraryView.setTextColor(getResources().getColor(android.R.color.black));
            libraryView.setBackgroundResource(R.drawable.library_item_background); // Set the blue background
            libraryView.setPadding(16, 16, 16, 16);

            // Format the library data with bold name
            SpannableStringBuilder data = new SpannableStringBuilder();
            String nameText = "Library Name: " + library.getName() + "\n";
            data.append(nameText);
            data.setSpan(new StyleSpan(Typeface.BOLD), 0, nameText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            data.append("Address: ").append(library.getAddress()).append("\n")
                    .append("Open Status: ").append(library.isOpen() ? "Open" : "Closed").append("\n")
                    .append("Open Days: ").append(library.getOpenDays()).append("\n")
                    .append("Statement: ").append(library.getOpenStatement()).append("\n");

            libraryView.setText(data);

            // Add the TextView to the container layout
            container.addView(libraryView);

            // Add margin to separate each library entry visually
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) libraryView.getLayoutParams();
            params.setMargins(0, 0, 0, 16); // Margin at the bottom
            libraryView.setLayoutParams(params);
        }
    }

    private void showError(String message) {
        Log.e(TAG, message);
        Toast.makeText(LibrariesActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
