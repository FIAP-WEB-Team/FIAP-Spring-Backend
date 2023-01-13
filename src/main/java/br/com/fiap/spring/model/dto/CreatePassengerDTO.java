package br.com.fiap.spring.model.dto;

public class CreatePassengerDTO {
    public String BirthDate;
    public String FirstName;
    public String LastName;
    public String Gender;
    public String Nationality;

    public PassengerDTO toPassengerDTO() {
        PassengerDTO temp = new PassengerDTO();
        temp.BirthDate = BirthDate;
        temp.FirstName = FirstName;
        temp.LastName = LastName;
        temp.Gender = Gender;
        temp.Nationality = Nationality;
        return temp;
    }
}
