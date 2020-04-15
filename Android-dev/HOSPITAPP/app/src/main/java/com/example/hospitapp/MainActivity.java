package com.example.hospitapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private FloatingActionButton fabMaterial;
    private FloatingActionButton fabHelp;
    private Button proveedoresButton;

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        mContext = getApplicationContext();

        openAdd();
        openMaterial();
        openHelp();

        //openProveedores();

    }

    public void openAdd() {

        fabAdd = findViewById(R.id.addFabButton);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogAdd();
            }
        });
    }

    public void onDialogAdd() {
        AddDialog addDialog = new AddDialog();
        addDialog.show(getSupportFragmentManager(), "Add Dialog");
    }

    public void openMaterial(){
        fabMaterial = findViewById(R.id.addMaterialButton);
        fabMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogMaterials();
            }
        });
    }

    public void onDialogMaterials() {
        MaterialsDialog materalsDialog = new MaterialsDialog();
        materalsDialog.show(getSupportFragmentManager(), "Add Materials");
    }

    public void openHelp() {
        fabHelp = findViewById(R.id.helpFabButton);
        fabHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openHelp = new Intent(getContext(), HelpActivity.class);
                startActivity(openHelp);
            }
        });
    }

    public static Context getContext(){
        return mContext;
    }

    public void openProveedores() {

        //proveedoresButton = findViewById(R.id.buttonProveedor);
        proveedoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoPedidosDialog newDialog = new InfoPedidosDialog();
                newDialog.show(getSupportFragmentManager(), "Elegir Proveedor");
            }
        });
    }

    public void finalizeMain() {
        finish();
    }








}