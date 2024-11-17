// LibraryApi.java
package com.example.dssmv_projectdroid_1221432_1231479.api;

import java.util.List;

import com.example.dssmv_projectdroid_1221432_1231479.model.Book;
import com.example.dssmv_projectdroid_1221432_1231479.model.CreateLibraryBookRequest;
import com.example.dssmv_projectdroid_1221432_1231479.model.Library;
import com.example.dssmv_projectdroid_1221432_1231479.model.LibraryBook;
import retrofit2.Call;
import retrofit2.http.*;

public interface LibraryApi {

    // Endpoint para obter a lista de bibliotecas
    @GET("library") // Definido para coincidir com a URL base completa
    Call<List<Library>> getLibraries();

    @GET("library/{id}/book")
    Call<List<LibraryBook>> getBooksByLibraryId(@Path("id") String libraryId);

    @POST("library/{libraryId}/book/{isbn}")
    Call<Void> addBook(@Path("libraryId") String libraryId,
                       @Path("isbn") String isbn,
                       @Body CreateLibraryBookRequest request);

    @GET("user/checked-out")
    Call<List<LibraryBook>> getBooksByUser(@Query("userId") String username);

    @POST("library")
    Call<Library> addLibrary(@Body Library library);

    @POST("library/{libraryId}/book/{isbn}/checkout?userId={user}")
    Call<Library> checkOutBook(@Body Library library);

    @PUT("library/{id}")
    Call<Library> updateLibrary(@Path("id") String id, @Body Library library);

    @DELETE("library/{id}")
    Call<Void> removeLibrary(@Path("id") String id);
}
