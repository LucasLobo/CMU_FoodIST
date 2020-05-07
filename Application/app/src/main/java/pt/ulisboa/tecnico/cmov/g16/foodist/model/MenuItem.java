package pt.ulisboa.tecnico.cmov.g16.foodist.model;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.LinkedList;

public class MenuItem implements Serializable { //fixme not necessary with current approach

    private String name;
    private double price;
    private boolean availability;
    private int discount;
    private String description;
    private transient LinkedList<ImageView> images;
    private FoodType foodType;


    public MenuItem(String name, double price, FoodType foodType, boolean availability, String description, int discount, LinkedList<ImageView> images) {
        this.name = name;
        this.foodType = foodType;
        this.availability = availability;
        this.price = price;
        this.description = description;
        setDiscount(discount);
        this.images = images;
    }

    // * GETTERS * //
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public LinkedList<ImageView> getImages() {
        return images;
    }

    public boolean getAvailability(){
        return availability;
    }


    // * SETTERS * //

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
        discount = 0;
    }

    public void setDiscount(int discount) {
        if(discount >= 0 && discount < 100) {
            this.discount = discount;
            price = price * (1 - (discount*0.01));
        }else
            this.discount = 0;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String toString(){
        return ("Food: " + foodType + "\n" + "Price: " + price + " with" + " discount: " + discount + "\n" + description);
    }
}
