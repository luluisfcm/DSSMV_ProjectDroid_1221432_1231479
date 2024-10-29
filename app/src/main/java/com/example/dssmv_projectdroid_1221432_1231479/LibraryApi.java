package com.example.dssmv_projectdroid_1221432_1231479;

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
    @GET("library")
    Call<List<Library>> getLibraries();


    // Obter uma biblioteca por ID (UUID)
    @GET("libraries/{id}")
    Call<Library> getLibraryById(@Path("id") String id);

    // Adicionar uma nova biblioteca
    @POST("libraries")
    Call<Library> addLibrary(@Body Library library);

    // Atualizar uma biblioteca existente (UUID)
    @PUT("libraries/{id}")
    Call<Library> updateLibrary(@Path("id") String id, @Body Library library);

    // Remover uma biblioteca por ID (UUID)
    @DELETE("libraries/{id}")
    Call<Void> removeLibrary(@Path("id") String id);
}
