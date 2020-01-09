package com.example.apartmenttourschedular.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.apartmenttourschedular.model.Apartment;

public class DataGenerator {

    private static List<Apartment> apartments = new ArrayList<>();

    public static void prepareData(List<String> apartmentTypes){
        {

            String[] buildings = {"A", "B", "C"};
            int numberOfFlatsPerBuilding = 16;

            for(String building : buildings ) {
                for(int i=1, j=0; i<=numberOfFlatsPerBuilding; i++, j++){

                    if(j >= apartmentTypes.size()){
                        j=0;
                    }

                    String name = building + "-" + String.format("%02d",i);

                    Apartment apartment = new Apartment(name, apartmentTypes.get(j), true);

                    Log.i("DataGenerator : ", apartment.toString());

                    apartments.add(apartment);

                }
            }


        }
    }

    public static List<Apartment> getApartments(){
        return apartments;
    }

}
