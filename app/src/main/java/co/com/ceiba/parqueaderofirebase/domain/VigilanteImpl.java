package co.com.ceiba.parqueaderofirebase.domain;

import java.util.Calendar;

import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

public class VigilanteImpl implements Vigilante {

    static VigilanteImpl reference;

    public static VigilanteImpl getInstance() {
        if (reference == null) {
            reference = new VigilanteImpl();
        }
        return reference;
    }

    private VigilanteImpl() {
    }

    @Override
    public boolean validarCantidadCarros(int cantidadCarrosActual) {

        int cantidadCarros = cantidadCarrosActual + 1;
        return cantidadCarros <= 20;

    }

    @Override
    public boolean validarCantidadMotos(int cantidadMotosActual) {
        int cantidadMotos = cantidadMotosActual + 1;
        return cantidadMotos <= 10;
    }

    @Override
    public boolean validarPlaca(String placa, long fechaIngreso) {

        String primeraLetra = placa.substring(0, 1);
        if (primeraLetra.equals("A")) {
            //Dias validos
            int lunes = 2;
            int domingo = 1;

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(fechaIngreso);
            int diaIngresoDeLaSemana = calendar.get(Calendar.DAY_OF_WEEK);
            return ((diaIngresoDeLaSemana == domingo) || (diaIngresoDeLaSemana == lunes));

        }
        return true;

    }

    @Override
    public long calcularTiempoVehiculoParqueadero(long fechaIngreso, long fechaSalida) {
        long tiempo = fechaSalida - fechaIngreso;
        double tiempoEnSegundos = (float) tiempo / 1_000;
        return (Double.valueOf(Math.ceil(tiempoEnSegundos / 3600))).longValue();
    }

    @Override
    public long cobrarParqueadero(Vehiculo vehiculo, Parqueadero parqueadero) {

        long valor = 0;
        if (vehiculo.getCilindraje() != 0) {
            valor = (vehiculo.getDiasEnParqueadero() * parqueadero.getValorDiaMoto())
                    + (vehiculo.getHorasEnParqueadero() * parqueadero.getValorHoraMoto());
            if (vehiculo.getCilindraje() > parqueadero.getTopeCilindraje()) {
                valor = valor + parqueadero.getAdicionCilindraje();
            }
        } else {
            valor = (vehiculo.getDiasEnParqueadero() * parqueadero.getValorDiaCarro())
                    + (vehiculo.getHorasEnParqueadero() * parqueadero.getValorHoraCarro());
        }
        return valor;

    }

    @Override
    public long[] calcularDiasHoras(long horas) {
        long dias = 0;
        long[] diasHoras = new long[2];
        while (horas >= 0) {

            if (horas > 9) {
                dias++;
                horas = horas - 24;
            } else {
                break;
            }

        }
        if (horas < 0) {
            horas = 0;
        }
        diasHoras[0] = dias;
        diasHoras[1] = horas;
        return diasHoras;
    }

}
