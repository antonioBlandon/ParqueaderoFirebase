package co.com.ceiba.parqueaderofirebase.cobrar;

import java.util.Calendar;
import java.util.List;

import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerImpl;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerParqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;
import co.com.ceiba.parqueaderofirebase.domain.Vigilante;
import co.com.ceiba.parqueaderofirebase.domain.VigilanteImpl;

public class CobroParqueaderoModel implements CobroParqueadero.Model {

    private CobroParqueaderoPresenter presenter;

    public CobroParqueaderoModel(CobroParqueaderoPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getVehiculo(List<Vehiculo> vehiculoList, String placa) {
        Vehiculo vehiculoRetrieve = new Vehiculo();
        for (Vehiculo itemVehiculo : vehiculoList) {
            if (itemVehiculo.getPlaca().equals(placa)) {
                vehiculoRetrieve = itemVehiculo;
                break;
            }
        }
        presenter.validarPlacaExiste(vehiculoRetrieve);
    }

    @Override
    public void registrarSalida(Vehiculo vehiculo, Parqueadero parqueadero) {
        vehiculo.setFechaSalida(Calendar.getInstance().getTimeInMillis());
        vehiculo.setValorPagado(cobrar(vehiculo, DataBaseManagerParqueadero.getParqueadero()));
        DataBaseManagerImpl.getInstance().registrarSalidaParqueadero(vehiculo.getCilindraje() == 0
                , vehiculo
                , DataBaseManagerParqueadero.getParqueadero());
        presenter.showProcesoExitoso(vehiculo);
    }

    public long cobrar(Vehiculo vehiculo, Parqueadero parqueadero) {
        Vigilante vigilante = VigilanteImpl.getInstance();
        long tiempoParqueadero = vigilante.calcularTiempoVehiculoParqueadero(vehiculo.getFechaIngreso(), vehiculo.getFechaSalida());
        long[] diasHoras = vigilante.calcularDiasHoras(tiempoParqueadero);
        vehiculo.setDiasEnParqueadero(diasHoras[0]);
        vehiculo.setHorasEnParqueadero(diasHoras[1]);
        return vigilante.cobrarParqueadero(vehiculo, parqueadero);
    }

}
