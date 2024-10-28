package com.example.dssmv_projectdroid_1221432_1231479;

import com.example.dssmv_projectdroid_1221432_1231479.model.Library;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;


import java.util.List;

public class LibraryRepository {

    public void fetchLibraries(Callback<List<Library>> callback) {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/library/").create(LibraryApi.class);
        Call<List<Library>> call = api.getLibraries();
        call.enqueue(callback);
    }
}