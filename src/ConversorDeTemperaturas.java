//1. IMPORTACIONES Y DECLARACIÓN DE CLASE
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConversorDeTemperaturas {
    /* importe Scanner para entrada por consola.
     * Importe InputMismatchException para manejar errores de entrada.
     * Definí la clase ConversorDeTemperaturas
     */

    //2.INICIALIZACIÓN DE SCANNER
    private static final Scanner scanner = new Scanner(System.in);
    //se creo un Scanner global para reutilizarlo en todo el programa.

    //3.MÉTODO main: EJECUCIÓN PRINCIPAL
    public static void main(String[] args) {
        /*
        *Muestra el mensaje de bienvenida.
        * controla la ejecución repetida mediante un bucle while
        * Usa try-catch para manejar errores.
         */
        System.out.println("=== CONVERSOR BIDIRECCIONAL DE TEMPERATURAS ===\n");

        boolean continueProgram = true;

        while (continueProgram) {
            try {
                //dentro del bucle:
                int option = showMenuAndGetOption();
         //llama al menú principal y captura la opción del usuario
                switch (option) {
                    /* Usar un switch para legir la acción segun el menú:
                    * celsius a fahrenheit
                    * fahrenheit a celsius
                    * deteccion automatica (ej: "30C", "86F")
                    * Salir
                     */
                    case 1:
                        convertCelsiusToFahrenheit();
                        break;
                    case 2:
                        convertFahrenheitToCelsius();
                        break;
                    case 3:
                        autoDetectConversion();
                        break;
                    case 4:
                        continueProgram = false;
                        System.out.println("\n¡Gracias por usar el conversor! ¡Hasta luego! 👋");
                        break;
                    default:
                        System.out.println("❌ Opción inválida. Por favor seleccione una opción del 1 al 4.");
                }

                if (continueProgram) {
                    //despues de cada conversion:
                    continueProgram = askToContinue();
                    //pregunta si desea seguir o salir
                }

            } catch (InputMismatchException e) {
                System.out.println("❌ Error: Por favor ingrese un número válido.");
                scanner.nextLine(); // Limpiar el buffer
            } catch (Exception e) {
                System.out.println("❌ Error inesperado: " + e.getMessage());
            }
        }

        scanner.close();
    }
//4.MENU PRINCIPAL
    /**
     * Muestra el menú principal y obtiene la opción del usuario
     */
    private static int showMenuAndGetOption() {
        /**
         * Muestra las opciones por consola
         * retorna el numero ingresado por el usuario
         */
        System.out.println("\nSeleccione el tipo de conversión:");
        System.out.println("1) Celsius a Fahrenheit");
        System.out.println("2) Fahrenheit a Celsius");
        System.out.println("3) Detección automática (ej: 30C o 86F)");
        System.out.println("4) Salir");
        System.out.print("\nSeleccione una opción: ");

        return scanner.nextInt();
    }

    //5.CONVIERTE DE CELSIUS A FAHRENHEIT
    private static void convertCelsiusToFahrenheit() {
        /**
         * Solicita tempratura en °C
         * aplica formula: (°C x 9/5) + 32
         * Muestra resultado
         * llama a checkEctremeConditions() para comentarios adicionales.
         */
        System.out.print("Ingrese la temperatura en grados Celsius: ");
        double celsius = getValidTemperature();

        double fahrenheit = (celsius * 9.0 / 5.0) + 32;

        System.out.printf("\n%.1f grados Celsius equivale a %.1f grados Fahrenheit.\n",
                celsius, fahrenheit);

        checkExtremeConditions(celsius, "C");
    }

    //6.CONVIERTE FAHRENHEIT A CELSIUS
    private static void convertFahrenheitToCelsius() {
        /*
        similar al anterior, pero convierte desde °F usando (°F -32) x 5/9
         */
        System.out.print("Ingrese la temperatura en grados Fahrenheit: ");
        double fahrenheit = getValidTemperature();

        double celsius = (fahrenheit - 32) * 5.0 / 9.0;

        System.out.printf("\n%.1f grados Fahrenheit equivale a %.1f grados Celsius.\n",
                fahrenheit, celsius);

        checkExtremeConditions(celsius, "C");
    }

    // 7.DETENCCIÓN AUTOMÁTICA (BONUS AVANZADO)
    private static void autoDetectConversion() {
        /**
         * El usuario ingresa un valor como "30C" o "86F"
         * Se separa el número de la unidad
         * se convierte automaticamente según la unidad detectada
         * Valida si es numérico y si la unidad es valida
         */
        System.out.print("Ingrese la temperatura con su unidad (ej: 30C o 86F): ");
        scanner.nextLine(); // Limpiar buffer
        String input = scanner.nextLine().trim().toUpperCase();

        if (input.isEmpty()) {
            System.out.println("❌ Error: No se ingresó ningún valor.");
            return;
        }

        char unit = input.charAt(input.length() - 1);
        String numberPart = input.substring(0, input.length() - 1);

        try {
            double temperature = Double.parseDouble(numberPart);

            switch (unit) {
                case 'C':
                    double fahrenheit = (temperature * 9.0 / 5.0) + 32;
                    System.out.printf("\n%.1f grados Celsius equivale a %.1f grados Fahrenheit.\n",
                            temperature, fahrenheit);
                    checkExtremeConditions(temperature, "C");
                    break;
                case 'F':
                    double celsius = (temperature - 32) * 5.0 / 9.0;
                    System.out.printf("\n%.1f grados Fahrenheit equivale a %.1f grados Celsius.\n",
                            temperature, celsius);
                    checkExtremeConditions(celsius, "C");
                    break;
                default:
                    System.out.println("❌ Error: Unidad no reconocida. Use 'C' para Celsius o 'F' para Fahrenheit.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: El valor numérico no es válido.");
        }
    }

    //8.VALIDACIÓN DE ENTREDA NÚMERICA
    private static double getValidTemperature() {
        /**
         * Maneja errores con try-catch.
         * Valida si es menor que -273.15 ¬C (Cero absoluto) y advierte al usuario.
         * Si la entrada no es numerica, vueve a pedirla
         */
        while (true) {
            try {
                double temperature = scanner.nextDouble();

                // Validación de temperaturas extremas pero técnicamente posibles
                if (temperature < -273.15) {
                    System.out.println("⚠️  Advertencia: La temperatura está por debajo del cero absoluto (-273.15°C).");
                    System.out.print("¿Desea continuar? (s/n): ");
                    scanner.nextLine(); // Limpiar buffer
                    String response = scanner.nextLine().trim().toLowerCase();

                    if (!response.equals("s") && !response.equals("si") && !response.equals("sí")) {
                        System.out.print("Ingrese una nueva temperatura: ");
                        continue;
                    }
                }

                return temperature;

            } catch (InputMismatchException e) {
                System.out.println("❌ Error: Por favor ingrese un número válido.");
                scanner.nextLine(); // Limpiar el buffer
                System.out.print("Ingrese la temperatura: ");
            }
        }
    }

    //9. CHEQUEO DE CONDICIONES EXTREMAS
    private static void checkExtremeConditions(double celsius, String unit) {
        /** imprime mensajes especiales según el valor:
         * 0°C -> congelación
         * 100¬C -> ebullición
         * < -40°C -> extremadamente frio
         * 50°C -> extremadamente caliente
         * 20-25°C -> temperatura ambiente ideal
         * 37 - 38°C -> temperatura corporal normal
         * -273.15¬C -> cero absoluto
         */
        // Convertir a Celsius si es necesario para las comparaciones
        double tempInCelsius = unit.equals("C") ? celsius : celsius;

        if (tempInCelsius <= 0) {
            System.out.println("🧊 ¡Temperatura de congelación del agua!");
        } else if (tempInCelsius >= 100) {
            System.out.println("💨 ¡Temperatura de ebullición del agua!");
        } else if (tempInCelsius < -40) {
            System.out.println("🥶 ¡Temperatura extremadamente fría!");
        } else if (tempInCelsius > 50) {
            System.out.println("🔥 ¡Temperatura extremadamente caliente!");
        }

        // Condiciones especiales
        if (tempInCelsius == -273.15) {
            System.out.println("❄️ ¡Cero absoluto alcanzado!");
        } else if (tempInCelsius >= 37 && tempInCelsius <= 38) {
            System.out.println("🌡️ Temperatura corporal normal humana");
        } else if (tempInCelsius >= 20 && tempInCelsius <= 25) {
            System.out.println("🏠 Temperatura ambiente ideal");
        }
    }

    //10.¿DESEA CONTINUAR?
    private static boolean askToContinue() {
        //pregunta si desea hacer otra conversión, aceptar con "s", "si", "sí", "y", "yes"
        System.out.print("\n¿Desea realizar otra conversión? (s/n): ");
        scanner.nextLine(); // Limpiar buffer
        String response = scanner.nextLine().trim().toLowerCase();

        return response.equals("s") || response.equals("si") || response.equals("sí") ||
                response.equals("yes") || response.equals("y");
    }
}