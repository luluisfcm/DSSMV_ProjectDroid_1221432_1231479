package com.example.dssmv_projectdroid_1221432_1231479;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/library/").create(LibraryApi.class);
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

    private void showError(String message) {
        Log.e(TAG, message);
        Toast.makeText(LibrariesActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
