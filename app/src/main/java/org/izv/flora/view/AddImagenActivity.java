package org.izv.flora.view;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.izv.flora.MainActivity;
import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.model.entity.Imagen;
import org.izv.flora.viewmodel.AddFloraViewModel;
import org.izv.flora.viewmodel.AddImagenViewModel;
import org.izv.flora.viewmodel.MainActivityViewModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddImagenActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launcher;
    private Intent resultadoImagen = null;
    private EditText etNombre, etDescripcion;
    private TextView tvIdFlora;
    private MainActivityViewModel mavm;
    private AddImagenViewModel aivm;
    private Button btImg, btAdd;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_imagen);
        initialize();

        btImg.setOnClickListener(v -> {
            selectImage();
        });

        btAdd.setOnClickListener(v -> {
            uploadDataImage();
            startActivity(new Intent(AddImagenActivity.this, MainActivity.class));
        });

    }

    private void initialize() {
        launcher = getLauncher();
        etDescripcion = findViewById(R.id.etDescrFlora);
        etNombre = findViewById(R.id.etNombreFlora);
        tvIdFlora = findViewById(R.id.tvIdAdd);
        btImg = findViewById(R.id.btSelectImg);
        id = -1;

        btAdd= findViewById(R.id.btAddImg);

        aivm = new ViewModelProvider(this).get(AddImagenViewModel.class);
        mavm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        MutableLiveData<ArrayList<Flora>> floraList = mavm.getFloraLiveData();
        mavm.getFlora();

        floraList.observe(this,  floras -> {
            //si vengo de añadir flora
            if(MainActivity.modo == 1) {
                //asignamos nombre e id de la flora
                tvIdFlora.setText(MainActivity.nombre);
                id = floras.get(mavm.getFloraLiveData().getValue().size()-1).getId();
            }
            //si vengo de editar flora
            if(MainActivity.modo == 2) {
                //asignamos nombre e id de la flora
                tvIdFlora.setText(MainActivity.nombre);
                id = MainActivity.idFlora;
            }
        });


    }

    private void uploadDataImage() {
        String nombre = etNombre.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        if(!(nombre.trim().isEmpty() || id < 0 || resultadoImagen == null)) {

            Imagen imagen = new Imagen();
            imagen.name = nombre;
            imagen.descr = descripcion;
            imagen.idflora = id;

            aivm.saveImagen(resultadoImagen, imagen);
        }
    }

    ActivityResultLauncher<Intent> getLauncher() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            //respuesta al resultado de haber seleccionado una imagen
            if(result.getResultCode() == Activity.RESULT_OK) {
                resultadoImagen = result.getData();
            }
        });
    }

    Intent getContentIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        return intent;
    }

    void selectImage() {
        Intent intent = getContentIntent();
        launcher.launch(intent);
    }
}