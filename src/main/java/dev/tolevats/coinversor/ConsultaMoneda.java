package dev.tolevats.coinversor;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaMoneda {
    // Método para buscar la tasa de cambio entre dos monedas
    public Moneda buscarMoneda(String monedaBase, String monedaTarget, double cantidad) {

        // clave real de ExchangeRate-API
        String apiKey = "37f9c9db881b2ef42d903c54";

        // Usando el endpoint 'pair' para convertir directamente
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + monedaBase + "/" + monedaTarget + "/" + cantidad);

        try (HttpClient client = HttpClient.newHttpClient()) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Gson para convertir JSON en clase Moneda
            return new Gson().fromJson(response.body(), Moneda.class);
        } catch (Exception e) {
            throw new RuntimeException("No encontré esa moneda, ¿quizás un error en el código ISO?");
        }
    }
}
