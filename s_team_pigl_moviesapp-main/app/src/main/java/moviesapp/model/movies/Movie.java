package moviesapp.model.movies;

import moviesapp.model.api.Genres;

import java.util.List;
import java.util.Objects;

public record Movie(boolean adult, String backdropPath, List<String> genres, String id, String originalLanguage,
                    String originalTitle, String overview, double popularity, String posterPath, String releaseDate,
                    String title, boolean video, double minVoteAverage, int voteCount) {
    @Override
    public String toString(){
        String print =
                " |__________________" +
                        "\n  title: " +
                        title +
                        "\n  vote average: " +
                        minVoteAverage;

        if(releaseDate != null && releaseDate.length() >= 4){
            print += "\n  release year: " + releaseDate.substring(0,4);
        }
        else{
            print += "\n  release year: empty";
        }
        print += "\n__________________________\n";

        return print;
    }

    /**
     * Return the details of the movie
     * @return the details of the movie
     */
    public String details(){
        return "__________________________  \n"+"movie n°"+ id +":\n  title: "+title+"\n  original title: "+originalTitle+
                "\n  release date: "+releaseDate+ "\n  original language: "+originalLanguage+
                "\n  genreIds: "+genres+"\n  popularity: " +popularity+"\n  adult: "+adult+
                "\n  vote average: "+minVoteAverage+"\n  vote count: " +voteCount+"\n  overview: "
                +overview+ "\n__________________________  ";
    }

    /**
     * Return a string that represents json code corresponding to the movie.
     * @return a string that represents json code corresponding to the movie.
     */
    public String toJsonFormat() {
        return "    {\n" +
                "      \"adult\" : " + adult() + ",\n" +
                "      \"backdrop_path\" : \"" + backdropPath() + "\",\n" +
                "      \"genre_ids\" : " + genres() + ",\n" +
                "      \"id\" : " + id() + ",\n" +
                "      \"original_language\" : \"" + originalLanguage() + "\",\n" +
                "      \"original_title\" : \"" + originalTitle() + "\",\n" +
                "      \"overview\" : \"" + overview() + "\",\n" +
                "      \"popularity\" : " + popularity() + ",\n" +
                "      \"poster_path\" : \"" + posterPath() + "\",\n" +
                "      \"release_date\" : \"" + releaseDate() + "\",\n" +
                "      \"title\" : \"" + title() + "\",\n" +
                "      \"video\" : " + video() + ",\n" +
                "      \"vote_average\" : " + minVoteAverage() + ",\n" +
                "      \"vote_count\" : " + voteCount() + "\n" +
                "    }";
    }

    public String getReleaseYear(){
        return releaseDate.substring(0,4);
    }

    public String genresToString(){

        if(genres.isEmpty()){
            return "";
        }

        StringBuilder genresToString = new StringBuilder(" • ");

        for(String genre: genres){
            if (genre == null || genre.isEmpty()) {
                return genre;
            }
            for(String genreKey : Genres.GENRE_NAME_ID_MAP.keySet()){
                if(Objects.equals(Genres.GENRE_NAME_ID_MAP.get(genreKey), genre)){
                    genresToString.append(genreKey.substring(0, 1).toUpperCase()).append(genreKey.substring(1)).append(", ");
                    continue;
                }
            }
        }
        return genresToString.substring(0,genresToString.length()-2);
    }

    public String overviewToString(int maxWidth){
        String[] words = overview.split(" ");
        StringBuilder formattedText = new StringBuilder();
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + 1 > maxWidth) {
                if (!line.isEmpty()) {
                    formattedText.append(line).append("\n");
                    line = new StringBuilder();
                }
            }
            if (!line.isEmpty()) {
                line.append(" ");
            }
            line.append(word);
        }

        if (!line.isEmpty()) {
            formattedText.append(line);
        }

        return formattedText.toString();
    }
}