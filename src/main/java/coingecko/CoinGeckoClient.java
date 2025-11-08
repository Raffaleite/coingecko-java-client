package coingecko;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CoinGeckoClient {

    private static final String API_KEY = "CG-Ao4ka2KA4U4JxRdMVZ8TyASh";
    private static final String BASE_URL = "https://api.coingecko.com/api/v3";
    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private String auth(String url) {
        return url + (url.contains("?") ? "&" : "?") + "x_cg_demo_api_key=" + API_KEY;
    }

    public List<Crypto> getMarketList() throws IOException, InterruptedException {

        String url = auth(BASE_URL
                + "/coins/markets?vs_currency=usd"
                + "&order=market_cap_desc"
                + "&per_page=50"
                + "&page=1"
                + "&sparkline=false"
                + "&locale=en");

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() != 200) {
            throw new RuntimeException("Erro HTTP " + resp.statusCode() + " | Resposta: " + resp.body());
        }

        return mapper.readValue(resp.body(), new TypeReference<List<Crypto>>() {
        });
    }

   public Crypto getCryptoDetails(String id) throws IOException, InterruptedException {

    String url = auth(BASE_URL
            + "/coins/" + id
            + "?localization=false"
            + "&tickers=false"
            + "&market_data=true"
            + "&community_data=false"
            + "&developer_data=false"
            + "&sparkline=false"
    );

    HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

    HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());

    if (resp.statusCode() != 200) {
        throw new RuntimeException("Erro HTTP " + resp.statusCode() + " | Resposta: " + resp.body());
    }

    // Em vez de desserializar tudo extrair apenas os campos que interessa
    var node = mapper.readTree(resp.body());

    Crypto c = new Crypto();

    // campos simples
    if (node.has("id")) c.id = node.get("id").asText();
    if (node.has("symbol")) c.symbol = node.get("symbol").asText();
    if (node.has("name")) c.name = node.get("name").asText();

    if (node.has("market_data") && node.get("market_data").has("current_price")) {
        c.currentPrice = node.get("market_data").get("current_price").get("usd").asDouble();
    }

    return c;
}

}
