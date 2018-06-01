package co.com.ceiba.parqueaderofirebase.registrar;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;

import co.com.ceiba.parqueaderofirebase.R;
import co.com.ceiba.parqueaderofirebase.architecture.VolleyManager;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerImpl;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerParqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

public class RegistroModel implements RegistroVehiculo.Model {

    private RegistroVehiculo.Presenter presenter;

    public RegistroModel(RegistroVehiculo.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Vehiculo crearVehiculo(String placa, int cilindraje) {
        return new Vehiculo(placa, cilindraje, Calendar.getInstance().getTimeInMillis());
    }

    @Override
    public void getTRM(final Context context) {
        String url = "http://app.docm.co/prod/Dmservices/Utilities.svc/GetTRM";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        presenter.showTRM("$ " + response.replace("\"", ""));
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.showErrorTRM(context.getString(R.string.error_cargando_trm));
            }
        });

        // Add the request to the RequestQueue.
        VolleyManager.getInstance(context).addToRequestQueue(stringRequest);
    }

    @Override
    public void agregarVehiculo(Vehiculo vehiculo, Parqueadero parqueadero) {
        boolean isCar = vehiculo.getCilindraje() == 0;
        DataBaseManagerImpl.getInstance().agregarVehiculo(isCar, vehiculo, DataBaseManagerParqueadero.getParqueadero());
        presenter.showRegistroExitoso();
    }

}
