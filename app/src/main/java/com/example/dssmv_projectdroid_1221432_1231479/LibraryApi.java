package com.example.dssmv_projectdroid_1221432_1231479;

import java.util.List;
import java.util.UUID;

import com.example.dssmv_projectdroid_1221432_1231479.model.Library;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LibraryApi {

    // Obter uma lista de todas as bibliotecas
    @GET("libraries")
    Call<List<Library>> getLibraries();

    // Obter uma biblioteca pelo ID
    @GET("libraries/{id}")
    Call<Library> getLibraryById(@Path("id") UUID id);

    // Adicionar uma nova biblioteca
    @POST("libraries")
    Call<Library> addLibrary(@Body Library library);

    // Atualizar uma biblioteca existente
    @PUT("libraries/{id}")
    Call<Library> updateLibrary(@Path("id") UUID id, @Body Library library);

    // Remover uma biblioteca pelo ID
    @DELETE("libraries/{id}")
    Call<Void> removeLibrary(@Path("id") UUID id);
}
