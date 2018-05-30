package co.com.ceiba.parqueaderofirebase.data;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.com.ceiba.parqueaderofirebase.cobrar.ActivityCobrar;
import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;
import co.com.ceiba.parqueaderofirebase.registrar.ActivityRegistrar;

public class DataBaseManagerParqueadero implements DataBaseManager {

    static DataBaseManagerParqueadero instance;
    public static Parqueadero parqueadero;

    public static DataBaseManagerParqueadero getInstance() {
        if (instance == null) {
            instance = new DataBaseManagerParqueadero();
        }
        return instance;
    }

    @Override
    public void read(String nodeRerence, final Activity activity) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(nodeRerence);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parqueadero = dataSnapshot.getValue(Parqueadero.class);
                Log.e("PARQUEADERO", parqueadero.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void write(String nodeReference, Object object) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(nodeReference);
        reference.setValue(object);
    }

}
