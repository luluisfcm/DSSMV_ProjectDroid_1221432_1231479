package com.example.dssmv_projectdroid_1221432_1231479;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dssmv_projectdroid_1221432_1231479.model.Library;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the API instance
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);

        // Make the API call
        api.getLibraries().enqueue(new Callback<List<Library>>() {
            @Override
            public void onResponse(Call<List<Library>> call, Response<List<Library>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Library> libraries = response.body();
                    for (Library library : libraries) {
                        Log.d(TAG, "Library Name: " + library.getName());
                        Log.d(TAG, "Address: " + library.getAddress());
                    }
                } else {
                    Log.e(TAG, "Failed to fetch libraries: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Library>> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }
}