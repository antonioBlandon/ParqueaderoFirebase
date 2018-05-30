package co.com.ceiba.parqueaderofirebase.domain;

import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

public interface Vigilante {

    boolean validarCantidadCarros(int cantidadCarrosActual);

    boolean validarCantidadMotos(int cantidadMotosActual);

    boolean validarPlaca(String placa, long fechaIngreso);

    long calcularTiempoVehiculoParqueadero(long fechaIngreso, long fechaSalida);

    long cobrarParqueadero(Vehiculo vehiculo, Parqueadero parqueadero);

    long[] calcularDiasHoras(long horas);

}
