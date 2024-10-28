package com.example.dssmv_projectdroid_1221432_1231479;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dssmv_projectdroid_1221432_1231479.model.Library;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private LibraryRepository libraryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        libraryRepository = new LibraryRepository();
        fetchAndDisplayLibraries();
    }

    private void fetchAndDisplayLibraries() {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/library/").create(LibraryApi.class);
        Call<List<Library>> call = api.getLibraries();

        Log.d(TAG, "Making API call to: " + call.request().url()); // Log the request URL

        call.enqueue(new Callback<List<Library>>() {
            @Override
            public void onResponse(Call<List<Library>> call, Response<List<Library>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Library> libraries = response.body();
                    Log.d(TAG, "Libraries fetched: " + libraries.size()); // Log number of libraries fetched
                    displayLibraries(libraries);
                } else {
                    Log.e(TAG, "Failed to fetch libraries: " + response.code() + " " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e(TAG, "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    Toast.makeText(MainActivity.this, "Failed to fetch libraries", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Library>> call, Throwable t) {
                Log.e(TAG, "Error fetching libraries: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }





    private void displayLibraries(List<Library> libraries) {
        StringBuilder data = new StringBuilder();
        for (Library library : libraries) {
            data.append("Library Name: ").append(library.getName()).append("\n")
                    .append("Address: ").append(library.getAddress()).append("\n")
                    .append("Open Status: ").append(library.isOpen() ? "Open" : "Closed").append("\n")
                    .append("Open Days: ").append(library.getOpenDays()).append("\n")
                    .append("Statement: ").append(library.getOpenStatement()).append("\n\n");
        }

        TextView tvLibraryData = findViewById(R.id.tvLibraryData);
        tvLibraryData.setText(data.toString());
    }
}
