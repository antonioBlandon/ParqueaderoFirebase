package co.com.ceiba.parqueaderofirebase.registrar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.com.ceiba.parqueaderofirebase.cobrar.ActivityCobrar;
import co.com.ceiba.parqueaderofirebase.R;
import co.com.ceiba.parqueaderofirebase.data.DataBaseConstants;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerParqueadero;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerVehiculo;
import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;
import co.com.ceiba.parqueaderofirebase.domain.VigilanteImpl;
import co.com.ceiba.parqueaderofirebase.utils.Utils;

public class ActivityRegistrar extends AppCompatActivity {

    private Activity context = this;
    private boolean isCar;

    private EditText etPlaca;
    private EditText etCilindraje;

    public static Parqueadero parqueadero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        etCilindraje = (EditText) findViewById(R.id.edit_text_ingresar_cilindraje);

        etPlaca = (EditText) findViewById(R.id.edit_text_ingresar_placa);
        etPlaca.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(6)});

        Button btnRegistrarIngreso = (Button) findViewById(R.id.btn_ingresar_ingresar_vehiculo);
        btnRegistrarIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vehiculo vehiculo = validarCamposNulos(etPlaca.getText().toString(), etCilindraje.getText().toString());
                if(vehiculo == null){
                    Toast.makeText(context, getString(R.string.placa_vacia), Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean placaValida = VigilanteImpl.getInstance().validarPlaca(vehiculo.getPlaca(), vehiculo.getFechaIngreso());
                boolean tieneCupo = validarCupo(vehiculo, isCar, parqueadero);
                boolean placaExiste = validarPlacaExiste(DataBaseManagerVehiculo.listVehiculo, vehiculo);
                boolean ingresoExitoso = false;
                if(placaValida && tieneCupo && !placaExiste){
                    DataBaseManagerVehiculo.getInstance().write(null, vehiculo);
                    if (isCar){
                        DataBaseManagerParqueadero.getInstance().write(DataBaseConstants.REFERENCE_PARKING+DataBaseConstants.REFERENCE_COUNT_CAR, parqueadero.getCantidadCarros() + 1);
                    }else{
                        DataBaseManagerParqueadero.getInstance().write(DataBaseConstants.REFERENCE_PARKING+DataBaseConstants.REFERENCE_COUNT_MOTO, parqueadero.getCantidadMotos() + 1);
                    }
                    etCilindraje.setText("");
                    etPlaca.setText("");
                    ingresoExitoso = true;
                }
                showMessage(ingresoExitoso, placaValida, tieneCupo, placaExiste);
            }
        });

        // Set up floating action button
        FloatingActionButton fabIrAcobrar = findViewById(R.id.fab_ingresar_ir_a_cobrar);
        fabIrAcobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ActivityRegistrar.this, ActivityCobrar.class));
            }
        });

        DataBaseManagerVehiculo.getInstance().read(DataBaseConstants.REFERENCE_VEHICLE, context);
        DataBaseManagerParqueadero.getInstance().read(DataBaseConstants.REFERENCE_PARKING, context);
        getTRM();

    }

    public void getTRM() {

        final TextView tvTRM = findViewById(R.id.text_view_ingresar_trm);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://app.docm.co/prod/Dmservices/Utilities.svc/GetTRM";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        tvTRM.setText("$ " + response.replace("\"", ""));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvTRM.setText(getString(R.string.error_cargando_trm));
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void showMessage(boolean ingresoExitoso, boolean placaValida, boolean tieneCupo, boolean placaExiste) {
        if (ingresoExitoso) {
            Toast.makeText(context, getString(R.string.ingreso_exitoso), Toast.LENGTH_SHORT).show();
        } else if ( placaValida && tieneCupo && !placaExiste) { //Solo se muestra si los otros mensajes no se han de mostrar
            Toast.makeText(context, getString(R.string.error_registro), Toast.LENGTH_SHORT).show();
        }
        if (!placaValida) {
            Toast.makeText(context, getString(R.string.vehiculo_no_autorizado), Toast.LENGTH_LONG).show();
        }
        if (!tieneCupo) {
            Toast.makeText(context, getString(R.string.parqueadero_lleno), Toast.LENGTH_LONG).show();
        }
        if (placaExiste) {
            Toast.makeText(context, getString(R.string.placa_existe), Toast.LENGTH_LONG).show();
        }
    }

    public Vehiculo validarCamposNulos(String placa, String cilindraje) {
        if (placa.isEmpty()) {
            return null;
        }else {
            if (cilindraje.isEmpty()){
                isCar = true;
                return new Vehiculo(placa, 0, Calendar.getInstance().getTimeInMillis());
            }else{
                isCar = false;
                return new Vehiculo(placa, Integer.valueOf(cilindraje), Calendar.getInstance().getTimeInMillis());
            }
        }
    }

    public boolean validarCupo(Vehiculo vehiculo, boolean isCar, Parqueadero parqueadero){
        if(isCar){
            return VigilanteImpl.getInstance().validarCantidadCarros(parqueadero.getCantidadCarros());
        }else{
            return VigilanteImpl.getInstance().validarCantidadMotos(parqueadero.getCantidadMotos());
        }
    }

    public boolean validarPlacaExiste(List<Vehiculo> listVehiculo, Vehiculo newVehicle){

        for (Vehiculo vehiculoItem : listVehiculo) {
            if(vehiculoItem.getPlaca().contains(newVehicle.getPlaca())){
                return true;
            }
        }
        return false;
    }

}
