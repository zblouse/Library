package com.zblouse.library;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.splashscreen.SplashScreen;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements FirestoreCallback {
    private boolean clickedLogin = false;
    private boolean clickedCreate = false;
    private Context context;
    private FirestoreCallback callback = this;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    clickedLogin = true;
                    clickedCreate = false;
                    UserDatabaseHandler.getUser(firebaseUser.getUid(), callback);
                }
            }
    );

    private final ActivityResultLauncher<Intent> createAccountLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    clickedLogin = false;
                    clickedCreate = true;
                    Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                    startActivity(intent);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context = this;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            UserDatabaseHandler.getUser(user.getUid(), this);
        }

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Choose authentication providers
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.GoogleBuilder().build());

                // Create and launch sign-in intent
                Intent signInIntent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build();
                signInLauncher.launch(signInIntent);
            }
        });

        Button createAccountButton = findViewById(R.id.create_account_button);
        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Choose authentication providers
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.GoogleBuilder().build());

                // Create and launch create account intent
                Intent createAccountIntent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build();
                createAccountLauncher.launch(createAccountIntent);
            }
        });
    }


    @Override
    public void dataReturned(List<Book> books) {

    }

    @Override
    public void userReturned(User user) {
        if (clickedLogin) {
            if (user != null) {
                Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        } else if (clickedCreate){
            if (user == null) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);

            } else{
                Toast toast = Toast.makeText(context,"Account already exists", Toast.LENGTH_SHORT);
                toast.show();
                Intent sendToUserHomeIntent = new Intent(LoginActivity.this, UserHomeActivity.class);
                sendToUserHomeIntent.putExtra("user", user);
                startActivity(sendToUserHomeIntent);
            }
        } else {
            if (user != null) {
                Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            } else {
                FirebaseAuth.getInstance().signOut();
            }
        }
    }
}