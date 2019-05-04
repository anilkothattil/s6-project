package com.example.expensetracker;

import android.content.Intent;
import android.database.Cursor;
import android.telephony.SmsMessage;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.util.Log;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    DatabaseHandler myDb;
    int selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);

        Log.i("DB","Create Main");
        myDb = new DatabaseHandler(this);
        selected=-1;
        readRecords();
    }
    public void readRecords() {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getApplicationContext(),registration.class));
            finish();
        }

        ArrayList<String> spinnerArray = new ArrayList<String>();
        while (res.moveToNext()) {
            spinnerArray.add(res.getString(1));
        }
//        Toast.makeText(this, spinnerArray.toString(), Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setVisibility(View.VISIBLE);spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long arg3)
            {
                String city = "The bank is " + parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), city, Toast.LENGTH_LONG).show();
                selected = position;
            }

            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub
            }
        });
    }

    public void changePage(View view) {
        String city = "The bank is " + spinner.getItemAtPosition(selected).toString();
        Toast.makeText(this, city, Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),activity_screen2.class));
    }

    public void registerbank(View view) {
        startActivity(new Intent(getApplicationContext(),registration.class));
        finish();
    }

    public void showExpense(View view) {
        startActivity(new Intent(getApplicationContext(),ExpenseActivity.class));
        finish();
    }

//    public void onResume(){
//        Log.i("cs.fsu", "smsActivity : onResume");
//        super.onResume();
//        setContentView(R.layout.activity_main);
//
//        Intent intent = getIntent();
//        Bundle bundle = intent.getBundleExtra("mySMS");
//
//        if (bundle != null) {
//            Object[] pdus = (Object[])bundle.get("pdus");
//            SmsMessage sms = SmsMessage.createFromPdu((byte[])pdus[0]);
//            Log.i("SMS", "smsActivity : SMS is <" +  sms.getMessageBody() +">");
//
//            //strip flag
//            String message = sms.getMessageBody();
//            while (message.contains("FLAG"))
//                message = message.replace("FLAG", "");
//
//            TextView tx = (TextView) findViewById(R.id.txtSMS);
//            tx.setText(message);
//        } else
//            Log.i("cs.fsu", "smsActivity : NULL SMS bundle");
//    }

}
