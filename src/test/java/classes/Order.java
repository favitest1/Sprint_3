package classes;

import java.util.List;

public class Order1 {

    String firstName;
    String lastName;
    String address;
    int metroStation;
    String phone;
    int rentTime;
    String deliveryDate;
    String comment;
    List <String> color;

    public Order1(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
}
