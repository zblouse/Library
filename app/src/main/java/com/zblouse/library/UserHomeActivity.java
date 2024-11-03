package com.zblouse.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Home activity the user is presented with after authenticating with the application. Contains a navigation drawer to switch between different fragments
 */
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

    /**
     * Method that is called when a navigation item is selected in the navigation drawer
     * @param item The selected item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int selectedItem = item.getItemId();
        if(selectedItem == R.id.nav_logout){
            //Logout navigation item has been selected, sign the user out of firebase and return to login activity
            FirebaseAuth.getInstance().signOut();
            Intent returnToLoginIntent = new Intent(UserHomeActivity.this,LoginActivity.class);
            startActivity(returnToLoginIntent);
        } else if(selectedItem == R.id.nav_addBook){
            //Add book navigation item has been selected, switch the active fragment to the AddBookFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddBookFragment()).commit();

        } else if(selectedItem == R.id.nav_home){
            //Home navigation item has been selected, switch the active fragment to the HomeFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment()).commit();
        }else {
            //Should not fall into this case, useful during testing to see Navigation Drawer was working correctly
            //Could be removed, but keeping to make further expansion easier.
            System.out.println("Selected Item: " + selectedItem);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
