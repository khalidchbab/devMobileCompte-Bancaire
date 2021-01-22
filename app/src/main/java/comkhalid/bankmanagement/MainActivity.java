package comkhalid.bankmanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import comkhalid.bankmanagement.models.Operation;
import comkhalid.bankmanagement.models.OperationsController;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    private EditText mAmount;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Double Sold = 0.0;
    private TextView solde_view;

    private Button addButton;

    private ListView transactions;
    private ArrayList<Operation> operations;
    private OperationsController adapter;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UI();
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day
                        );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);

            }
        };

        addButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String amount = mAmount.getText().toString();
                String date = mDisplayDate.getText().toString();
                try {
                    Add(amount,date);
                } catch (DateTimeParseException e) {
                    showNotif("Choose a Date By Clicking");
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void Add(String amount, String date) throws DateTimeParseException {
        boolean ok = !isEmpty(mAmount);
        if(ok){
            Double mnt =  Double.parseDouble(amount);
            if(mnt == 0){
                //Toast.makeText(this, "You Can't Add 0", Toast.LENGTH_SHORT).show();
                showNotif("You Can't Add 0");
                clearForm();
                return;
            }else if(!checkSold(mnt)){
                Log.d(TAG ,"Insufficient Balance");
                showNotif("Insufficient Balance");
                clearForm();
                return;
            }
            Sold += mnt;
            Log.d(TAG ,"SOLD ; " + Sold);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
            LocalDate localdate = LocalDate.parse(date,formatter);;
            Operation op = new Operation(generateNum(),mnt,localdate);
            operations.add(op);
            adapter.notifyDataSetChanged();
            String solde_text = Double.toString(Sold);
            solde_view.setText(solde_text);
            clearForm();
        }else{
            showNotif("Empty Form : Please specify the amount to enter");
        }
    }

    void showNotif(String Message){
        Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();
    }

    Boolean checkSold(Double amount){
        Log.d(TAG,Sold + "  " + amount);
        return Sold >= -1*amount;
    }

    void clearForm(){
        mAmount.setText("");
        mDisplayDate.setText(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    String generateNum(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private void UI() {
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mAmount = (EditText) findViewById(R.id.Amount);
        transactions = findViewById(R.id.transactions);
        solde_view = findViewById(R.id.textView);
        addButton = (Button) findViewById(R.id.addButton);

        operations = new ArrayList<Operation>();
        adapter = new OperationsController(this, R.layout.activity_operations_list,operations);
        transactions.setAdapter(adapter);
    }

}