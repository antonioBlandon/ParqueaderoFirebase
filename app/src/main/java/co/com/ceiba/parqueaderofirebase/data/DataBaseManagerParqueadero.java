package co.com.ceiba.parqueaderofirebase.data;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;

public class DataBaseManagerParqueadero implements DataBaseManager {

    static DataBaseManagerParqueadero instance;
    static Parqueadero parqueadero;

    public static DataBaseManagerParqueadero getInstance() {
        if (instance == null) {
            instance = new DataBaseManagerParqueadero();
        }
        return instance;
    }

    public static Parqueadero getParqueadero() {
        return parqueadero;
    }

    @Override
    public void read(String nodeRerence, final Activity activity) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(nodeRerence);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parqueadero = dataSnapshot.getValue(Parqueadero.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("OnCancelledParqueadero", databaseError.toString());
            }
        });
    }

    @Override
    public void write(String nodeReference, Object object) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(nodeReference);
        reference.setValue(object);
    }

}
