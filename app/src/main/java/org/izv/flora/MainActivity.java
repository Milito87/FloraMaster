package org.izv.flora;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.izv.flora.view.AddFloraActivity;

import org.izv.flora.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private FloraAdapter floraAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private Intent i;
    public static long idFlora;
    public static int modo;
    public static String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        fab.setOnClickListener(v -> {

            startActivity(new Intent(MainActivity.this, AddFloraActivity.class));

        });

    }

    private void initialize() {

        fab = findViewById(R.id.fab);

        recyclerView = findViewById(R.id.recyclerFlora);
        recyclerView .setLayoutManager(new LinearLayoutManager(this));
        floraAdapter = new FloraAdapter(this);

        modo = 0;
        nombre = "";

        MainActivityViewModel mavm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mavm.getFlora();

        mavm.getFloraLiveData().observe(this, floraPlural -> {
            Log.v("xyzyx", floraPlural.toString());
            floraAdapter.setFloraList(mavm.getFloraLiveData().getValue());
            recyclerView.setAdapter(floraAdapter);

        });
        mavm.getFlora();
    }

}