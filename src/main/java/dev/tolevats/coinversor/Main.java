package dev.tolevats.coinversor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        ConsultaMoneda consulta = new ConsultaMoneda();

        // EXTRA: Lista para guardar el historial de conversiones
        List<String> historial = new ArrayList<>();

        int opcion = 0;

        System.out.println("*************************************************");
        System.out.println("¡Bienvenido a Coinversor Pal!");
        System.out.println("*************************************************");

        while (opcion != 9) {
            // Menú interactivo
            System.out.println("\nSeleccione una opción de conversión:");
            System.out.println("1) Dólar (USD) => Peso Argentino (ARS)");
            System.out.println("2) Peso Argentino (ARS) => Dólar (USD)");
            System.out.println("3) Dólar (USD) => Real Brasileño (BRL)");
            System.out.println("4) Real Brasileño (BRL) => Dólar (USD)");
            System.out.println("5) Dólar (USD) => Peso Colombiano (COP)");
            System.out.println("6) Peso Colombiano (COP) => Dólar (USD)");
            System.out.println("7) Ver Historial de Conversiones");
            System.out.println("9) Salir");
            System.out.print("Elija una opción válida: ");

            try {
                opcion = lectura.nextInt();

                if (opcion == 9) {
                    System.out.println("¡Gracias por usar Coinversor Pal! Nos vemos pronto.");
                    break;
                }

                if (opcion == 7) {
                    System.out.println("--- Historial ---");
                    for (String registro : historial) {
                        System.out.println(registro);
                    }
                    continue; // Vuelve al inicio del bucle
                }

                String base = "";
                String target = "";

                // Lógica de selección de monedas
                switch (opcion) {
                    case 1 -> { base = "USD"; target = "ARS"; }
                    case 2 -> { base = "ARS"; target = "USD"; }
                    case 3 -> { base = "USD"; target = "BRL"; }
                    case 4 -> { base = "BRL"; target = "USD"; }
                    case 5 -> { base = "USD"; target = "COP"; }
                    case 6 -> { base = "COP"; target = "USD"; }
                    default -> {
                        System.out.println("Opción no válida. Intente de nuevo.");
                        continue;
                    }
                }

                System.out.print("Ingrese la cantidad a convertir: ");
                double cantidad = lectura.nextDouble();

                // Llamada a la API
                Moneda moneda = consulta.buscarMoneda(base, target, cantidad);

                // Formateo de la salida con timestamp (EXTRA)
                String resultado = String.format("Valor %.2f [%s] corresponde al valor final de =>> %.2f [%s]",
                        cantidad, base, moneda.conversion_result(), target);

                String log = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        + " - " + resultado;

                historial.add(log); // Guardar en historial
                System.out.println(resultado);

            } catch (Exception e) {
                System.out.println("Ocurrió un error (¿Ingresaste una letra en lugar de un número?): " + e.getMessage());
                lectura.nextLine(); // Limpiar buffer del scanner
            }
        }
    }
}
