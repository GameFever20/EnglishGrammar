package utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Aisha on 1/31/2018.
 */

public class FireBaseHandler {


    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mFirebaseDatabase;

    public FireBaseHandler() {

        mFirebaseDatabase = FirebaseDatabase.getInstance();

    }

    public void downloadItemData(String TopicName, final String subTopicName, final OnDatalistener onDatalistener) {


        DatabaseReference myRef = mFirebaseDatabase.getReference().child(TopicName + "/" + subTopicName);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String textData = dataSnapshot.getValue(String.class);

                onDatalistener.onDataDownLoad(textData, true);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onDatalistener.onDataDownLoad(null, false);
            }
        });


    }

    public void downloadItemData(String TopicName, final OnDatalistener onDatalistener) {


        DatabaseReference myRef = mFirebaseDatabase.getReference().child(TopicName + "/");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String textData = dataSnapshot.getValue(String.class);

                onDatalistener.onDataDownLoad(textData, true);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onDatalistener.onDataDownLoad(null, false);
            }
        });


    }





    public interface OnDatalistener {


        public void onDataDownLoad(String itemData, boolean isSuccessful);

        public void onDataUpload(boolean isSuccessful);
    }
}
