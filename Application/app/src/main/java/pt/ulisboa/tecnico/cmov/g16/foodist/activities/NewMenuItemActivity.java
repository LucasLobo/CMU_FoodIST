package pt.ulisboa.tecnico.cmov.g16.foodist.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

import pt.ulisboa.tecnico.cmov.g16.foodist.Data;
import pt.ulisboa.tecnico.cmov.g16.foodist.R;
import pt.ulisboa.tecnico.cmov.g16.foodist.model.MenuItem;
import pt.ulisboa.tecnico.cmov.g16.foodist.model.TypeOfFood;

public class NewMenuItemActivity extends AppCompatActivity {

    Data data;
    Spinner typeOfFood;
    private LinearLayout imageLayout;
    private LinkedList<ImageView> images = new LinkedList<>();

    private static final int PICK_IMAGE = 100;
    private int foodServiceIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_menu_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        foodServiceIndex = intent.getIntExtra("foodServiceIndex", -1);

        data = (Data) getApplicationContext();

        typeOfFood = findViewById(R.id.foodType);
        setupSpinner();
        imageLayout = findViewById(R.id.imageSlots);

        Button importButton = findViewById(R.id.importImageButton);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImageFromGallery();
            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText title = findViewById(R.id.title);
                EditText price = findViewById(R.id.price);
                EditText description = findViewById(R.id.description);
                EditText discount = findViewById(R.id.discount);

                try {

                    MenuItem item = new MenuItem(title.getText().toString(),
                            Double.parseDouble(price.getText().toString()),
                            TypeOfFood.valueOf(typeOfFood.getSelectedItem().toString().toUpperCase()),
                            true, description.getText().toString(),
                            Integer.parseInt(discount.getText().toString()), images);
                    data.getFoodService(foodServiceIndex).getMenu().getMenuList().add(item);
                    Toast.makeText(getApplicationContext(), title.getText().toString() + " created successfully!", Toast.LENGTH_LONG).show();
                    finish();
                }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Please setup a valid price", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button cancelButton = findViewById(R.id.cancelButton);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
    }

    private void setupSpinner() {
        ArrayAdapter<TypeOfFood> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, TypeOfFood.values());
        typeOfFood.setAdapter(adapter);
    }

    private void chooseImageFromGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if (data != null) {
                ImageView imageView = new ImageView(this);
                imageView.setImageURI(data.getData());
                images.add(imageView);
                imageView.setVisibility(View.VISIBLE);
                imageLayout.addView(imageView);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}