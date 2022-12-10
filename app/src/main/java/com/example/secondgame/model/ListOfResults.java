package com.example.secondgame.model;

import java.util.ArrayList;

public class ListOfResults {

    private ArrayList<Result> results;

    public ListOfResults() {
        results = new ArrayList<>();
    }

    ;

    public ArrayList<Result> getResults() {
        return results;
    }

    public ListOfResults setResults(ArrayList<Result> results) {
        this.results = results;
        return this;
    }
}
