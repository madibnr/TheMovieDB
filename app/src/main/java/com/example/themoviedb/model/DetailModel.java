package com.example.themoviedb.model;

import android.provider.MediaStore;

import java.util.List;

public class DetailModel {

    private Integer id;
    private String title;
    private String backdrop;
    private String overview;
    private Double vote_average;
    private List<Genres> genres;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "DetailModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", backdrop='" + backdrop + '\'' +
                ", overview='" + overview + '\'' +
                ", vote_average=" + vote_average +
                ", genres=" + genres +
                '}';
    }

    public class Genres {

        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Genres{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
