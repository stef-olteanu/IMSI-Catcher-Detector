package DatabaseLogic;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import CallBacks.DatabaseReaderCallBack;
import CallBacks.InternalDatabaseCallBack;
import Checkers.InternalDBChecker;
import Model.Cell;
import Utils.MConstants;

public class FirebaseHelper {
    //region Private Members
    private FirebaseFirestore mDatabaseReference;
    private String mDatabaseResponse;
    //endregion

    //region Constructor
    public FirebaseHelper(){
        mDatabaseReference = FirebaseFirestore.getInstance();
    }
    //endregion


    //region Public Methods
    public void checkDatabaseEntry(final Cell cell, final InternalDatabaseCallBack internalDatabaseCallBack) {
        DocumentReference cellReference = mDatabaseReference.collection("GoodCells").document(cell.GetCid());
        cellReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists() && task.isSuccessful()) {
                    mDatabaseResponse = MConstants.FirebaseHelper.EXISTS_IN_GOOD;
                    internalDatabaseCallBack.OnReturnResponseCallback(mDatabaseResponse);
                } else {
                    DocumentReference cellReference = mDatabaseReference.collection("WarningCells").document(cell.GetCid());
                    cellReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(Task<DocumentSnapshot> task) {
                            if(task.getResult().exists()) {
                                mDatabaseResponse = MConstants.FirebaseHelper.EXISTS_IN_WARNING;
                                internalDatabaseCallBack.OnReturnResponseCallback(mDatabaseResponse);
                            } else {
                                mDatabaseResponse = MConstants.FirebaseHelper.EXISTS_IN_ALERT;
                                internalDatabaseCallBack.OnReturnResponseCallback(mDatabaseResponse);
                            }
                            //latch.countDown();
                        }
                    });
                }
            }
        });
    }

    /**
     *
     * @param cellToInsert
     * The first if verifies if the Cell already exists in the database so as not to create duplicates
     * The second if verifies if the Cell exists in the Alert Collection, in the Warning Collection and in the Good Collection
     * The third if verifies if the Cell exists in the Warning Collection. If so, it will be deleted and added in the Alert Collection only if it doesn't already exist there
     */
    public void insertCell(final Cell cellToInsert){
        final Map<String,Object> dataToInsert = new HashMap<>();
        dataToInsert.put("lat", cellToInsert.GetCellLat());
        dataToInsert.put("lon", cellToInsert.GetCellLong());
        dataToInsert.put("cid", cellToInsert.GetCid());
        dataToInsert.put("lac", cellToInsert.GetLac());
        dataToInsert.put("mcc", cellToInsert.GetMcc());
        dataToInsert.put("mnc", cellToInsert.GetMnc());
        dataToInsert.put("status", cellToInsert.getmCellStatus());

        if(cellToInsert.getmCellStatus() == "GOOD"){
            //Daca exista celula in colectia WARNING se va sterge si va fi stearsa - pentru cazul in care s-a efectuat analiza si s-a stabilit ca celula este GOOD
            DocumentReference cellReference = mDatabaseReference.collection("WarningCells").document(cellToInsert.GetCid());
            cellReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        mDatabaseReference.collection("WarningCells").document(cellToInsert.GetCid()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.i("INFO:", "Stergerea celulei " + cellToInsert.GetCid() + " din colectia Warning s-a efectuat cu succes");
                            }
                        });
                    }

                    //se va introduce in colectia GOOD celula
                    DocumentReference cellReference = mDatabaseReference.collection("GoodCells").document(cellToInsert.GetCid());
                    cellReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (!documentSnapshot.exists()) {
                                mDatabaseReference.collection("GoodCells")
                                        .document(cellToInsert.GetCid())
                                        .set(dataToInsert)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.i("INFO: ", "Celula " + cellToInsert.GetCid() + " a fost adaugata cu succes in baza de date");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("INFO: ", "Adaugarea celulei " + cellToInsert.GetCid() + " in baza de date nu a reusit");
                                            }
                                        });
                            } else {
                                Log.i("INFO: ", "Celula " + cellToInsert.GetCid() + " exista deja in baza de date");
                            }
                        }
                    });
                }
            });

        }
        if(cellToInsert.getmCellStatus() == "WARNING") {
            //Daca celula exista deja in colectia AlertCells sau GoodCells atunci nu se va introduce in WARNING
            DocumentReference cellReference = mDatabaseReference.collection("AlertCells").document(cellToInsert.GetCid());
            cellReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (!documentSnapshot.exists()) {
                        DocumentReference documentReference = mDatabaseReference.collection("GoodCells").document(cellToInsert.GetCid());
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (!documentSnapshot.exists()) {
                                    DocumentReference docRef = mDatabaseReference.collection("WarningCells").document(cellToInsert.GetCid());
                                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (!documentSnapshot.exists()) {
                                                mDatabaseReference.collection("WarningCells")
                                                        .document(cellToInsert.GetCid())
                                                        .set(dataToInsert)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.i("INFO: ", "Celula " + cellToInsert.GetCid() + " a fost adaugata cu succes in baza de date");
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.i("INFO: ", "Adaugarea celulei " + cellToInsert.GetCid() + " in baza de date nu a reusit");
                                                            }
                                                        });
                                            } else {
                                                Log.i("INFO: ", "Celula " + cellToInsert.GetCid() + " exista deja in baza de date");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }

        if(cellToInsert.getmCellStatus() == "ALERT"){
            DocumentReference cellReference = mDatabaseReference.collection("WarningCells").document(cellToInsert.GetCid());
            cellReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        mDatabaseReference.collection("WarningCells").document(cellToInsert.GetCid()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.i("INFO:", "Stergerea celulei "  + cellToInsert.GetCid() +" din colectia Warning s-a efectuat cu succes");
                            }
                        });
                    }
                    DocumentReference docRef = mDatabaseReference.collection("AlertCells").document(cellToInsert.GetCid());
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(!documentSnapshot.exists()){
                                mDatabaseReference.collection("AlertCells")
                                        .document(cellToInsert.GetCid())
                                        .set(dataToInsert)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.i("INFO: ", "Celula "  + cellToInsert.GetCid() +" a fost adaugata cu succes in baza de date");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("INFO: ", "Adaugarea celulei "  + cellToInsert.GetCid() +" in baza de date nu a reusit" );
                                            }
                                        });
                            }else{
                                Log.i("INFO: ", "Celula "  + cellToInsert.GetCid() +" exista deja in baza de date");
                            }
                        }
                    });
                }
            });
        }

    }

    public List<Cell> getAllCells(final DatabaseReaderCallBack databaseReaderCallBack, String collection){
        final List<Cell> databaseCells = new ArrayList<>();
        mDatabaseReference.collection(collection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.i("INFO:", "DOCUMENT ID: " + document.getId() + " a fost descarcat din baza de date");
                                String docId = document.getId();
                                if(!docId.equals("0")) {
                                    Cell mCell = new Cell();
                                    mCell.setmCid(document.get("cid").toString());
                                    mCell.setmLac(document.get("lac").toString());
                                    mCell.setmCellLat(document.get("lat").toString());
                                    mCell.setmCellLong(document.get("lon").toString());
                                    mCell.setmMnc(document.get("mnc").toString());
                                    mCell.setmMcc(document.get("mcc").toString());
                                    mCell.setmCellStatus(document.get("status").toString());
                                    databaseCells.add(mCell);
                                }
                            }
                            databaseReaderCallBack.OnCallBack(databaseCells);
                        }
                    }
                });
        return databaseCells;
    }

    public void getCellByLatLon(final DatabaseReaderCallBack databaseReaderCallBack, final double lat, final double lon){
        final List<Cell> foundCell = new ArrayList<>();
        String[] cellTypes = {"GoodCells", "WarningCells", "AlertCells"};
        for(String cell : cellTypes){
            mDatabaseReference.collection(cell)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    String id = document.getId();
                                    if(!id.equals("0")){
                                        String latit = document.get("lat").toString();
                                        String longit = document.get("lon").toString();
                                        if(Double.parseDouble(latit) == lat && Double.parseDouble(longit) == lon){
                                            Cell mCell = new Cell();
                                            mCell.setmCid(document.get("cid").toString());
                                            mCell.setmLac(document.get("lac").toString());
                                            mCell.setmCellLat(document.get("lat").toString());
                                            mCell.setmCellLong(document.get("lon").toString());
                                            mCell.setmMnc(document.get("mnc").toString());
                                            mCell.setmMcc(document.get("mcc").toString());
                                            mCell.setmCellStatus(document.get("status").toString());
                                            foundCell.add(mCell);
                                            databaseReaderCallBack.OnCallBack(foundCell);
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

    //endregion
}
