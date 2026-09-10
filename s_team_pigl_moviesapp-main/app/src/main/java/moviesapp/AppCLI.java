package moviesapp;

import moviesapp.controller.command_line.CLController;

import static moviesapp.model.api.Genres.fillGENRE_NAME_ID_MAP;

public class AppCLI {
    public static void main(String[] args) {
        fillGENRE_NAME_ID_MAP();
        System.out.println("\n__________________________\nWelcome to MovieLand!\n__________________________");
        CLController controller = new CLController();
        controller.select();
    }
}