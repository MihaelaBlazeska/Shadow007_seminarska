package com.example.shadow007;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AddProductActivity extends AppCompatActivity {
    EditText date;
    String uid;
    String brand;
    DatePickerDialog datePickerDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private List<Product> Products;
    private Spinner spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

    databaseReference = FirebaseDatabase.getInstance().getReference("Product");



        Toolbar mytoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



    spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            brand = spinner.getSelectedItem().toString();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    });




}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favourite:
                Toast.makeText(getApplicationContext(), "You chose favourite", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.settings:
                Toast.makeText(getApplicationContext(), "You chose settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                logout();
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




    public void stavibaza(View view) {



        EditText modelt = findViewById(R.id.model);
        String model = modelt.getText().toString();

        EditText desct = findViewById(R.id.desc);
        String desc = desct.getText().toString();

        int dis=0;

        RadioGroup rg=findViewById(R.id.radio);
        int radbid=rg.getCheckedRadioButtonId();
        View rb=rg.findViewById(radbid);
        int ind=rg.indexOfChild(rb);
        RadioButton r=(RadioButton) rg.getChildAt(ind);
        String gender=r.getText().toString();

        EditText quantityt = findViewById(R.id.quantity);
        int quantity= Integer.parseInt(quantityt.getText().toString());
        EditText pricet = findViewById(R.id.price);
        int price= Integer.parseInt(pricet.getText().toString());
        EditText imgt=findViewById(R.id.image);
        String image=imgt.getText().toString();



        Product product = new Product(model,quantity,price,dis,gender,brand,desc,image);


        databaseReference.push().setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText( getApplicationContext(), "You've successfully added new Product", Toast.LENGTH_SHORT).show();
                pricet.setText("");
                quantityt.setText("");
                rg.clearCheck();
                desct.setText("");
                spinner.setSelection(0);
                modelt.setText("");
                imgt.setText("");

            }
        });


    }
    public void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);

// I'm using fragment here so I'm using getView() to provide ViewGroup
// but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(AddProductActivity.this).inflate(R.layout.logout,null);
// Set up the input


        builder.setView(viewInflated);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Bye",Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent2);


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // cancel button
            }
        });


        builder.show();

    }
}