package com.example.apartmenttourschedular;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.example.apartmenttourschedular.model.Apartment;
import com.example.apartmenttourschedular.model.Appointment;
import com.example.apartmenttourschedular.util.DataGenerator;

public class PropertySelectionActivity extends AppCompatActivity {
    private Appointment appointment;
    List<String> availableAparments;
    ListView listPropertyItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_selection);
        listPropertyItems = findViewById(R.id.listPropertyItems);
        setTitle(R.string.titleSelectApartment);

        Serializable intentData = getIntent().getSerializableExtra("appointment");
        if(intentData != null && intentData instanceof Appointment){
            appointment = (Appointment) intentData;
        } else{
            appointment = new Appointment();
        }

        availableAparments = filterDataForSelection(DataGenerator.getApartments());
        initiateAparmentList();
        addListeners();
    }

    private void initiateAparmentList() {
        if (availableAparments.size() == 0) {
            availableAparments.add(String.valueOf( R.string.NoApartmentAvailable ));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( getApplicationContext(), android.R.layout.simple_list_item_1, availableAparments ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView( position, convertView, parent );
                TextView textView = view.findViewById( android.R.id.text1 );
                textView.setTextColor( Color.rgb( 0, 32, 96 ) );
                textView.setTextSize( TypedValue.COMPLEX_UNIT_SP, 22 );
                textView.setGravity( Gravity.CENTER );
                return view;
            }
        };

        listPropertyItems.setAdapter( arrayAdapter );
    }

    private List<String> filterDataForSelection(List<Apartment> apartments) {
        List<String> availableApartments = new ArrayList<>();
        for(Apartment apt : apartments){
            if(apt.isAvailable() && apt.getApartmentType().equalsIgnoreCase(appointment.getApartmentType())){
                availableApartments.add(apt.getName());
            }
        }
        return availableApartments;
    }

    private void addListeners() {
        listPropertyItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AppointmentActivity.class);
                appointment.setPropertyName(availableAparments.get(i));
                intent.putExtra("appointment", appointment);
                startActivity(intent);
            }
        });
    }

}
