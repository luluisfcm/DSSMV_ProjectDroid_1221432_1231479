package com.example.dssmv_projectdroid_1221432_1231479.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dssmv_projectdroid_1221432_1231479.R;


public class UsernameActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        editTextUsername = findViewById(R.id.editText_username);
        buttonSubmit = findViewById(R.id.button_submit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();

                if (username.isEmpty()) {
                    Toast.makeText(UsernameActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                } else {
                    // Enviar para a próxima Activity ou salvar o nome de usuário
                    Intent intent = new Intent(UsernameActivity.this, MainActivity.class);
                    intent.putExtra("username", username);  // Enviar o nome para outra Activity
                    startActivity(intent);
                }
            }
        });
    }
}

