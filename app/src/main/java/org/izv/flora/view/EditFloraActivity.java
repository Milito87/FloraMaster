package org.izv.flora.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.izv.flora.MainActivity;
import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;

import org.izv.flora.viewmodel.DeleteFloraViewModel;
import org.izv.flora.viewmodel.DeleteImgViewModel;
import org.izv.flora.viewmodel.EditFloraViewModel;
import org.izv.flora.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

public class EditFloraActivity extends AppCompatActivity {

    private EditText etNombre, etFamily, etHabitat, etAltitud;
    private Button btEdit, btDelete, btClean;
    private MainActivityViewModel mavm;
    private int pos;
    private AlertDialog.Builder builder;
    private EditFloraViewModel evm;
    private DeleteImgViewModel divm;
    private DeleteFloraViewModel dvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flora);
        initialize();

        //guardar cambios
        btEdit.setOnClickListener(v -> {
            builder  = new AlertDialog.Builder(v.getContext());
            builder.setTitle(etNombre.getText()).setMessage("Do you want add imagen?")
                    .setNegativeButton("NO", (dialog, which) -> {
                        evm.editFlora(MainActivity.idFlora,getFlora());
                        startActivity(new Intent(EditFloraActivity.this, MainActivity.class));
                    })
                    .setPositiveButton( "YES", (dialog, which) -> {
                        getFlora();
            evm.editFlora(MainActivity.idFlora,getFlora());
            Intent i = new Intent(EditFloraActivity.this,  AddImagenActivity.class);
            MainActivity.modo=2;
            MainActivity.nombre = etNombre.getText().toString();
            startActivity(i);}).show();
        });

        //limpiar campos
        btClean.setOnClickListener(v -> {
            etFamily.setText("");
            etHabitat.setText("");
            etAltitud.setText("");
            Toast.makeText(this, "FIELDS CLEANED", Toast.LENGTH_SHORT).show();
        });

        //borrar flora
        btDelete.setOnClickListener(v -> {
            builder  = new AlertDialog.Builder(v.getContext());
            builder.setTitle(etNombre.getText()).setMessage("Do you want delete?")
                    .setNegativeButton("NO", (dialog, which) -> {

                    })
                    .setPositiveButton( "Yes", (dialog, which) -> {
                        dvm.deleteFlora(MainActivity.idFlora);
                        startActivity(new Intent(EditFloraActivity.this, MainActivity.class));
                    }).show();
        });

    }

    private void initialize() {

        etNombre = findViewById(R.id.etNameEdit);
        etFamily = findViewById(R.id.etFamilyEdit);
        etHabitat = findViewById(R.id.etHabitatEdit);
        etAltitud = findViewById(R.id.etAltitudeEdit);

        mavm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        MutableLiveData<ArrayList<Flora>> floraList = mavm.getFloraLiveData();
        mavm.getFlora();

        floraList.observe(this,  floras -> {
            //busco la flora a cargar en pantalla
            for(int i=0;i < floras.size();i++) {
                if(floras.get(i).getId() == MainActivity.idFlora) {
                    pos=i;
                    etNombre.setText(floras.get(pos).getNombre());
                    etFamily.setText(floras.get(pos).getFamilia());
                    etHabitat.setText(floras.get(pos).getHabitat());
                    etFamily.setText(floras.get(pos).getAltitud());
                }
            }

        });

        evm = new ViewModelProvider(this).get(EditFloraViewModel.class);
        evm.getEditFloraLiveData().observe(this, aLong -> {
            Log.v("xyzyx", "respuesta " + aLong);
            if(aLong > 0) {
                finish();
            }
        });

        btEdit = findViewById(R.id.btChangeEdit);
        btClean = findViewById(R.id.btCleanEdit);
        btDelete = findViewById(R.id.btDeleteEdit);

        divm = new ViewModelProvider(this).get(DeleteImgViewModel.class);
        divm.getDeleteImgLiveData().observe(this, aLong -> {
            Log.v("xyzyx", "respuesta " + aLong);
            if(aLong > 0) {
                finish();
            }
        });
        dvm = new ViewModelProvider(this).get(DeleteFloraViewModel.class);
        dvm.getDeleteFloraLiveData().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                Log.v("xyzyx", "respuesta " + aLong);
                if(aLong > 0) {
                    finish();
                }
            }
        });
    }

    private Flora getFlora(){
        Flora flora = new Flora();
        flora.setNombre(etNombre.getText().toString());
        return flora;
    }

}