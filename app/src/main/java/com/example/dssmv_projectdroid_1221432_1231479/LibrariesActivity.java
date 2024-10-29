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

import java.util.ArrayList;
import java.util.List;

public class LibrariesActivity extends AppCompatActivity {
    private List<Library> libraries;
    private static final String TAG = "LibrariesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries);

        // Tente a chamada da API; caso falhe, use mock
        fetchLibraries();
    }

    private void fetchLibraries() {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/library/").create(LibraryApi.class);
        Call<List<Library>> call = api.getLibraries();

        call.enqueue(new Callback<List<Library>>() {
            @Override
            public void onResponse(Call<List<Library>> call, Response<List<Library>> response) {
                if (response.isSuccessful()) {
                    libraries = response.body();
                    displayLibraries(libraries);
                } else {
                    String errorMessage;
                    try {
                        errorMessage = response.errorBody() != null ? response.errorBody().string() : "Erro desconhecido";
                    } catch (Exception e) {
                        errorMessage = "Erro ao processar a resposta: " + e.getMessage();
                    }
                    Log.e(TAG, "Erro: " + response.code() + " - " + errorMessage);
                    Toast.makeText(LibrariesActivity.this, "Erro: " + response.code() + " - " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Library>> call, Throwable throwable) {
                Log.e(TAG, "Erro na chamada da API: " + throwable.getMessage());
                Toast.makeText(LibrariesActivity.this, "Erro: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                // Fallback para dados mock
                fetchLibraries();
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
