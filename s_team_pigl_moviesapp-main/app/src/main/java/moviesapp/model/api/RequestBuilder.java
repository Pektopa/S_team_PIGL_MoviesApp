package moviesapp.model.api;

import moviesapp.model.exceptions.IndexException;
import moviesapp.model.exceptions.NotAPositiveIntegerException;
import okhttp3.Request;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestBuilder {
    final static String baseUrl = "https://api.themoviedb.org/3";
    final static String apiKey = "api_key=5e40bf6f22600832c99dbb5d52115269";
    final static String language = "&language=en-US";
    private static SearchCriteria criteria = new SearchCriteria();
    static final Map<String, String> criteriaToUrl = new HashMap<>();
    public static String requestUrl;
    public static int minAcceptableYearValue = 1874;
    public static int maxAcceptableYearValue = LocalDate.now().getYear();
    public static String imageBaseURL = "https://image.tmdb.org/t/p";
    public static String imageSize = "/w220_and_h330_face";

    public RequestBuilder() {
    }

    public RequestBuilder(SearchCriteria criteria) {

        if (criteria != null) {
            RequestBuilder.criteria = criteria;
            criteriaToUrl.put("title", "&query=" + criteria.title);

            if(criteria.minYear.equals(criteria.maxYear) && criteria.minVoteAverage.isEmpty() && criteria.genreIds.isEmpty()) {

                criteriaToUrl.put("minY", "&primary_release_year=" + criteria.minYear);
            }
            else {
                if(criteria.minYear.isEmpty()){
                    criteriaToUrl.put("minY", "&primary_release_date.gte=");
                }
                else {
                    criteriaToUrl.put("minY", "&primary_release_date.gte=" + criteria.minYear + "-01-01");
                }
                if (criteria.maxYear.isEmpty()) {
                    criteriaToUrl.put("maxY", "&primary_release_date.lte=");
                }
                else {
                    criteriaToUrl.put("maxY", "&primary_release_date.lte=" + criteria.maxYear + "-12-31");
                }
                criteriaToUrl.put("genres", "&with_genres=" + buildUrlWithGenres(criteria.genreIds));
                criteriaToUrl.put("vote", "&vote_average.gte=" + criteria.minVoteAverage);
                criteriaToUrl.put("page", "&page=" + criteria.page);
            }
        }
        else {
            criteriaToUrl.put("title", "&query=");
            criteriaToUrl.put("minY", "&primary_release_date.gte=");
            criteriaToUrl.put("maxY", "&primary_release_date.lte=");
            criteriaToUrl.put("genres", "&with_genres=");
            criteriaToUrl.put("vote", "&vote_average.gte=");
            criteriaToUrl.put("page", "&page=");
        }
    }

    /**
     * Builds a URL string with the given list of genres.
     *
     * @param genres The list of genres.
     * @return The URL string with genres.
     */
    private String buildUrlWithGenres(List<String> genres) {

        String genresString = "";

        if (genres != null && !genres.isEmpty()) {
            genresString =  genres.toString().replace("[", "").replace("]", "");
        }

        return genresString;
    }

    /**
     * Builds a request based on the search mode.
     *
     * @param searchMode The search mode.
     * @return The built request.
     * @throws IndexException If an invalid search mode is provided.
     */
    Request build(int searchMode) throws IndexException {

        switch (searchMode) {
            case 1 -> criteriaToUrl.put("searchMode", "/search/movie?");
            case 2 -> criteriaToUrl.put("searchMode", "/discover/movie?");
            case 3 -> criteriaToUrl.put("searchMode", "/movie/popular?");
            case 4 -> criteriaToUrl.put("searchMode", "/genre/movie/list?");
            default -> throw new IndexException();
        }

        setRequestUrl();

        return new Request.Builder().url(requestUrl).build();
    }

    /**
     * Builds a request with the given URL.
     *
     * @param url The URL for the request.
     * @return The built request.
     */
    Request build(String url) {
        requestUrl = url;
        retrieveCriteriaFromUrl();
        return new Request.Builder().url(url).build();
    }

    /**
     * Sets the request URL based on criteria.
     */
    private void setRequestUrl() {
        requestUrl = baseUrl +
                criteriaToUrl.get("searchMode") +
                apiKey +
                language +
                criteriaToUrl.get("title") +
                criteriaToUrl.get("minY") +
                criteriaToUrl.get("maxY") +
                criteriaToUrl.get("genres") +
                criteriaToUrl.get("vote") +
                criteriaToUrl.get("page");
    }

    /**
     * Converts the given string value to a positive integer.
     *
     * @param valueToConvert The string value to convert to a positive integer.
     * @return The positive integer converted from the given string.
     * @throws NotAPositiveIntegerException If the given string cannot be converted to a positive integer.
     */
    public static int convertAsPositiveInt(String valueToConvert) throws NotAPositiveIntegerException {

        try{
            int value = Integer.parseInt(valueToConvert);

            if(value < 0) {
                throw new NumberFormatException();
            }
            return value;
        }
        catch (NumberFormatException e){
            throw new NotAPositiveIntegerException();
        }
    }

    /**
     * Parses criteria from the given URL and populates the corresponding fields in the criteria object.
     * The URL should contain query parameters in the format key=value separated by '&'.
     */
    private void retrieveCriteriaFromUrl() {

        Map <String, String> criteriaFromUrl = parseUrl();

        criteria.title = criteriaFromUrl.get("query");

        if (criteriaFromUrl.get("primary_release_date.gte").isEmpty()) {
            criteria.minYear = "";
        }
        else {
            criteria.minYear = criteriaFromUrl.get("primary_release_date.gte").substring(0, 4);
        }
        if (criteriaFromUrl.get("primary_release_date.lte").isEmpty()) {
            criteria.maxYear = "";
        }
        else {
            criteria.maxYear = criteriaFromUrl.get("primary_release_date.lte").substring(0, 4);
        }
        if (criteriaFromUrl.get("with_genres").isEmpty()) {
            criteria.genreIds = new ArrayList<>();
        }
        else {
            criteria.genreIds = Arrays.asList(criteriaFromUrl.get("with_genres").split(",\\s*"));
        }
        criteria.minVoteAverage = criteriaFromUrl.get("vote_average.gte");
        criteria.page = criteriaFromUrl.get("page");
    }

    private static Map<String, String> parseUrl() {
        return Stream.of(requestUrl.split("&"))
                .map(param -> param.split("="))
                .collect(Collectors.toMap(param -> param[0], param -> param.length > 1 ? param[1] : ""));
    }

    /**
     * Retrieves the current page from the criteria object.
     *
     * @return The current page number.
     */
    public static int getCurrentPage() throws NotAPositiveIntegerException {

        return convertAsPositiveInt(criteria.page);
    }


    public static String updateRequestUrlPage (String newPage) {
        String oldPage = parseUrl().get("page");
        return requestUrl.substring(0, requestUrl.length() - oldPage.length()) + newPage;
    }
}
