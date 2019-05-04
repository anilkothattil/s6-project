package com.example.expensetracker;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {

    DatabaseHandler myDb;
    TextView txtDisp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        myDb = new DatabaseHandler(this);
        txtDisp = findViewById(R.id.txtDisp);
        Cursor res = myDb.getAllExpenses();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();

        }else {
            while (res.moveToNext()) {
                txtDisp.setText(txtDisp.getText()+res.getString(1)+"\n---------------------\n");
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
