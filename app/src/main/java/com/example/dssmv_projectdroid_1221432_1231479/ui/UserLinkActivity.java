package com.example.dssmv_projectdroid_1221432_1231479.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dssmv_projectdroid_1221432_1231479.R;
import com.example.dssmv_projectdroid_1221432_1231479.model.Author;
import com.example.dssmv_projectdroid_1221432_1231479.model.Book;
import com.example.dssmv_projectdroid_1221432_1231479.api.LibraryApi;
import com.example.dssmv_projectdroid_1221432_1231479.api.RetrofitClient;

import java.util.List;

import com.example.dssmv_projectdroid_1221432_1231479.model.LibraryBook;
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
        // Verifica se o username foi passado corretamente
        if (username != null) {
            fetchBooksByUser(username);  // Chama o método para buscar livros pelo nome de usuário
        } else {
            Toast.makeText(this, "Username não fornecido", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para procurar os livros do usuário através da API
    private void fetchBooksByUser(String username) {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);

        // A chamada correta agora retorna Call<List<Book>>:
        Call<List<LibraryBook>> call = api.getBooksByUser(username);

        call.enqueue(new Callback<List<LibraryBook>>() {
            @Override
            public void onResponse(Call<List<LibraryBook>> call, Response<List<LibraryBook>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayBooks(response.body());  // Se os livros forem retornados com sucesso, exibe-os
                } else {
                    showError("Erro: " + response.code());  // Caso ocorra algum erro na resposta
                }
            }

            @Override
            public void onFailure(Call<List<LibraryBook>> call, Throwable throwable) {
                showError("Erro: " + throwable.getMessage());  // Caso falhe na comunicação com a API
            }
        });
    }

    // Método para exibir os livros na interface
    private void displayBooks(List<LibraryBook> books) {
        LinearLayout container = findViewById(R.id.containerBooks);
        container.removeAllViews(); // Clear any existing views
        Log.d("LibraryDetailActivity", "Displaying books: " + books.size());  // Verifique o número de livros

        for (LibraryBook libraryBook : books) {
            Book book = libraryBook.getBook();  // Get the Book object

            // Create a Horizontal layout to display the image and text
            LinearLayout horizontalContainer = new LinearLayout(this);
            horizontalContainer.setOrientation(LinearLayout.HORIZONTAL);
            horizontalContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            horizontalContainer.setPadding(0, 0, 0, 16); // Padding between items

            // Create ImageView for the book cover
            ImageView coverImageView = new ImageView(this);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(200, 300); // Set image size
            coverImageView.setLayoutParams(imageParams);
            coverImageView.setPadding(8, 8, 8, 8);
            coverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Fetch and set the cover for the book
            fetchBookCover(libraryBook.getIsbn(), book, coverImageView);  // Pass the ISBN and Book object

            // Create TextView for book details
            TextView bookDetails = new TextView(this);
            bookDetails.setTextSize(16);
            bookDetails.setTextColor(getResources().getColor(android.R.color.black));
            bookDetails.setPadding(16, 0, 0, 0); // Padding between image and text

            // Use SpannableStringBuilder to make the title bold
            SpannableStringBuilder data = new SpannableStringBuilder();
            String titleText = "Title: " + book.getTitle() + "\n";
            data.append(titleText);
            data.setSpan(new StyleSpan(Typeface.BOLD), 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            String authorName = (book.getAuthors() != null && !book.getAuthors().isEmpty())
                    ? book.getAuthors().get(0).getName()
                    : "Unknown Author";
            data.append("Author: ").append(authorName).append("\n");
            data.append("Stock: ").append(String.valueOf(libraryBook.getStock())).append("\n");
            bookDetails.setText(data);

            // Add ImageView and TextView to the horizontal container
            horizontalContainer.addView(coverImageView);
            horizontalContainer.addView(bookDetails);

            // Add the horizontal container to the main vertical container
            container.addView(horizontalContainer);

        }
    }//AJSHGDJASGDJASD

    private void fetchBookCover(String isbn, Book book, ImageView coverImageView) {
        if (isbn != null && !isbn.isEmpty()) {
            // Construct the cover URL with the ISBN
            String coverUrl = "http://193.136.62.24/v1/assets/cover/" + isbn + "-S.jpg";
            Log.d("LibraryDetailActivity", "Cover URL for book: " + book.getTitle() + " - " + coverUrl);

            // Load the image using Glide, with a placeholder image if it fails
            Glide.with(this)
                    .load(coverUrl)
                    .placeholder(R.drawable.placeholder_image) // Image shown while loading
                    .error(R.drawable.placeholder_image)        // Image shown on load failure
                    .into(coverImageView);
        } else {
            // Set a placeholder if ISBN is null or empty
            coverImageView.setImageResource(R.drawable.placeholder_image);
            Log.d("LibraryDetailActivity", "ISBN is null or empty for book: " + book.getTitle());
        }
    }





    // Método para exibir erros na interface
    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}