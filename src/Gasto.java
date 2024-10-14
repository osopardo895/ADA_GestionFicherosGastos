import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Gasto {
    private LocalDate fecha;
    private String categoria;
    private String descripcion;
    private double cantidad;

    public Gasto(LocalDate fecha, String categoria, String descripcion, double cantidad) {
        this.fecha = fecha;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String toString(){
        return this.fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+ "," +this.categoria+ "," +this.descripcion+ "," +this.cantidad;
    }
}
