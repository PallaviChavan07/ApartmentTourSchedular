package com.example.apartmenttourschedular;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import com.example.apartmenttourschedular.model.Appointment;
import com.example.apartmenttourschedular.util.DateTimeUtil;

public class AppointmentActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    TextView txtDateTimeInput;
    TextView txtPropertyInput;
    Appointment appointment;
    DatePickerDialog.OnDateSetListener pickDateListner;
    RadioGroup.OnCheckedChangeListener radioCheckedChangeListener;
    private Button continueAptButton;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        setTitle(R.string.titleBookAppoint);

        Serializable intentData = getIntent().getSerializableExtra("appointment");

        appointment = new Appointment();

        radioGroup = findViewById(R.id.FloorPlanRadioGroup);
        txtDateTimeInput = findViewById(R.id.txtDateTimeInput);
        txtPropertyInput = findViewById(R.id.txtPropertyInput);
        continueAptButton = findViewById( R.id.continue_apt_button);

        if(intentData != null && intentData instanceof Appointment){
            appointment = (Appointment) intentData;
            updateComponentValues();

        } else{
            appointment = new Appointment();
            RadioButton btn = getCheckedRadioButton();
            if(btn != null) {
                appointment.setApartmentType(btn.getText().toString());
            }

        }
        addListeners();

    }


    private void addListeners() {

        final Calendar minDateTimeCal = Calendar.getInstance();

        final TimePickerDialog.OnTimeSetListener pickTimeListner = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay,
                                  int minute) {
                mHour = hourOfDay ;
                mMinute = minute;

                String dateTime = String.format("%02d", mDay) + "-";
                dateTime += String.format("%02d", mMonth) + "-";
                dateTime += mYear;
                dateTime += "   " + String.format("%02d", mHour) + ":";
                dateTime += String.format("%02d", mMinute);

                Date selectedDate = DateTimeUtil.convertStringToDate(dateTime, true);

                if(minDateTimeCal.getTime().after(selectedDate)){
                    Toast.makeText(AppointmentActivity.this, R.string.futureDateTimeValidateMsg , Toast.LENGTH_LONG).show();
                } else {
                    txtDateTimeInput.setText(DateTimeUtil.convertDateToString(selectedDate));
                    updateTextBoxProperties(txtDateTimeInput);
                    appointment.setDate(selectedDate);
                }
            }
        };

        txtDateTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateAndGetCalendarMinDateTime(minDateTimeCal);

                if(StringUtils.isBlank(txtDateTimeInput.getText().toString())){
                    updateCurrentDateTimeFields(minDateTimeCal);
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(AppointmentActivity.this,
                        pickDateListner  , mYear, mMonth - 1, mDay);
                datePickerDialog.getDatePicker().setMinDate(minDateTimeCal.getTimeInMillis());
                datePickerDialog.show();
            }
        });


        pickDateListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                mYear = year;
                mMonth = monthOfYear + 1;
                mDay = dayOfMonth;


                TimePickerDialog timePickerDialog = new TimePickerDialog(AppointmentActivity.this, pickTimeListner
                        , mHour, mMinute, false);

                timePickerDialog.show();

            }
        };


        txtPropertyInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PropertySelectionActivity.class);
                intent.putExtra("appointment", appointment);

                startActivity(intent);
            }
        });

        radioCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                modifyFloorSelection();
            }
        };

        radioGroup.setOnCheckedChangeListener(radioCheckedChangeListener);

        continueAptButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(StringUtils.isBlank(txtDateTimeInput.getText().toString())){
                    Toast.makeText(AppointmentActivity.this, R.string.dateTimeValidateMsg, Toast.LENGTH_LONG).show();
                }else if(StringUtils.isBlank(txtPropertyInput.getText().toString())){
                    Toast.makeText(AppointmentActivity.this, R.string.unitValidateMsg, Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), ConfirmationActivity.class);
                    intent.putExtra("appointment", appointment);
                    startActivity(intent);
                }

            }
        } );
    }

    private void updateAndGetCalendarMinDateTime(Calendar minDateTimeCal){
        minDateTimeCal.setTimeInMillis( System.currentTimeMillis());
        DateTimeUtil.convertToQuarterlyFormat(minDateTimeCal);
        minDateTimeCal.add( Calendar.HOUR_OF_DAY, 2);
        minDateTimeCal.set( Calendar.SECOND, 0);
        minDateTimeCal.set( Calendar.MILLISECOND, 0);
    }

    private void updateCurrentDateTimeFields(Calendar minDateTimeCal){
        mYear = minDateTimeCal.get( Calendar.YEAR);
        mMonth = minDateTimeCal.get( Calendar.MONTH);
        mDay = minDateTimeCal.get( Calendar.DAY_OF_MONTH);
        mHour = minDateTimeCal.get( Calendar.HOUR_OF_DAY);
        mMinute = minDateTimeCal.get( Calendar.MINUTE);
    }

    private void modifyFloorSelection(){
        int radioButtonId = radioGroup.getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(radioButtonId);
        appointment.setApartmentType(radioButton.getText().toString());
        txtPropertyInput.setText("");
    }

    private void updateComponentValues() {
        RadioButton selectedBtn = getRadioButtonByText(appointment.getApartmentType());
        if(selectedBtn != null){
            selectedBtn.setChecked(true);
        }

        if(appointment.getDate() != null){
            txtDateTimeInput.setText(DateTimeUtil.convertDateToString(appointment.getDate()));
        }

        txtPropertyInput.setText(appointment.getPropertyName());
        updateTextBoxProperties(txtDateTimeInput, txtPropertyInput);
    }


    private RadioButton getRadioButtonByText(String text){
        for(int i = 0; i < radioGroup.getChildCount(); i++){
            RadioButton btn = (RadioButton) radioGroup.getChildAt(i);
            if(btn.getText().toString().equalsIgnoreCase(text)){
               return btn;
            }
        }

        return null;
    }

    private RadioButton getCheckedRadioButton(){
        for(int i = 0; i < radioGroup.getChildCount(); i++){
            RadioButton btn = (RadioButton) radioGroup.getChildAt(i);
            if(btn.isChecked()){
                return btn;
            }
        }
        return null;
    }

    private void updateTextBoxProperties(TextView... textViews){

        for(TextView textView : textViews){
            textView.setTextColor( Color.rgb(0,0,0));
        }
    }

}
