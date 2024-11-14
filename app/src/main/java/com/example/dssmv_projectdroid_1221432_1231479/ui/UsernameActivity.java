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
    private Button buttonViewUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        // Atribuir o EditText e Button pelos IDs corretos
        editTextUsername = findViewById(R.id.editText_username);
        buttonViewUsers = findViewById(R.id.button_view_users);

        // Configurar o listener para o botão
        buttonViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();

                if (username.isEmpty()) {
                    Toast.makeText(UsernameActivity.this, "Por favor, insira um nome de usuário", Toast.LENGTH_SHORT).show();
                } else {
                    // Enviar o nome de usuário para UserLinkActivity
                    Intent intent = new Intent(UsernameActivity.this, UserLinkActivity.class);
                    intent.putExtra("username", username); // Passando o nome de usuário para a próxima activity
                    startActivity(intent);
                }
            }
        });
    }
}
