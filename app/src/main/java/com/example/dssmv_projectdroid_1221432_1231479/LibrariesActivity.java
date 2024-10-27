package com.example.dssmv_projectdroid_1221432_1231479;

import com.example.dssmv_projectdroid_1221432_1231479.model.Library;
import retrofit2.Call;

import java.util.List;

public class LibrariesActivity {
    private void fetchLibraries(){
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);

        Call<List<Library>> call = api.getLibraries();


    }
}
