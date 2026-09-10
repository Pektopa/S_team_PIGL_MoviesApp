package moviesapp.model;

import moviesapp.model.movies.Favorites;
import moviesapp.model.movies.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FavoritesTest {
    private static Favorites favorites;
    private final Movie movie1 = new Movie(true,null,null,"1",null,
            null, null,0,null,null,null,true,
            0, 0);
    private final Movie movie2 = new Movie(true,null,null,"2",null,
            null, null,0,null,null,null,true,
            0, 0);
    private final Movie movie3 = new Movie(true,null,null,"3",null,
            null, null,0,null,null,null,true,
            0, 0);

    @BeforeAll
    static void setupBeforeAll(){
        favorites = Favorites.instance;
    }

    @BeforeEach
    void setupBeforeEach(){
        if(!favorites.isEmpty()){
            favorites.clear();
        }
    }

    @Test
    void testToString(){
        assertEquals(favorites.toString(), "Your list of movies is empty.");
        favorites.add(movie1);
        assertEquals(favorites.toString(), indexOfMovie(1) + movie1);
        favorites.add(movie2);
        favorites.add(movie3);
        assertEquals(favorites.toString(),
                indexOfMovie(1) + movie1 + indexOfMovie(2) + movie2 + indexOfMovie(3) + movie3);
        favorites.remove(movie2);
        assertEquals(favorites.toString(),
                indexOfMovie(1) + movie1 + indexOfMovie(2) + movie3);
        favorites.remove(movie1);
        favorites.remove(movie3);
        assertEquals(favorites.toString(), "Your list of movies is empty.");
    }

    @Test
    void testClear(){
        assertThat(favorites.isEmpty()).isTrue();
        favorites.clear();
        assertThat(favorites.isEmpty()).isTrue();
        favorites.add(movie1);
        favorites.add(movie2);
        assertThat(favorites.isEmpty()).isFalse();
        favorites.clear();
        assertThat(favorites.isEmpty()).isTrue();
    }

    @Test
    void testIsEmpty(){
        assertThat(favorites.isEmpty()).isTrue();
        favorites.add(movie2);
        assertThat(favorites.isEmpty()).isFalse();
        favorites.clear();
        assertThat(favorites.isEmpty()).isTrue();
    }

    @Test
    void testAdd(){
        favorites.add(movie1);
        assertEquals(favorites.toString(),
                indexOfMovie(1) + movie1);
        favorites.add(movie2);
        favorites.add(movie3);
        assertEquals(favorites.toString(),
                indexOfMovie(1) + movie1 + indexOfMovie(2) + movie2 + indexOfMovie(3) + movie3);
        favorites.add(movie2);
        favorites.add(movie3);
        assertEquals(favorites.toString(),
                indexOfMovie(1) + movie1 + indexOfMovie(2) + movie2 + indexOfMovie(3) + movie3);
        favorites.add(null);
        assertEquals(favorites.toString(),
                indexOfMovie(1) + movie1  + indexOfMovie(2) + movie2 + indexOfMovie(3) + movie3);
    }
    @Test
    void testRemove(){
        favorites.add(movie1);
        favorites.remove(movie1);
        assertEquals(favorites.toString(), "Your list of movies is empty.");
        favorites.add(movie1);
        favorites.add(movie2);
        favorites.remove(movie2);
        assertEquals(favorites.toString(), indexOfMovie(1) + movie2);
        favorites.remove(movie3);
        assertEquals(favorites.toString(), indexOfMovie(1) + movie2);
        favorites.remove(null);
        assertEquals(favorites.toString(), indexOfMovie(1) + movie2);
    }

    private String indexOfMovie(int index){
        return "| n°0" + index;
    }
}