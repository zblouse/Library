package com.zblouse.library;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Activity for registering a new user account. User will need to authenticate with Firebase before
 * they are able to create an account.
 */
public class CreateAccountActivity extends AppCompatActivity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_account), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent sendToLoginIntent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            startActivity(sendToLoginIntent);
        }

        EditText nameEditText = findViewById(R.id.name_edit_text);
        Button createAccountButton = findViewById(R.id.create_account_button);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameEditText.getText().toString().isEmpty()){
                    Toast toast = Toast.makeText(context,"Name cannot be empty", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    User newUser = new User(user.getUid(), nameEditText.getText().toString());
                    UserDatabaseHandler.createUser(newUser);
                    Intent sendToUserHomeIntent = new Intent(CreateAccountActivity.this, UserHomeActivity.class);
                    sendToUserHomeIntent.putExtra("user", newUser);
                    startActivity(sendToUserHomeIntent);
                }
            }
        });
    }
}
