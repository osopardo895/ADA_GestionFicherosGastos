import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class RegistroGastos {
    private static final String ARCHIVO_GASTOS = "gastos.txt";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final List<Gasto> listaGastos = new ArrayList<>();

    public static void main(String[] args) {
        cargarGastos();
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n--- Registro de Gastos Personales ---");
            System.out.println("1. Añadir gasto");
            System.out.println("2. Ver todos los gastos");
            System.out.println("3. Calcular total de gastos");
            System.out.println("4. Ver gastos por categoría");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            switch (opcion) {
                case 1:
                    anadirGasto(scanner);
                    break;
                case 2:
                    verGastos();
                    break;
                case 3:
                    calcularTotalGastos();
                    break;
                case 4:
                    verGastosPorCategoria(scanner);
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
        guardarGastos();
        scanner.close();
    }

    private static void cargarGastos() {
        File archivoGastos = new File(ARCHIVO_GASTOS);
        if (archivoGastos.exists()){
            try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_GASTOS))) {
                String linea = null;
                do {
                    linea = reader.readLine();
                    if (!linea.isEmpty()){
                        String[] partes = linea.split(",");
                        LocalDate fecha = LocalDate.parse(partes[0],FORMATO_FECHA);
                        String categoria = partes[1];
                        String descripcion = partes[2];
                        double cantidad = Double.parseDouble(partes[3]);
                        listaGastos.add(new Gasto(fecha,categoria,descripcion,cantidad));
                    }
                }
                while (!linea.isEmpty());
            }catch (IOException e){
                System.out.println("No se pudo cargar el archivo de gastos: " + e.getMessage());
            }
        }else{
            System.out.println("""
                                No hay ningún archivo 'Gastos.txt'
                                Generando nuevo archivo""");
            try {
                for (int i = 0; i<3; i++){
                    Thread.sleep(900);
                    System.out.println(".");
                }
            }catch (InterruptedException e){
                System.out.println("Error con la espera de carga: "+e.getMessage());
            }
        }
    }

    private static void guardarGastos(){
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_GASTOS))) {
            for (Gasto gasto : listaGastos) {
                writer.println(gasto.toString());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los gastos: " + e.getMessage());
        }
    }
    private static void anadirGasto(Scanner scanner) {
        System.out.print("Introduce la fecha (DD/MM/YYYY): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine(),FORMATO_FECHA);
        System.out.print("Introduce la categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Introduce la descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Introduce la cantidad: ");
        double cantidad = scanner.nextDouble();
        // añade un Gasto a la lista listaGastos
        listaGastos.add(new Gasto(fecha,categoria,descripcion,cantidad));
        System.out.println("Gasto registrado correctamente.");

        /*try (PrintWriter writer = new PrintWriter(new
                FileWriter(ARCHIVO_GASTOS, true))) {
            writer.println(fecha + "," + categoria + "," + descripcion + ","
                    + cantidad);
        } catch (IOException e) {
            System.out.println("Error al registrar el gasto: " +
                    e.getMessage());
        }*/
    }
    private static void verGastos() {
        System.out.println("\n--- Todos los Gastos ---");
        for (Gasto i : listaGastos){
            System.out.println(i.toString());
        }
        /*try (BufferedReader reader = new BufferedReader(new
                FileReader(ARCHIVO_GASTOS))) {
            String linea;
            System.out.println("\n--- Todos los Gastos ---");
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                System.out.println("Fecha: " + partes[0] + ", Categoría: " +
                                partes[1] +
                                ", Descripción: " + partes[2] + ", " +
                        "cantidad: $" + partes[3]);
            }
        } catch (IOException e) {
            System.out.println("Error al leer los gastos: " +
                    e.getMessage());
        }*/
    }
    private static void calcularTotalGastos() {
        double total = 0;
        for (Gasto i : listaGastos) {
            total+=i.getCantidad();
        }
        System.out.println("Total de gastos: $" + total);
        /*try (BufferedReader reader = new BufferedReader(new
                FileReader(ARCHIVO_GASTOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                total += Double.parseDouble(partes[3]);
            }
            System.out.println("Total de gastos: $" + total);
        } catch (IOException e) {
            System.out.println("Error al calcular el total de gastos: " +
                    e.getMessage());
        }*/
    }
    private static void verGastosPorCategoria(Scanner scanner) {
        System.out.print("Introduce la categoría a buscar: ");
        String categoriaBuscada = scanner.nextLine().toLowerCase();
        for (Gasto i :listaGastos){
            if (i.getCategoria().toLowerCase().equals(categoriaBuscada)){
                System.out.println(i.toString());
            }
        }

        /*try (BufferedReader reader = new BufferedReader(new
                FileReader(ARCHIVO_GASTOS))) {
            String linea;
            boolean encontrado = false;
            System.out.println("\n--- Gastos de la categoría '" +
                    categoriaBuscada + "' ---");
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[1].toLowerCase().equals(categoriaBuscada)) {
                    System.out.println("Fecha: " + partes[0] + ", " +
                            "Descripción: " + partes[2] + ", cantidad: $" + partes[3]);
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println("No se encontraron gastos en esta " +
                        "categoría.");
            }
        } catch (IOException e) {
            System.out.println("Error al buscar gastos por categoría: " +
                    e.getMessage());
        }*/
    }
}