package com.example.dssmv_projectdroid_1221432_1231479.ui;

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
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dssmv_projectdroid_1221432_1231479.R;
import com.example.dssmv_projectdroid_1221432_1231479.model.Book;
import com.example.dssmv_projectdroid_1221432_1231479.api.LibraryApi;
import com.example.dssmv_projectdroid_1221432_1231479.api.RetrofitClient;

import java.util.List;

import com.example.dssmv_projectdroid_1221432_1231479.model.Library;
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
            fetchBooksByUser(username);
        } else {
            Toast.makeText(this, "Username não fornecido", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para procurar os livros do usuário através da API
    private void fetchBooksByUser(String username) {
        Log.d("UserLinkActivity", "Fetching books for user: " + username);
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);

        Call<List<LibraryBook>> call = api.getBooksByUser(username);

        call.enqueue(new Callback<List<LibraryBook>>() {
            @Override
            public void onResponse(Call<List<LibraryBook>> call, Response<List<LibraryBook>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("UserLinkActivity", "Books fetched successfully: " + response.body().size());
                    displayBooks(response.body());
                } else {
                    Log.d("UserLinkActivity", "Error fetching books: " + response.code());
                    showError("Erro: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<LibraryBook>> call, Throwable throwable) {
                Log.d("UserLinkActivity", "Failed to fetch books: " + throwable.getMessage());
                showError("Erro: " + throwable.getMessage());
            }
        });
    }


    // Método para exibir os livros na interface
    private void displayBooks(List<LibraryBook> books) {
        LinearLayout container = findViewById(R.id.containerBooks);
        container.removeAllViews(); // Clear existing views
        Log.d("UserLinkActivity", "Displaying books: " + books.size());

        for (LibraryBook libraryBook : books) {
            Book book = libraryBook.getBook();
            String libraryId = libraryBook.getLibraryId();
            // Create a horizontal container
            LinearLayout horizontalContainer = new LinearLayout(this);
            horizontalContainer.setOrientation(LinearLayout.HORIZONTAL);
            horizontalContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            horizontalContainer.setPadding(0, 0, 0, 16);

            // ImageView for the book cover
            ImageView coverImageView = new ImageView(this);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(200, 300);
            coverImageView.setLayoutParams(imageParams);
            coverImageView.setPadding(8, 8, 8, 8);
            coverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            fetchBookCover(libraryBook.getIsbn_book(), book, coverImageView);

            // TextView for book details
            TextView bookDetails = new TextView(this);
            bookDetails.setTextSize(16);
            bookDetails.setTextColor(getResources().getColor(android.R.color.black));
            bookDetails.setPadding(16, 0, 0, 0);

            SpannableStringBuilder data = new SpannableStringBuilder();
            String titleText = "Title: " + book.getTitle() + "\n";
            data.append(titleText);
            data.setSpan(new StyleSpan(Typeface.BOLD), 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            String authorName = (book.getAuthors() != null && !book.getAuthors().isEmpty())
                    ? book.getAuthors().get(0).getName()
                    : "Unknown Author";
            data.append("Author: ").append(authorName).append("\n");

            String dueDate = libraryBook.getDueDate();
            String formattedDate = dueDate.split("T")[0];
            data.append("Due date: ").append(formattedDate).append("\n");
            bookDetails.setText(data);

            // Add click listeners
            horizontalContainer.setOnLongClickListener(v -> {
                showCheckInDialog(libraryId, libraryBook.getIsbn_book(), book.getTitle());
                return true; // Indicate that the event was consumed
            });

            horizontalContainer.addView(coverImageView);
            horizontalContainer.addView(bookDetails);
            container.addView(horizontalContainer);
        }
    }

    private void showCheckInDialog(String libraryId, String isbn, String bookTitle) {
        new AlertDialog.Builder(this)
                .setTitle("Check in")
                .setMessage("Do you want to check in the book \"" + bookTitle + "\"?")
                .setPositiveButton("Sim", (dialog, which) -> performCheckIn(libraryId, isbn))
                .setNegativeButton("Não", null)
                .show();
    }

    private void performCheckIn(String libraryId, String isbn) {
        // Cria uma instância da API
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);

        // Faz a chamada ao endpoint de checkIn
        libraryId = libraryId.substring(0, 8) + "-" +
                libraryId.substring(8, 12) + "-" +
                libraryId.substring(12, 16) + "-" +
                libraryId.substring(16, 20) + "-" +
                libraryId.substring(20, 32);

        Call<Library> call = api.checkInBook(libraryId, isbn, username);

        call.enqueue(new Callback<Library>() {
            @Override
            public void onResponse(Call<Library> call, Response<Library> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserLinkActivity.this, "Check in realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    fetchBooksByUser(username);
                } else {
                    Toast.makeText(UserLinkActivity.this, "Erro ao realizar o check in: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Library> call, Throwable t) {
                fetchBooksByUser(username);
                //Toast.makeText(UserLinkActivity.this, "Falha na comunicação: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchBookCover(String isbn, Book book, ImageView coverImageView) {
        if (isbn != null && !isbn.isEmpty()) {
            // Construct the cover URL with the ISBN
            String coverUrl = "http://193.136.62.24/v1/assets/cover/" + isbn + "-L.jpg";
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