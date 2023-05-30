import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Queue;
import java.util.Scanner;

public class WebAPI {
    public static void getNowPlaying() {
        String APIkey = "0d84fb3d8f2d7a81762b0619024966f4"; // your personal API key on TheMovieDatabase
        String queryParameters = "?api_key=" + APIkey;
        String endpoint = "https://api.themoviedb.org/3/movie/top_rated";
        String url = endpoint + queryParameters;
        String urlResponse = "";
        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            urlResponse = response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // when determining HOW to parse the returned JSON data,
        // first, print out the urlResponse, then copy/paste the output
        // into the online JSON parser: https://jsonformatter.org/json-parser
        // use the visual model to help you determine how to parse the data!
        JSONObject jsonObj = new JSONObject(urlResponse);
        JSONArray movieList = jsonObj.getJSONArray("results");
        for (int i = 0; i < movieList.length(); i++) {
            JSONObject movieObj = movieList.getJSONObject(i);
            String movieTitle = movieObj.getString("title");
            int movieID = movieObj.getInt("id");
            String posterPath = movieObj.getString("poster_path");
            String fullPosterPath = "https://image.tmdb.org/t/p/w500" + posterPath;
            System.out.println(movieID + " " + movieTitle + " " + fullPosterPath);
        }
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter a movie ID to learn more: ");
        String ID = scan.nextLine();

        String endpoint2 = "https://api.themoviedb.org/3/movie/" + ID;
        String url2 = endpoint2 + queryParameters;
        try {
            URI myUri = URI.create(url2); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            urlResponse = response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JSONObject jsonObj2 = new JSONObject(urlResponse);
        String title = "Title: " + jsonObj2.getString("title");
        String overview = "Overview: " + jsonObj2.getString("overview");
        String releaseDate = "Release Datae: " + jsonObj2.getString("release_date");
        int runtime = jsonObj2.getInt("runtime");
        int revenue = jsonObj2.getInt("revenue");
        System.out.println(title + "\n" + overview + "\n" + releaseDate + "\n" + "Runtime: " + runtime + "\n" + "Revenue: " + revenue);
        JSONArray array = jsonObj2.getJSONArray("genres");
        System.out.println("Genres: ");
        for(int i = 0; i< array.length(); i++){
            System.out.println(array.getJSONObject(i).getString("name"));
        }
    }
}