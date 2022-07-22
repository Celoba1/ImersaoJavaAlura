package Projetos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {
        //
        final String ANSI_RESET = "\u001b[m";
        final String ANSI_PURPLE_BACKGROUND = "\u001b[45;1m";
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // principais dados (titulo, poster, classificação)
        var parser = new JSONParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println("\u001b[37;1m " + ANSI_PURPLE_BACKGROUND + "Titulo: " + filme.get("title") + ANSI_RESET);
            System.out.println(filme.get("image"));

            var star = "★";
            var rating = (int) Math.round(Double.parseDouble(filme.get("imDbRating")));
            star = star.repeat(rating);
            System.out.printf("%sNota: %s%s", ANSI_PURPLE_BACKGROUND, filme.get("imDbRating"), ANSI_RESET);

        }

    }
}