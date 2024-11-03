package com.example.dssmv_projectdroid_1221432_1231479.ui;

import android.os.Bundle;
import android.widget.LinearLayout;
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
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;

public class LibraryDetailActivity extends AppCompatActivity {
    private TextView booksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);

        String libraryId = getIntent().getStringExtra("library_id");

        // Atualize para buscar o LinearLayout onde os livros serão exibidos
        LinearLayout containerBooksData = findViewById(R.id.containerBooksData);

        if (libraryId != null) {
            fetchBooks(libraryId); // Esse método irá chamar displayBooks para preencher o layout
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

    private  void fetchCoverUrl(String isbn){
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);
        Call<List<LibraryBook>> call = api.getCoverByISBN(isbn);

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
        }
        );
    }

    private void displayBooks(List<LibraryBook> books) {
        LinearLayout container = findViewById(R.id.containerBooksData);
        container.removeAllViews();

        for (LibraryBook libraryBook : books) {
            Book book = libraryBook.getBook();

            TextView bookView = new TextView(this);
            bookView.setTextSize(16);
            bookView.setTextColor(getResources().getColor(android.R.color.black));
            bookView.setBackgroundResource(R.drawable.book_item_background);
            bookView.setPadding(16, 16, 16, 16);

            // Use SpannableStringBuilder to style the text
            SpannableStringBuilder data = new SpannableStringBuilder();

            // Title with bold style
            String titleText = "Title: " + book.getTitle() + "\n";
            data.append(titleText);
            data.setSpan(new StyleSpan(Typeface.BOLD), 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Author and ISBN details
            String authorName = (book.getAuthors() != null && !book.getAuthors().isEmpty())
                    ? book.getAuthors().get(0).getName()
                    : "Unknown Author";
            data.append("Author: ").append(authorName).append("\n");
            data.append("ISBN: ").append(libraryBook.getIsbn()).append("\n");

            bookView.setText(data);

            // Add the styled TextView to the container
            container.addView(bookView);

            // Set margins for the view
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bookView.getLayoutParams();
            params.setMargins(0, 0, 0, 16);
            bookView.setLayoutParams(params);
        }
    }



    private void showError(String message) {
        Toast.makeText(LibraryDetailActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
