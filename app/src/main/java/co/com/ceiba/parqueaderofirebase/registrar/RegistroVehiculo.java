package co.com.ceiba.parqueaderofirebase.registrar;

import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

public interface RegistroVehiculo {

    interface View {
        void mostrarCobrar();

        void showError(int idMessageError);

        void showErrorTRM();

        void showRegistroExitoso();

        void showTRM(String trm);
    }

    interface Presenter {
        void getTRM();

        void showTRM(String trm);

        void showErrorTRM();

        void showRegistroExitoso();

        void validarCamposNulos(String placa, String cilindraje);
    }

    interface Model {
        Vehiculo crearVehiculo(String placa, int cilindraje);

        void getTRM();

        void agregarVehiculo(Vehiculo vehiculo, Parqueadero parqueadero);
    }

}
