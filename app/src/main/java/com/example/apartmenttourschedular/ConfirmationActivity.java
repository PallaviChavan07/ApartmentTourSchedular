package com.example.apartmenttourschedular;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;
import com.example.apartmenttourschedular.model.Appointment;

public class ConfirmationActivity extends AppCompatActivity {
    Appointment appointment;
    EditText txtFirstName;
    EditText txtLastName;
    EditText txtEmail;
    EditText txtPhone;
    Button confirm_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        setTitle(R.string.titleConfirmAppoint);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);

        Serializable intentData = getIntent().getSerializableExtra("appointment");
        if(intentData != null && intentData instanceof Appointment){
            appointment = (Appointment) intentData;
        } else{
            appointment = new Appointment();
        }

        confirm_button = (Button)findViewById( R.id.btnConfirm);
        confirm_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtils.isBlank(txtFirstName.getText().toString())){
                    Toast.makeText(ConfirmationActivity.this, R.string.firstNameValidateMsg,
                            Toast.LENGTH_LONG).show();
                }else if(StringUtils.isBlank(txtLastName.getText().toString())){
                    Toast.makeText(ConfirmationActivity.this, R.string.lastNameValidateMsg,
                            Toast.LENGTH_LONG).show();
                }else if(StringUtils.isBlank(txtEmail.getText().toString())){
                    Toast.makeText(ConfirmationActivity.this, R.string.emailValidateMsg,
                            Toast.LENGTH_LONG).show();
                }else if(StringUtils.isBlank(txtPhone.getText().toString())) {
                    Toast.makeText( ConfirmationActivity.this, R.string.contactValidateMsg,
                            Toast.LENGTH_LONG ).show();
                }
                else {
                    appointment.setFirstName(txtFirstName.getText().toString());
                    appointment.setLastName(txtLastName.getText().toString());
                    appointment.setEmail(txtEmail.getText().toString());
                    appointment.setPhoneNumber(txtPhone.getText().toString());
                    Toast.makeText(ConfirmationActivity.this, R.string.appointmentConfirmMsg,
                    Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), LastDetailsActivity.class);
                    intent.putExtra("appointment", appointment);
                    startActivity(intent);
                }

            }
        } );
    }
}
