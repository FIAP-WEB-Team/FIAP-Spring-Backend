package br.com.fiap.spring.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class FirebaseRepository {
    public <T> T getSingleDocumentFromCollection(Class<T> classType, String collectionPath, String documentID)
            throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentSnapshot> future = dbFirestore.collection(collectionPath).document(documentID).get();

        DocumentSnapshot snapshot = future.get();

        if (snapshot.exists())
            return snapshot.toObject(classType);
        return null;
    }

    public <T> String createSingleDocumentInCollection(String collectionPath, T document)
            throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> future = dbFirestore.collection(collectionPath).add(document);

        DocumentReference result = future.get();

        return result.getId();
    }

    public <T> List<T> getMultipleDocumentsFromCollection(Class<T> classType, String collectionPath, int limit,
                                                          Map<String, String> filters)
            throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Query query = dbFirestore.collection(collectionPath).limit(limit);
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            query = query.whereEqualTo(entry.getKey(), entry.getValue());
        }
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot snapshot = future.get();

        if (!snapshot.isEmpty()) {
            return snapshot.getDocuments().stream().map(document -> document.toObject(classType)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
