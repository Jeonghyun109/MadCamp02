package com.example.test1;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.ui.Data.LoginResponse;
import com.example.test1.ui.RetrofitClient;
import com.example.test1.ui.ServiceApi;
import com.facebook.CallbackManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private ServiceApi service;
    private Context mContext;
    private Button btn_custom_login;
    private Button btn_custom_logout;
    private CallbackManager mCallbackManager;
    String Name;
    String Email;
    static public int id;
    static public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        ((JHJApplication)this.getApplication()).setId(intent.getIntExtra("UserID",0));
        ((JHJApplication)this.getApplication()).setName(intent.getStringExtra("Username"));
        id=((JHJApplication)this.getApplication()).getId();
        name=((JHJApplication)this.getApplication()).getName();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View hView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        intent = getIntent();
        Name = intent.getStringExtra("Username");
        Email = intent.getStringExtra("Useremail");

        TextView textView1 = (TextView) hView.findViewById(R.id.UserName);
        textView1.setText(Name);
        TextView textView2 = (TextView) hView.findViewById(R.id.UserEmail);
        textView2.setText(Email);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_contact, R.id.nav_gallery)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    Toast.makeText(getApplicationContext(),"로그아웃입니다", Toast.LENGTH_SHORT).show();

                    service = RetrofitClient.getClient().create(ServiceApi.class);
                    service.userLogout().enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.e("이거 또 틀렸대",t.getMessage());
                        }
                    });
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}