package co.com.ceiba.parqueaderofirebase.data.entities;

public class Vehiculo {

    private int cilindraje;
    private long fechaIngreso;
    private long fechaSalida;
    private String placa;
    private long diasEnParqueadero;
    private long horasEnParqueadero;
    private long valorPagado;

    public Vehiculo() {
    }

    public Vehiculo(int cilindraje, long fechaIngreso, long fechaSalida, String placa, long diasEnParqueadero, long horasEnParqueadero, long valorPagado) {
        this.cilindraje = cilindraje;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.placa = placa;
        this.diasEnParqueadero = diasEnParqueadero;
        this.horasEnParqueadero = horasEnParqueadero;
        this.valorPagado = valorPagado;
    }

    public Vehiculo(String placa, int cilindraje, long fechaIngreso) {
        this.placa = placa;
        this.cilindraje = cilindraje;
        this.fechaIngreso = fechaIngreso;
    }

    public int getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(int cilindraje) {
        this.cilindraje = cilindraje;
    }

    public long getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(long fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public long getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(long fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public long getDiasEnParqueadero() {
        return diasEnParqueadero;
    }

    public void setDiasEnParqueadero(long diasEnParqueadero) {
        this.diasEnParqueadero = diasEnParqueadero;
    }

    public long getHorasEnParqueadero() {
        return horasEnParqueadero;
    }

    public void setHorasEnParqueadero(long horasEnParqueadero) {
        this.horasEnParqueadero = horasEnParqueadero;
    }

    public long getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(long valorPagado) {
        this.valorPagado = valorPagado;
    }
}
