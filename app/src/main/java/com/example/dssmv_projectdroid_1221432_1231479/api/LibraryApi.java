// LibraryApi.java
package com.example.dssmv_projectdroid_1221432_1231479.api;

import java.util.List;
import com.example.dssmv_projectdroid_1221432_1231479.model.Library;
import com.example.dssmv_projectdroid_1221432_1231479.model.LibraryBook;
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

    @GET("library/{id}/book")
    Call<List<LibraryBook>> getBooksByLibraryId(@Path("id") String libraryId);

    @GET("assets/cover/{isbn}-s.jpg")
    Call<LibraryBook> getCoverByISBN(@Path("isbn") String isbn);

    @POST("library")
    Call<Library> addLibrary(@Body Library library);

    @PUT("library/{id}")
    Call<Library> updateLibrary(@Path("id") String id, @Body Library library);

    @DELETE("library/{id}")
    Call<Void> removeLibrary(@Path("id") String id);
}
