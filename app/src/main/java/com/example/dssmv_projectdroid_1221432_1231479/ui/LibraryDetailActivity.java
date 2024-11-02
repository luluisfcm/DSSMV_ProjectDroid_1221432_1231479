package com.example.dssmv_projectdroid_1221432_1231479.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dssmv_projectdroid_1221432_1231479.R;
import com.example.dssmv_projectdroid_1221432_1231479.api.LibraryApi;
import com.example.dssmv_projectdroid_1221432_1231479.api.RetrofitClient;
import com.example.dssmv_projectdroid_1221432_1231479.model.Book;
import com.example.dssmv_projectdroid_1221432_1231479.model.Library;
import com.example.dssmv_projectdroid_1221432_1231479.model.LibraryBook;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class LibraryDetailActivity extends AppCompatActivity {
    private TextView booksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);

        String libraryId = getIntent().getStringExtra("library_id");

        booksTextView = findViewById(R.id.tvBooks); // TextView para exibir os livros

        if (libraryId != null) {
            fetchBooks(libraryId);
        } else {
            Toast.makeText(this, "Library ID not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchBooks(String libraryId) {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);
        Call<List<LibraryBook>> call = api.getBooksByLibraryId(libraryId);

        call.enqueue(new Callback<List<LibraryBook>>() {
            @Override
            public void onResponse(Call<List<LibraryBook>> call, Response<List<LibraryBook>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayBooks(response.body());
                } else {
                    showError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<LibraryBook>> call, Throwable throwable) {
                showError("Error: " + throwable.getMessage());
            }
        });
    }


    private void displayBooks(List<LibraryBook> books) {
        TextView booksTextView = findViewById(R.id.tvBooks);
        StringBuilder bookDetails = new StringBuilder();

        for (LibraryBook libraryBook : books) {
            Book book = libraryBook.getBook();

            // Extract title, first author name, and ISBN
            String title = book.getTitle();
            String isbn = libraryBook.getIsbn();

            // Get the first author's name if available
            String authorName = (book.getAuthors() != null && !book.getAuthors().isEmpty())
                    ? book.getAuthors().get(0).getName()
                    : "Unknown Author";

            // Append details to the StringBuilder
            bookDetails.append("Title: ").append(title).append("\n");
            bookDetails.append("Author: ").append(authorName).append("\n");
            bookDetails.append("ISBN: ").append(isbn).append("\n\n");
        }

        // Set the text on the TextView
        booksTextView.setText(bookDetails.toString());
    }
private void showError(String message) {
    Toast.makeText(LibraryDetailActivity.this, message, Toast.LENGTH_SHORT).show();
}


}
