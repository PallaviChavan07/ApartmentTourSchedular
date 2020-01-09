package com.example.apartmenttourschedular;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.util.Arrays;
import com.example.apartmenttourschedular.util.DataGenerator;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.titleWelcome);

        Button schedule_tour = (Button) findViewById( R.id.schedule_button );
        schedule_tour.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateStaticData();
                //start Appointment activity
                Intent intent = new Intent(getApplicationContext(), AppointmentActivity.class);
                startActivity(intent);

            }
        } );
    }

    private void generateStaticData(){
        String[] apartmentTypes = {getString(R.string.aptTypeStudio), getString(R.string.aptTypeOneBed),
                getString(R.string.aptTypeTwoBed), getString(R.string.aptTypeThreeBed)};
        DataGenerator.prepareData( Arrays.asList(apartmentTypes));
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

}
