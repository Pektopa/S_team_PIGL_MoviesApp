package moviesapp.model.api;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteria {

    public String title = "";
    public String minYear = "";
    public String maxYear = "";
    public List<String> genreIds = new ArrayList<>();
    public String minVoteAverage = "";
    public String page = "1";

    public SearchCriteria(){}

    public SearchCriteria(String title, String minYear, String maxYear, List<String> genreIds, String minVoteAverage, String page) {
        this.title = title;
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.genreIds = genreIds;
        this.minVoteAverage = minVoteAverage;
        this.page = page;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "title='" + title + '\'' +
                ", minYear='" + minYear + '\'' +
                ", maxYear='" + maxYear + '\'' +
                ", genres=" + genreIds +
                ", minVoteAverage='" + minVoteAverage + '\'' +
                ", page='" + page + '\'' +
                '}';
    }

    public boolean noInformationSent() {
        return noDiscoverCriteria() && title.isEmpty();
    }

    boolean noDiscoverCriteria(){
        return minYear.isEmpty() && maxYear.isEmpty() && genreIds.isEmpty() && minVoteAverage.isEmpty();
    }
}
