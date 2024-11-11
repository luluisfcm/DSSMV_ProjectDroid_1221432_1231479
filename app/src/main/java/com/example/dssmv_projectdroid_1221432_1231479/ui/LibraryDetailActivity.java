package com.example.dssmv_projectdroid_1221432_1231479.ui;

import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
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
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;

public class LibraryDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);

        // Verifique se o container foi encontrado corretamente
        LinearLayout containerBooksData = findViewById(R.id.containerBooksData);
        if (containerBooksData == null) {
            Toast.makeText(this, "Container não encontrado", Toast.LENGTH_SHORT).show();
        }

        String libraryId = getIntent().getStringExtra("library_id");
        if (libraryId != null) {
            fetchBooks(libraryId); // Chama o método para buscar e exibir os livros
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
                    List<LibraryBook> books = response.body();
                    displayBooks(books);
                    // Para cada livro, busque a capa usando o ISBN
                    for (LibraryBook libraryBook : books) {
                        fetchBookCover(libraryBook.getIsbn(), libraryBook, libraryBook.getCoverImageView());
                    }
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

    private void fetchBookCover(String isbn, LibraryBook libraryBook, ImageView coverImageView) {
        if (isbn != null) {
            // Construct the URL directly up to /v1/ as you requested
            String coverUrl = "http://193.136.62.24/v1/assets/cover/" + isbn + "-s.jpg";
            Log.d("LibraryDetailActivity", "Cover URL for book: " + libraryBook.getBook().getTitle() + " - " + coverUrl);

            // Use Glide to load the image from the constructed URL
            Glide.with(this)
                    .load(coverUrl)
                    .into(coverImageView);

            coverImageView.setOnClickListener(v -> {
                Intent intent = new Intent(this, BookDetailsActivity.class);
                intent.putExtra("coverUrl", "http://193.136.62.24/v1/assets/cover/" + isbn + "-m.jpg");
                intent.putExtra("title", libraryBook.getBook().getTitle());
                intent.putExtra("author", libraryBook.getBook().getAuthors().get(0).getName());
                intent.putExtra("stock", libraryBook.getStock());
                startActivity(intent);
            });
        } else {
            coverImageView.setImageDrawable(null);  // Clear image if ISBN is null
        }
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

            SpannableStringBuilder data = new SpannableStringBuilder();
            String titleText = "Title: " + book.getTitle() + "\n";
            data.append(titleText);
            data.setSpan(new StyleSpan(Typeface.BOLD), 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            String authorName = (book.getAuthors() != null && !book.getAuthors().isEmpty())
                    ? book.getAuthors().get(0).getName()
                    : "Unknown Author";
            data.append("Author: ").append(authorName).append("\n");
            data.append("ISBN: ").append(libraryBook.getIsbn()).append("\n");

            bookView.setText(data);

            // Adiciona o TextView ao container
            container.addView(bookView);

            // Cria uma ImageView para a capa
            ImageView coverImageView = new ImageView(this);
            coverImageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            coverImageView.setPadding(8, 8, 8, 8);

            // Carrega a imagem se a URL da capa estiver disponível
            if (book.getCoverUrls() != null && book.getCoverUrls().getMediumUrl() != null) {
                Glide.with(this)
                        .load(book.getCoverUrls().getMediumUrl())
                        .into(coverImageView);
            } else {
                coverImageView.setImageResource(R.drawable.placeholder_image); // Imagem de placeholder
            }

            container.addView(coverImageView);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bookView.getLayoutParams();
            params.setMargins(0, 0, 0, 16);
            bookView.setLayoutParams(params);
        }
    }

    private void displayBookCover(LibraryBook libraryBook) {
        LinearLayout container = findViewById(R.id.containerBooksData);

        // Obter o URL da capa
        String coverUrl = libraryBook.getBook().getCoverUrls() != null
                ? libraryBook.getBook().getCoverUrls().getSmallUrl()
                : "URL da capa não disponível";

        // Log para verificar se o método é chamado e o URL é obtido corretamente
        Log.d("LibraryDetailActivity", "URL da capa: " + coverUrl);

        // Exibir o URL em um TextView
        TextView coverUrlTextView = new TextView(this);
        coverUrlTextView.setTextSize(14);
        coverUrlTextView.setTextColor(getResources().getColor(android.R.color.black));
        coverUrlTextView.setText("URL da capa: " + coverUrl);

        // Adicionar o TextView ao container
        container.addView(coverUrlTextView);
    }

    private void showError(String message) {
        Toast.makeText(LibraryDetailActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}