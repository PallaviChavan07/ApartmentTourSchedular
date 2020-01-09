package com.example.apartmenttourschedular;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.io.Serializable;
import com.example.apartmenttourschedular.model.Appointment;
import com.example.apartmenttourschedular.util.DateTimeUtil;

public class LastDetailsActivity extends AppCompatActivity {
    Appointment appointment;
    TextView txtDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_details);
        setTitle(R.string.titleThankYou);
        txtDetails = findViewById(R.id.textDetails);

        Serializable intentData = getIntent().getSerializableExtra("appointment");
        if(intentData != null && intentData instanceof Appointment){
            appointment = (Appointment) intentData;
        } else{
            appointment = new Appointment();
        }

        txtDetails.setText(prepareDetailsMessage(appointment));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private String prepareDetailsMessage(Appointment appointment) {
        return getString( R.string.Success ) + "...\n\n\n" + getString( R.string.Name ) + appointment.getFirstName() + " " + appointment.getLastName() + "\n" +
                getString( R.string.Email ) + appointment.getEmail() + "\n" +
                getString(R.string.Phone) + appointment.getPhoneNumber() + "\n\n" +
                getString(R.string.Visit) + appointment.getPropertyName() + "\n" +
                getString( R.string.Type ) + appointment.getApartmentType() + "\n" +
                getString( R.string.Time ) + DateTimeUtil.convertDateToString( appointment.getDate() ) + "\n";
    }
}
