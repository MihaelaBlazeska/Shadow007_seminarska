package com.example.shadow007;

public class User {



        String Uid;
        String type;
        String name;
        String surname;
        String phone;
        String email;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }
        public User(String Uid, String name, String surname, String phone, String email,String type) {
            this.type=type;
            this.Uid=Uid;
            this.name=name;
            this.surname=surname;
            this.phone=phone;
            this.email=email;

        }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
            return Uid;
        }

        public void setUid(String uid) {
            Uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }
