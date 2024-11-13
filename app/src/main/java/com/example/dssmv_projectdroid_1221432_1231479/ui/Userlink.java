package com.example.dssmv_projectdroid_1221432_1231479.ui;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dssmv_projectdroid_1221432_1231479.R;
import com.example.dssmv_projectdroid_1221432_1231479.model.Book;
import com.example.dssmv_projectdroid_1221432_1231479.api.LibraryApi;
import com.example.dssmv_projectdroid_1221432_1231479.api.RetrofitClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLinkActivity extends AppCompatActivity {

    private LinearLayout containerBooks;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_link);

        // Inicializa o layout onde os livros serão exibidos
        containerBooks = findViewById(R.id.containerBooks);

        // Recebe o nome de usuário da Intent
        username = getIntent().getStringExtra("username");

        if (username != null) {
            fetchBooksByUser(username);
        } else {
            Toast.makeText(this, "Username não fornecido", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchBooksByUser(String username) {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);

        // A chamada correta agora retorna Call<List<Book>>:
        Call<List<Book>> call = api.getBooksByUser(username);

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayBooks(response.body());
                } else {
                    showError("Erro: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable throwable) {
                showError("Erro: " + throwable.getMessage());
            }
        });
    }

    private void displayBooks(List<Book> books) {
        containerBooks.removeAllViews();

        for (Book book : books) {
            TextView bookView = new TextView(this);
            bookView.setTextSize(16);
            bookView.setTextColor(getResources().getColor(android.R.color.black));
            bookView.setBackgroundResource(R.drawable.book_item_background);
            bookView.setPadding(16, 16, 16, 16);

            String displayText = "Title: " + (book.getTitle() != null ? book.getTitle() : "Unknown") + "\n" +
                    "Author: " + (book.getAuthors() != null ? book.getAuthors() : "Unknown") + "\n";

            bookView.setText(displayText);

            containerBooks.addView(bookView);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bookView.getLayoutParams();
            params.setMargins(0, 0, 0, 16);
            bookView.setLayoutParams(params);
        }
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
