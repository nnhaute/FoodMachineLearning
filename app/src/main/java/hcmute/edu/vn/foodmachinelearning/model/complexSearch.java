package hcmute.edu.vn.foodmachinelearning.model;

import java.util.ArrayList;

public class complexSearch {
    int offset;
    int number;
    ArrayList<Recipe> results;
    int totalResults;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<Recipe> getResult() {
        return results;
    }

    public void setResult(ArrayList<Recipe> result) {
        this.results = result;
    }

    public int getTotalResult() {
        return totalResults;
    }

    public void setTotalResult(int totalResult) {
        this.totalResults = totalResult;
    }

    public complexSearch(int offset, int number, ArrayList<Recipe> result, int totalResult) {
        this.offset = offset;
        this.number = number;
        this.results = result;
        this.totalResults = totalResult;
    }
}
