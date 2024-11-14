package com.example.dssmv_projectdroid_1221432_1231479.ui;
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
import java.util.List;

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
                showDeleteConfirmationDialog(library);
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
        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_library, null);

        // Initialize views in the dialog
        EditText editTextLibraryName = dialogView.findViewById(R.id.editTextLibraryName);
        EditText editTextLibraryAddress = dialogView.findViewById(R.id.editTextLibraryAddress);
        CheckBox checkBoxLibraryOpen = dialogView.findViewById(R.id.checkBoxLibraryOpen);

        CheckBox checkBoxMonday = dialogView.findViewById(R.id.checkBoxMonday);
        CheckBox checkBoxTuesday = dialogView.findViewById(R.id.checkBoxTuesday);
        CheckBox checkBoxWednesday = dialogView.findViewById(R.id.checkBoxWednesday);
        CheckBox checkBoxThursday = dialogView.findViewById(R.id.checkBoxThursday);
        CheckBox checkBoxFriday = dialogView.findViewById(R.id.checkBoxFriday);
        CheckBox checkBoxSaturday = dialogView.findViewById(R.id.checkBoxSaturday);
        CheckBox checkBoxSunday = dialogView.findViewById(R.id.checkBoxSunday);

        TimePicker timePickerOpenTime = dialogView.findViewById(R.id.timePickerOpenTime);
        TimePicker timePickerCloseTime = dialogView.findViewById(R.id.timePickerCloseTime);

        // Create and show the AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Add New Library")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    // Gather input data
                    String libraryName = editTextLibraryName.getText().toString().trim();
                    String libraryAddress = editTextLibraryAddress.getText().toString().trim();
                    boolean isOpen = checkBoxLibraryOpen.isChecked();

                    // Collect selected days
                    StringBuilder openDaysBuilder = new StringBuilder();
                    if (checkBoxMonday.isChecked()) openDaysBuilder.append("Mon ");
                    if (checkBoxTuesday.isChecked()) openDaysBuilder.append("Tue ");
                    if (checkBoxWednesday.isChecked()) openDaysBuilder.append("Wed ");
                    if (checkBoxThursday.isChecked()) openDaysBuilder.append("Thu ");
                    if (checkBoxFriday.isChecked()) openDaysBuilder.append("Fri ");
                    if (checkBoxSaturday.isChecked()) openDaysBuilder.append("Sat ");
                    if (checkBoxSunday.isChecked()) openDaysBuilder.append("Sun ");

                    String openDays = openDaysBuilder.toString().trim();

                    // Collect selected times from TimePickers
                    int openHour = timePickerOpenTime.getHour();
                    int openMinute = timePickerOpenTime.getMinute();
                    int closeHour = timePickerCloseTime.getHour();
                    int closeMinute = timePickerCloseTime.getMinute();

                    String openStatement = String.format("%02d:%02d - %02d:%02d",
                            openHour, openMinute, closeHour, closeMinute);

                    // Create Library object with collected data
                    Library newLibrary = new Library();
                    newLibrary.setName(libraryName);
                    newLibrary.setAddress(libraryAddress);
                    newLibrary.setOpen(isOpen);
                    newLibrary.setOpenDays(openDays);
                    newLibrary.setOpenStatement(openStatement);

                    // Call API to add library
                    LibraryApi api = RetrofitClient.getClient("http://193.136.62.24/v1/").create(LibraryApi.class);
                    Call<Library> call = api.addLibrary(newLibrary);
                    call.enqueue(new Callback<Library>() {
                        @Override
                        public void onResponse(Call<Library> call, Response<Library> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(LibrariesActivity.this, "Library added!", Toast.LENGTH_SHORT).show();
                                fetchLibraries(); // Refresh library list
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

    private void showError(String message) {
        Log.e(TAG, message);
        Toast.makeText(LibrariesActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}