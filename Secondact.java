package com.example.platewise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Secondact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondact);

        BottomNavigationView btmnavi = findViewById(R.id.Btmnavi);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment existingFragment = getSupportFragmentManager().findFragmentByTag("TIPS_FRAGMENT");




        btmnavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                if(id==R.id.nav_home) {
                        loadFrag(new HomeFragment(), false);
                }
                else if (id==R.id.tips) {
                    loadFrag(new TipsFragment(),false);
                }
                else{

                        loadFrag(new ContactFragment(),false);
                        }


                return true;
            }
        });
        btmnavi.setSelectedItemId(R.id.nav_home);

            }
            public void loadFrag(Fragment fragment,boolean flag){
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                if(flag){
                    ft.add(R.id.container,fragment);
                }else
                    ft.replace(R.id.container,fragment);

//                ft.add(R.id.container, fragment);
                ft.commit();

            }
        }

