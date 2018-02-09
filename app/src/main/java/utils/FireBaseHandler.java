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
import java.util.Collections;

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


    public void downloadDateList(int limit, final OnDateListlistener onDateListlistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("Date/");

        Query myref2 = mDatabaseRef.orderByKey().limitToLast(limit);


        ValueEventListener valueEventListener = myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> dateArrayList = new ArrayList<String>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String date = snapshot.getValue(String.class);
                    if (date != null) {
                        dateArrayList.add(date);

                    }
                }

                Collections.reverse(dateArrayList);

                onDateListlistener.onDateListDownLoad(dateArrayList, true);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onDateListlistener.onDateListDownLoad(null, false);

            }
        });



    }

    public void downloadQuestionList(int limit, String dateName, final OnQuestionlistener onQuestionlistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("Questions/");

        Query myref2 = mDatabaseRef.orderByChild("questionDateName").equalTo(dateName).limitToLast(limit);



        ValueEventListener valueEventListener = myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Questions> questionsArrayList = new ArrayList<Questions>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Questions questions = snapshot.getValue(Questions.class);
                    if (questions != null) {

                        questions.setQuestionUID(snapshot.getKey());

                    }
                    questionsArrayList.add(questions);
                }

                Collections.reverse(questionsArrayList);

                onQuestionlistener.onQuestionListDownLoad(questionsArrayList, true);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onQuestionlistener.onQuestionListDownLoad(null, false);

            }
        });



    }


    public void uploadQuestion(final Questions questions, final OnQuestionlistener onQuestionlistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("Questions/");

        questions.setQuestionUID(mDatabaseRef.push().getKey());

        DatabaseReference mDatabaseRef1 = mFirebaseDatabase.getReference().child("Questions/" + questions.getQuestionUID());


        mDatabaseRef1.setValue(questions).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onQuestionlistener.onQuestionDownLoad(questions, true);
                onQuestionlistener.onQuestionUpload(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failed to Upload Story", e.getMessage());

                onQuestionlistener.onQuestionUpload(false);
                onQuestionlistener.onQuestionDownLoad(null, false);
            }
        });


    }


    public void uploadDateName(final String date, final OnDatalistener onDatalistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("Date/");


        DatabaseReference mDatabaseRef1 = mFirebaseDatabase.getReference().child("Date/" + mDatabaseRef.push().getKey());

        //DatabaseReference mDatabaseRef1 = mFirebaseDatabase.getReference().child("Topic/");


        mDatabaseRef1.setValue(date).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onDatalistener.onDataDownLoad(date, true);
                onDatalistener.onDataUpload(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failed to Upload Story", e.getMessage());

                onDatalistener.onDataUpload(false);
                onDatalistener.onDataDownLoad(null, false);
            }
        });


    }


    public interface OnDatalistener {


        public void onDataDownLoad(String itemData, boolean isSuccessful);

        public void onDataUpload(boolean isSuccessful);
    }

    public interface OnDateListlistener {


        public void onDateDownLoad(String test, boolean isSuccessful);

        public void onDateListDownLoad(ArrayList<String> testList, boolean isSuccessful);


        public void onDateUpload(boolean isSuccessful);
    }

    public interface OnQuestionlistener {


        public void onQuestionDownLoad(Questions questions, boolean isSuccessful);

        public void onQuestionListDownLoad(ArrayList<Questions> questionList, boolean isSuccessful);


        public void onQuestionUpload(boolean isSuccessful);
    }


}
