package hcmute.edu.vn.foodmachinelearning.model;

import java.util.ArrayList;

public class Instructions{
    public String name;
    public ArrayList<Step> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }
}