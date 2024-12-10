package com.example.dssmv_projectdroid_1221432_1231479.ui;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.dssmv_projectdroid_1221432_1231479.R;
import com.example.dssmv_projectdroid_1221432_1231479.api.LibraryApi;
import com.example.dssmv_projectdroid_1221432_1231479.api.RetrofitClient;
import com.example.dssmv_projectdroid_1221432_1231479.model.Library;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import android.app.AlertDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibrariesActivity extends AppCompatActivity {
    private List<Library> libraries;
    private static final String TAG = "LibrariesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries);

        fetchLibraries();
        FloatingActionButton fabAddLibrary = findViewById(R.id.fab_add_library);
        fabAddLibrary.setOnClickListener(v -> {
            addLibrary();
        });
    }

    private void fetchLibraries() {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);
        Call<List<Library>> call = api.getLibraries();

        call.enqueue(new Callback<List<Library>>() {
            @Override
            public void onResponse(Call<List<Library>> call, Response<List<Library>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    libraries = response.body();
                    displayLibraries(libraries);
                } else {
                    showError("Erro: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Library>> call, Throwable throwable) {
                showError("Erro: " + throwable.getMessage());
            }
        });
    }

    private void displayLibraries(List<Library> libraries) {
        LinearLayout container = findViewById(R.id.containerLibraryData);
        container.removeAllViews();

        for (Library library : libraries) {
            TextView libraryView = new TextView(this);
            libraryView.setTextSize(16);
            libraryView.setTextColor(getResources().getColor(android.R.color.black));
            libraryView.setBackgroundResource(R.drawable.library_item_background);
            libraryView.setPadding(16, 16, 16, 16);

            // Create SpannableStringBuilder to format the display text
            SpannableStringBuilder data = new SpannableStringBuilder();

            // Check each field for null before appending
            String nameText = "Library Name: " + (library.getName() != null ? library.getName() : "Unknown") + "\n";
            data.append(nameText);
            data.setSpan(new StyleSpan(Typeface.BOLD), 0, nameText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            data.append("Address: ").append(library.getAddress() != null ? library.getAddress() : "Unknown").append("\n")
                    .append("Open Status: ").append(library.isOpen() ? "Open" : "Closed").append("\n")
                    .append("Open Days: ").append(library.getOpenDays() != null ? library.getOpenDays() : "N/A").append("\n")
                    .append("Statement: ").append(library.getOpenStatement() != null ? library.getOpenStatement() : "N/A").append("\n");

            libraryView.setText(data);

            libraryView.setOnLongClickListener(v -> {
                // Cria um PopupMenu
                PopupMenu popupMenu = new PopupMenu(this, v);
                popupMenu.getMenu().add("Edit");
                popupMenu.getMenu().add("Delete");

                // Define o listener para as opções do menu
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getTitle().equals("Edit")) {
                        // Ação para Editar
                        editLibrary(library);
                    } else if (item.getTitle().equals("Delete")) {
                        // Ação para Deletar
                        showDeleteConfirmationDialog(library);
                    }
                    return true;
                });

                // Exibe o menu
                popupMenu.show();
                return true;
            });

            // Set click listener to navigate to LibraryDetailActivity with the library's ID
            libraryView.setOnClickListener(v -> {
                Intent intent = new Intent(this, LibraryDetailActivity.class);
                intent.putExtra("library_id", library.getId() != null ? library.getId().toString() : ""); // Pass the ID as a String
                startActivity(intent);
            });

            container.addView(libraryView);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) libraryView.getLayoutParams();
            params.setMargins(0, 0, 0, 16);
            libraryView.setLayoutParams(params);
        }
    }

    private void showDeleteConfirmationDialog(Library library) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Library")
                .setMessage("Are you sure you want to delete " + library.getName() + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // If user confirms, delete the library
                    deleteLibrary(library.getId());
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void deleteLibrary(String libraryId) {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);
        Call<Void> call = api.removeLibrary(libraryId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Library deleted successfully
                    Toast.makeText(LibrariesActivity.this, "Library deleted successfully", Toast.LENGTH_SHORT).show();
                    fetchLibraries(); // Refresh the list of libraries
                } else {
                    Toast.makeText(LibrariesActivity.this, "Failed to delete library", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(LibrariesActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addLibrary() {
        String[] openTime = {""};
        String[] closeTime = {""};
        boolean[] selectedDays = new boolean[7];
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_library, null);

        EditText editTextLibraryName = dialogView.findViewById(R.id.editTextLibraryName);
        EditText editTextLibraryAddress = dialogView.findViewById(R.id.editTextLibraryAddress);

        Button btnSelectOpenTime = dialogView.findViewById(R.id.btnSelectOpenTime);
        Button btnSelectCloseTime = dialogView.findViewById(R.id.btnSelectCloseTime);
        Button btnSelectOpenDays = dialogView.findViewById(R.id.btnSelectOpenDays);

        btnSelectOpenTime.setOnClickListener(v -> {
            new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                openTime[0] = String.format("%02d:%02d", hourOfDay, minute);
                btnSelectOpenTime.setText(openTime[0]); // Atualiza o texto do botão
            }, 0, 0, true).show();
        });

        btnSelectCloseTime.setOnClickListener(v -> {
            new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                closeTime[0] = String.format("%02d:%02d", hourOfDay, minute);
                btnSelectCloseTime.setText(closeTime[0]); // Atualiza o texto do botão
            }, 0, 0, true).show();
        });

        btnSelectOpenDays.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Open Days");
            builder.setMultiChoiceItems(daysOfWeek, selectedDays, (dialog, which, isChecked) -> {
                selectedDays[which] = isChecked; // Atualiza a seleção
            });

            builder.setPositiveButton("OK", (dialog, which) -> {
                StringBuilder openDays = new StringBuilder();
                for (int i = 0; i < daysOfWeek.length; i++) {
                    if (selectedDays[i]) {
                        if (openDays.length() > 0) {
                            openDays.append(", ");
                        }
                        openDays.append(daysOfWeek[i]);
                    }
                }
                btnSelectOpenDays.setText(openDays.length() > 0 ? openDays.toString() : "Select Days");
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });

        new AlertDialog.Builder(this)
                .setTitle("Add New Library")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String libraryName = editTextLibraryName.getText().toString().trim();
                    String libraryAddress = editTextLibraryAddress.getText().toString().trim();

                    StringBuilder openDaysBuilder = new StringBuilder();
                    for (int i = 0; i < daysOfWeek.length; i++) {
                        if (selectedDays[i]) {
                            if (openDaysBuilder.length() > 0) {
                                openDaysBuilder.append(", ");
                            }
                            openDaysBuilder.append(daysOfWeek[i]);
                        }
                    }

                    // Cria um objeto Library com os dados preenchidos
                    Library newLibrary = new Library();
                    newLibrary.setName(libraryName);
                    newLibrary.setAddress(libraryAddress);
                    newLibrary.setOpenTime(openTime[0]); // Define o horário de abertura
                    newLibrary.setCloseTime(closeTime[0]); // Define o horário de fechamento
                    newLibrary.setOpenDays(openDaysBuilder.toString().trim());

                    // Chama a API para adicionar a biblioteca
                    LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);
                    Call<Library> call = api.addLibrary(newLibrary);
                    call.enqueue(new Callback<Library>() {
                        @Override
                        public void onResponse(Call<Library> call, Response<Library> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(LibrariesActivity.this, "Library added!", Toast.LENGTH_SHORT).show();
                                fetchLibraries(); // Atualiza a lista de bibliotecas
                            } else {
                                showError("Failed to add library: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Library> call, Throwable t) {
                            showError("Error: " + t.getMessage());
                        }
                    });
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .show();
    }

    private void editLibrary(Library library) {
        // Inflate o layout do diálogo de edição
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_edit_library, null);

        // Inicialize os campos do diálogo
        EditText editTextName = dialogView.findViewById(R.id.editTextEditLibraryName);
        EditText editTextAddress = dialogView.findViewById(R.id.editTextEditLibraryAddress);

        // Preencha os campos com os dados atuais da biblioteca
        editTextName.setText(library.getName());
        editTextAddress.setText(library.getAddress());

        // Crie o diálogo
        new AlertDialog.Builder(this)
                .setTitle("Edit Library")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    // Atualize os valores da biblioteca
                    String updatedName = editTextName.getText().toString().trim();
                    String updatedAddress = editTextAddress.getText().toString().trim();

                    library.setName(updatedName);
                    library.setAddress(updatedAddress);

                    // Envie a atualização para o servidor
                    updateLibrary(library);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void updateLibrary(Library library) {
        LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);
        Call<Library> call = api.updateLibrary(library.getId(), library);

        call.enqueue(new Callback<Library>() {
            @Override
            public void onResponse(Call<Library> call, Response<Library> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LibrariesActivity.this, "Library updated successfully!", Toast.LENGTH_SHORT).show();
                    fetchLibraries(); // Atualize a lista de bibliotecas
                } else {
                    showError("Failed to update library: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Library> call, Throwable t) {
                showError("Error: " + t.getMessage());
            }
        });
    }

    private void showError(String message) {
        Log.e(TAG, message);
        Toast.makeText(LibrariesActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}