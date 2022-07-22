package Alura;

import java.io.PrintStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class AppAlura {
    public static void main(String[] args) throws Exception {
        final String ANSI_RESET = "\u001b[m";
        final String ANSI_PURPLE_BACKGROUND = "\u001b[44;1m";
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
            System.out.println("Titulo: " + filme.get("title") + ANSI_RESET);
            System.out.println(filme.get("image"));

            var starCount = (int) Math.round(Double.parseDouble(filme.get("imDbRating")));
            var rating = Double.parseDouble(filme.get("imDbRating"));
            // gerar estrelas de acordo com manipulação dos dados de nota
            Charset utf8Charset = Charset.forName("UTF-8");
            Charset defaultCharset = Charset.defaultCharset();
            String unicodeMessage = "\u2605";
            byte[] sourceBytes = unicodeMessage.getBytes("UTF-8");
            String data = new String(sourceBytes , defaultCharset.name());
            PrintStream out = new PrintStream(System.out, true, utf8Charset.name());
            data = data.repeat(starCount);
            out.println("Nota: "+rating+" "+data);
        }
    }
}
