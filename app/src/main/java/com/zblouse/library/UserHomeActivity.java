package com.zblouse.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userhome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user_home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        User user = getIntent().getSerializableExtra("user",User.class);
        if(user == null){
            Intent sendToLoginIntent = new Intent(UserHomeActivity.this, LoginActivity.class);
            startActivity(sendToLoginIntent);
        }

        drawerLayout = findViewById(R.id.user_home);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView headerTextView = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_header_title);
        headerTextView.setText(user.getName());
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment()).commit();
        drawerLayout.open();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int selectedItem = item.getItemId();
        if(selectedItem == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            Intent returnToLoginIntent = new Intent(UserHomeActivity.this,LoginActivity.class);
            startActivity(returnToLoginIntent);
        } else if(selectedItem == R.id.nav_addBook){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddBookFragment()).commit();

        } else if(selectedItem == R.id.nav_home){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment()).commit();
        }else {
            System.out.println("Selected Item: " + selectedItem);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
