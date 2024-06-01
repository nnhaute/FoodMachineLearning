package hcmute.edu.vn.foodmachinelearning.model;

import java.util.ArrayList;

public class ExtendedIngredient {
    public String aisle;
    public double amount;
    public String consitency;
    public int id;
    public String image;
    public Measures measures;
    public ArrayList<String> meta;
    public String name;
    public String original;
    public String originalName;
    public String unit;

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getConsitency() {
        return consitency;
    }

    public void setConsitency(String consitency) {
        this.consitency = consitency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Measures getMeasures() {
        return measures;
    }

    public void setMeasures(Measures measures) {
        this.measures = measures;
    }

    public ArrayList<String> getMeta() {
        return meta;
    }

    public void setMeta(ArrayList<String> meta) {
        this.meta = meta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
