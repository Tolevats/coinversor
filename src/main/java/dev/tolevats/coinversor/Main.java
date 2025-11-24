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

        int opcion = -1;

        System.out.println("*************************************************");
        System.out.println("¡Bienvenido a Coinversor Pal!");
        System.out.println("*************************************************");

        while (opcion != 0) {
            // Menú interactivo
            System.out.println("\nSeleccione una opción de conversión:");
            // 1. CHILE (CLP)
            System.out.println("1) Dólar (USD) => Peso Chileno (CLP)");
            System.out.println("2) Peso Chileno (CLP) => Dólar (USD)");
            // 2. BRASIL (BRL)
            System.out.println("3) Dólar (USD) => Real Brasileño (BRL)");
            System.out.println("4) Real Brasileño (BRL) => Dólar (USD)");
            // 3. MÉXICO (MXN)
            System.out.println("3) Dólar (USD) => Peso Mexicano (MXN)");
            System.out.println("4) Peso Mexicano (MXN) => Dólar (USD)");
            // 4. COLOMBIA (COP)
            System.out.println("5) Dólar (USD) => Peso Colombiano (COP)");
            System.out.println("6) Peso Colombiano (COP) => Dólar (USD)");
            // 3. PERÚ (PEN)
            System.out.println("5) Dólar (USD) => Sol Peruano (PEN)");
            System.out.println("6) Sol Peruano (PEN) => Dólar (USD)");
            // 6. ARGENTINA (ARS)
            System.out.println("11) Dólar (USD) => Peso Argentino (ARS)");
            System.out.println("12) Peso Argentino (ARS) => Dólar (USD)");
            // EXTRAS
            System.out.println("13) Ver Historial de Conversiones");
            System.out.println("0) Salir");

            System.out.print("Elija una opción válida: ");

            try {
                opcion = lectura.nextInt();

                if (opcion == 0) {
                    System.out.println("¡Gracias por usar Coinversor Pal! Nos vemos pronto.");
                    break;
                }

                if (opcion == 13) {
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
                    case 1 -> { base = "USD"; target = "CLP"; }
                    case 2 -> { base = "CLP"; target = "USD"; }
                    case 3 -> { base = "USD"; target = "BRL"; }
                    case 4 -> { base = "BRL"; target = "USD"; }
                    case 5 -> { base = "USD"; target = "MXN"; }
                    case 6 -> { base = "MXN"; target = "USD"; }
                    case 7 -> { base = "USD"; target = "COP"; }
                    case 8 -> { base = "COP"; target = "USD"; }
                    case 9 -> { base = "USD"; target = "PEN"; }
                    case 10 -> { base = "PEN"; target = "USD"; }
                    case 11 -> { base = "USD"; target = "ARS"; }
                    case 12 -> { base = "ARS"; target = "USD"; }
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
