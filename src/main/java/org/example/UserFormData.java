package org.example;

public class UserFormData {
    public final String name;
    public final String email;
    public final String currentAddress;
    public final String permanentAddress;

    public UserFormData(String name, String email, String currentAddress, String permanentAddress) {
        this.name = name;
        this.email = email;
        this.currentAddress = currentAddress;
        this.permanentAddress = permanentAddress;
    }
}
