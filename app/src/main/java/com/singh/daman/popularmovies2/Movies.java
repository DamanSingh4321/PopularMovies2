package com.singh.daman.popularmovies2;

/**
 * Created by daman on 27/9/16.
 */
public class Movies {

    private String id;
    private String title;
    private String image;
    private String vote;
    private String date;
    private String overview;

    public Movies(){
    }

    public Movies(String id, String title, String image, String vote, String date, String overview) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.vote = vote;
        this.date = date;
        this.overview = overview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
