package com.example.dssmv_projectdroid_1221432_1231479.ui;

import android.content.Intent;
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
import com.example.dssmv_projectdroid_1221432_1231479.model.LibraryBook;
import com.example.dssmv_projectdroid_1221432_1231479.model.CreateLibraryBookRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.app.AlertDialog;
import android.widget.EditText;
import android.text.InputType;
import android.widget.LinearLayout;


public class LibraryDetailActivity extends AppCompatActivity {

    private String libraryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);

        LinearLayout containerBooksData = findViewById(R.id.containerBooksData);
        if (containerBooksData == null) {
            showError("Container not found");
            return;
        }

        libraryId = getIntent().getStringExtra("library_id");
        if (libraryId != null) {
            fetchBooks(libraryId);
        } else {
            showError("Library ID not found");
        }

        FloatingActionButton fabAddBook = findViewById(R.id.fab_add_book);
        fabAddBook.setOnClickListener(v -> showAddBookDialog());

    }

    private void fetchBooks(String libraryId) {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);
        Call<List<LibraryBook>> call = api.getBooksByLibraryId(libraryId);

        call.enqueue(new Callback<List<LibraryBook>>() {
            @Override
            public void onResponse(Call<List<LibraryBook>> call, Response<List<LibraryBook>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Call displayBooks with the full list of books
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
        LinearLayout container = findViewById(R.id.containerBooksData);
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
    }

    private void showAddBookDialog() {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Book");

        // Create a LinearLayout to hold the input fields
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        // ISBN input field
        final EditText isbnInput = new EditText(this);
        isbnInput.setHint("Enter ISBN");
        layout.addView(isbnInput);

        // Stock input field
        final EditText stockInput = new EditText(this);
        stockInput.setHint("Enter Stock");
        stockInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(stockInput);

        builder.setView(layout);

        // Set up dialog buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String isbn = isbnInput.getText().toString();
            String stockText = stockInput.getText().toString();
            int stock = stockText.isEmpty() ? 0 : Integer.parseInt(stockText);

            // Call method to add book with provided ISBN and stock
            addBookToLibrary(isbn, stock);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        builder.show();
    }

    private void addBookToLibrary(String isbn, int stock) {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);

        String libraryId = getIntent().getStringExtra("library_id");
        if (libraryId == null) {
            showError("Library ID not found");
            return;
        }

        // Create the request body with the stock
        CreateLibraryBookRequest requestBody = new CreateLibraryBookRequest(stock);

        Call<Void> call = api.addBook(libraryId, isbn, requestBody);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LibraryDetailActivity.this, "Book added successfully", Toast.LENGTH_SHORT).show();
                    fetchBooks(libraryId);  // Refresh the list of books
                } else {
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e("LibraryDetailActivity", "Error reading errorBody", e);
                    }
                    String errorMessage = "Failed to add book: " + response.code() + " - " + errorBody;
                    showError(errorMessage);
                    Log.e("LibraryDetailActivity", errorMessage);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showError("Error: " + t.getMessage());
                Log.e("LibraryDetailActivity", "API call failed", t);
            }
        });
    }

    private void fetchBookCover(String isbn, Book book, ImageView coverImageView) {
        if (isbn != null && !isbn.isEmpty()) {
            // Construct the cover URL with the ISBN
            String coverUrl = "http://193.136.62.24/v1/" + "assets/cover/" + isbn + "-S.jpg";
            Log.d("LibraryDetailActivity", "Cover URL for book: " + book.getTitle() + " - " + coverUrl);

            // Load the image using Glide, with a placeholder image if it fails
            Glide.with(this)
                    .load(coverUrl)
                    .placeholder(R.drawable.placeholder_image) // Image shown while loading
                    .error(R.drawable.placeholder_image)        // Image shown on load failure
                    .into(coverImageView);

            // Set an OnClickListener to open details on image click
            coverImageView.setOnClickListener(v -> {
                Intent intent = new Intent(this, BookDetailsActivity.class);
                intent.putExtra("coverUrl", "http://193.136.62.24/v1/assets/cover/" + isbn + "-m.jpg");
                intent.putExtra("title", book.getTitle());
                intent.putExtra("author", (book.getAuthors() != null && !book.getAuthors().isEmpty())
                        ? book.getAuthors().get(0).getName() : "Unknown Author");
                intent.putExtra("stock", 0);  // Passar o valor do estoque dinamicamente, se disponível
                intent.putExtra("description", book.getDescription() != null ? book.getDescription() : "No description available");
                startActivity(intent);
            });
        } else {
            // Set a placeholder if ISBN is null or empty
            coverImageView.setImageResource(R.drawable.placeholder_image);
        }
    }

    private void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
 