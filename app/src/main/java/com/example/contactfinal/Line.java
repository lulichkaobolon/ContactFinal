package com.example.contactfinal;

class Line {
    public static final String keyName = "name", keyPhone = "phone", keyImage = "image";
    String name, phone;
    int id;
    Line (String name, String phone, int id) {
        class ExtraInfo {
            String email, date, gender, city;
        }
        this.name = name;
        this.phone = phone;
        this.id = id;
    }
}
