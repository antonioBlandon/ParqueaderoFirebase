package co.com.ceiba.parqueaderofirebase.registrar;

import android.content.Intent;
import android.os.TestLooperManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.com.ceiba.parqueaderofirebase.R;
import co.com.ceiba.parqueaderofirebase.cobrar.ActivityCobrar;
import co.com.ceiba.parqueaderofirebase.data.DataBaseConstants;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerParqueadero;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerVehiculo;

public class RegistroActivityNew extends AppCompatActivity implements RegistroVehiculo.View{

    private EditText etPlaca;
    private EditText etCilindraje;
    private TextView tvTRM;

    private RegistroVehiculo.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        tvTRM = findViewById(R.id.text_view_ingresar_trm);
        etCilindraje = findViewById(R.id.edit_text_ingresar_cilindraje);
        etPlaca = findViewById(R.id.edit_text_ingresar_placa);
        etPlaca.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(6)});

        Button btnRegistrarIngreso = (Button) findViewById(R.id.btn_ingresar_ingresar_vehiculo);
        btnRegistrarIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        // Set up floating action button
        FloatingActionButton fabIrAcobrar = findViewById(R.id.fab_ingresar_ir_a_cobrar);
        fabIrAcobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCobrar();
            }
        });

        //Se inicia el presentador y su posterior consulta del TRM
        presenter = new RegistroPresenter(this);
        presenter.getTRM(this);

        //"Se pone a escuchar firebase"
        DataBaseManagerVehiculo.getInstance().read(DataBaseConstants.REFERENCE_VEHICLE);
        DataBaseManagerParqueadero.getInstance().read(DataBaseConstants.REFERENCE_PARKING);

    }

    private void registrar() {
        presenter.validarCamposNulos(etPlaca.getText().toString(), etCilindraje.getText().toString());
    }

    @Override
    public void mostrarCobrar() {
        finish();
        startActivity(new Intent(RegistroActivityNew.this, ActivityCobrar.class));
    }

    @Override
    public void showError(String messageError) {
        Toast.makeText(this, messageError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTRM(String trm) {
        tvTRM.setText(trm);
    }

    @Override
    public void showErrorTRM(String messageError) {
        tvTRM.setText(messageError);
    }

    @Override
    public void showRegistroExitoso() {
        etCilindraje.setText("");
        etPlaca.setText("");
        Toast.makeText(this, getString(R.string.ingreso_exitoso), Toast.LENGTH_SHORT).show();
    }
}
