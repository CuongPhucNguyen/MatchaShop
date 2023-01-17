//package com.example.matchashop.Firebase;
//
//import com.example.matchashop.models.UserModel;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//public class FirebaseUser {
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    FirebaseAuth mAuth = FirebaseAuth.getInstance();
//    UserModel getUser() {
//        db.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                return task.getResult().toObject(UserModel.class);
//            }
//        });
//    }
//}
