package com.soboleoko.medicalclinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data

//brak getterów, setterów oraz konstruktora, String zamiast long, modyfikatory dostępu
//jeżeli chcemy otrzymać indywidualne wartości to nie używamy beana
public class Patient {

    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;

}

