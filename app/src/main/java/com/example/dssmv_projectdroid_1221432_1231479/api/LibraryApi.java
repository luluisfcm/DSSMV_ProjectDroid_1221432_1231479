// LibraryApi.java
package com.example.dssmv_projectdroid_1221432_1231479.api;

import java.util.List;
import com.example.dssmv_projectdroid_1221432_1231479.model.Library;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LibraryApi {

    // Endpoint para obter a lista de bibliotecas
    @GET("library") // Definido para coincidir com a URL base completa
    Call<List<Library>> getLibraries();

    // Outros endpoints
    @GET("libraries/{id}")
    Call<Library> getLibraryById(@Path("id") String id);

    @POST("libraries")
    Call<Library> addLibrary(@Body Library library);

    @PUT("libraries/{id}")
    Call<Library> updateLibrary(@Path("id") String id, @Body Library library);

    @DELETE("libraries/{id}")
    Call<Void> removeLibrary(@Path("id") String id);
}
