package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private DatabaseHelper databaseHelper;
    private ArrayList<Photograph> photographs;
    private PhotographAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        photographs = new ArrayList<>();
        adapter = new PhotographAdapter(this, photographs);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Button captureButton = findViewById(R.id.captureButton);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhotograph();
            }
        });

        loadPhotographs();
    }

    private void dispatchTakePictureIntent() {
        // Implementa la lógica para capturar una imagen utilizando la cámara del dispositivo
    }

    private void savePhotograph() {
        // Implementa la lógica para guardar la fotografía en la base de datos SQLite
    }

    private void loadPhotographs() {
        // Implementa la lógica para cargar las fotografías desde la base de datos y actualizar el adaptador
    }
}
