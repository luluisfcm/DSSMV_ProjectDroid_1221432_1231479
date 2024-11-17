package com.example.dssmv_projectdroid_1221432_1231479.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.Button;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class UserLinkActivity extends AppCompatActivity {

    private LinearLayout containerBooks;
    private String username;

    // Função para converter dp para px
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    // Método para criar e configurar um botão Check-in
    private Button createCheckInButton() {
        Button checkInButton = new Button(this);
        checkInButton.setText("IN");

        // Definindo LayoutParams para o botão
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,  // Largura ajustada para preencher o espaço
                dpToPx(30)  // Altura fixa para o botão (48dp)
        );

        // Ajusta a margem do botão
        buttonLayoutParams.setMargins(0, 100, 0, dpToPx(0));  // Margem inferior de 8dp

        // Aplica LayoutParams ao botão
        checkInButton.setLayoutParams(buttonLayoutParams);

        // Adiciona padding para garantir um botão compacto
        checkInButton.setPadding(dpToPx(16), dpToPx(8), dpToPx(16), dpToPx(8));

        // Define o fundo do botão (certifique-se de que você tenha um drawable adequado)
        checkInButton.setBackgroundResource(R.drawable.button_background);

        // Define o texto do botão como branco
        checkInButton.setTextColor(Color.WHITE);

        // Define o tamanho do texto
        checkInButton.setTextSize(10);  // Tamanho fixo para o texto

        return checkInButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_link);

        containerBooks = findViewById(R.id.containerBooks);
        username = getIntent().getStringExtra("username");

        if (username != null) {
            fetchBooksByUser(username);  // Buscar livros do usuário
        } else {
            Toast.makeText(this, "Username não fornecido", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchBooksByUser(String username) {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);
        Call<List<LibraryBook>> call = api.getBooksByUser(username);

        call.enqueue(new Callback<List<LibraryBook>>() {
            @Override
            public void onResponse(Call<List<LibraryBook>> call, Response<List<LibraryBook>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayBooks(response.body());
                } else {
                    showError("Erro: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<LibraryBook>> call, Throwable throwable) {
                showError("Erro: " + throwable.getMessage());
            }
        });
    }

    private void displayBooks(List<LibraryBook> books) {
        containerBooks.removeAllViews(); // Limpa qualquer visualização anterior

        for (LibraryBook libraryBook : books) {
            Book book = libraryBook.getBook();  // Obtém o objeto do livro
            String isbn = libraryBook.getIsbn();

            // Cria um layout horizontal para exibir a imagem e o texto
            LinearLayout horizontalContainer = new LinearLayout(this);
            horizontalContainer.setOrientation(LinearLayout.HORIZONTAL);
            horizontalContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            horizontalContainer.setPadding(0, 0, 0, 16); // Padding entre os itens

            // Cria a ImageView para a capa do livro
            ImageView coverImageView = new ImageView(this);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(200, 300); // Define o tamanho da imagem
            coverImageView.setLayoutParams(imageParams);
            coverImageView.setPadding(8, 8, 8, 8);
            coverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


            // Fetch and set the cover for the book
            fetchBookCover(libraryBook.getIsbn_book(), book, coverImageView);  // Pass the ISBN and Book object

            // Cria o TextView para os detalhes do livro
            TextView bookDetails = new TextView(this);
            bookDetails.setTextSize(16);
            bookDetails.setTextColor(getResources().getColor(android.R.color.black));
            bookDetails.setPadding(16, 0, 0, 0); // Padding entre a imagem e o texto

            // Define o texto do livro usando o SpannableStringBuilder
            SpannableStringBuilder data = new SpannableStringBuilder();
            String titleText = "Title: " + book.getTitle() + "\n";
            data.append(titleText);
            data.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            String authorName = (book.getAuthors() != null && !book.getAuthors().isEmpty())
                    ? book.getAuthors().get(0).getName()
                    : "Unknown Author";
            data.append("Author: ").append(authorName).append("\n");
            data.append("Entregar até: ").append(libraryBook.getDueDate()).append("\n");
            bookDetails.setText(data);

            // Cria o botão Check-in para o livro
            Button checkInButton = createCheckInButton(); // Usa o método para criar o botão consistente

            // Define o ouvinte de clique para o botão Check-in
            checkInButton.setOnClickListener(v -> {
                checkInBook(libraryBook.getIsbn(), username, horizontalContainer, checkInButton);
            });

            // Adiciona a imagem, o texto e o botão ao layout horizontal
            horizontalContainer.addView(coverImageView);
            horizontalContainer.addView(bookDetails);
            horizontalContainer.addView(checkInButton);

            // Adiciona o layout horizontal ao container principal
            containerBooks.addView(horizontalContainer);
        }
    }

    private void fetchBookCover(String isbn, Book book, ImageView coverImageView) {
        if (isbn != null && !isbn.isEmpty()) {
            // Construct the cover URL with the ISBN
            String coverUrl = "http://193.136.62.24/v1/assets/cover/" + isbn + "-L.jpg";
            Log.d("LibraryDetailActivity", "Cover URL for book: " + book.getTitle() + " - " + coverUrl);

            // Load the image using Glide, with a placeholder image if it fails
            Glide.with(this)
                    .load(coverUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(coverImageView);
        } else {
            coverImageView.setImageResource(R.drawable.placeholder_image);
        }
    }
  
    // Método para exibir erros na interface
    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
