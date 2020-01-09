package com.example.apartmenttourschedular.model;

public class Apartment {

   /* public enum ApartmentType{
        STUDIO("Studio"),
        ONE_BEDROOM("1 Bedroom"),
        TWO_BEDROOMS("2 Bedrooms"),
        THREE_BEDROOMS("3 Bedrooms");

        private String name;

        ApartmentType(String type) {
            this.name = type;
        }

        public static ApartmentType getValueFromName(String pName){
            for(ApartmentType type : ApartmentType.values()){
                if(type.name.equalsIgnoreCase(pName)){
                    return type;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return name;
        }
    }*/

    String name;
    String apartmentType;
    boolean isAvailable;

    public Apartment(String name, String apartmentType, boolean isAvailable) {
        this.name = name;
        this.apartmentType = apartmentType;
        this.isAvailable = isAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(String apartmentType) {
        this.apartmentType = apartmentType;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "name='" + name + '\'' +
                ", apartmentType='" + apartmentType + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
