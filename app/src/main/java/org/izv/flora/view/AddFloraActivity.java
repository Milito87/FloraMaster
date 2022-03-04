package org.izv.flora.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.izv.flora.MainActivity;
import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.viewmodel.AddFloraViewModel;

public class AddFloraActivity extends AppCompatActivity {

    private EditText etNombre;
    private Button btAdd;
    private AddFloraViewModel avm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flora);
        initialize();

        btAdd.setOnClickListener(v -> {
            if(etNombre.getText().length() > 0 ) {
                Flora flora = new Flora();
                flora.setNombre(etNombre.getText().toString());
                avm.createFlora(flora);
                MainActivity.modo = 1;
                MainActivity.nombre = etNombre.getText().toString();
                Intent i = new Intent(AddFloraActivity.this, AddImagenActivity.class);
                startActivity(i);
            }
            else{
                Toast.makeText(this, "EMPTY NAME, put any name please", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initialize() {

        etNombre = findViewById(R.id.etAddName);

        avm = new ViewModelProvider(this).get(AddFloraViewModel.class);
        avm.getAddFloraLiveData().observe(this, aLong -> {
            if(aLong > 0) {
                finish();
            } else {
                Toast.makeText(AddFloraActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

        btAdd = findViewById(R.id.btAddFlora);

    }
}