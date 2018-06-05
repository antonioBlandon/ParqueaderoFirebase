package co.com.ceiba.parqueaderofirebase.cobrar;

import java.util.List;

import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

public interface CobroParqueadero {

    interface View {
        void showProcesoExitoso();

        void showResumen(Vehiculo vehiculo);

        void setUpErrorSearch(int idMessageError);

        void setUpInfo(Vehiculo vehiculo);
    }

    interface Presenter {
        void buscarPlaca(String placa);

        void cobrar();

        void validarCamposNulos(String placa);

        void validarPlacaExiste(Vehiculo vehiculo);

        void showProcesoExitoso(Vehiculo vehiculo);
    }

    interface Model {
        void getVehiculo(List<Vehiculo> vehiculoList, String placa);

        void registrarSalida(Vehiculo vehiculo, Parqueadero parqueadero);
    }

}
