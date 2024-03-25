package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Photograph photograph = new Photograph(imageBitmap, "");
            photographs.add(photograph);
            adapter.notifyDataSetChanged();
        }
    }

    private void savePhotograph() {
        for (Photograph photograph : photographs) {
            byte[] imageBytes = getBytesFromBitmap(photograph.getImage());
            databaseHelper.insertPhotograph(imageBytes, photograph.getDescription());
        }
        Toast.makeText(this, "Fotografías guardadas exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void loadPhotographs() {
        ArrayList<Photograph> savedPhotographs = databaseHelper.getAllPhotographs();
        photographs.clear();
        photographs.addAll(savedPhotographs);
        adapter.notifyDataSetChanged();
    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
