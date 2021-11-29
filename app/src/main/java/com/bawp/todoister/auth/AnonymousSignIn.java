package com.bawp.todoister.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bawp.todoister.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

/**
 * Activity to demonstrate anonymous login and account linking (with an email/password account).
 */
public class AnonymousSignIn {

    private static final String TAG = "AnonymousAuth";

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]


    // [START on_start_check_user]
    // [END on_start_check_user]

    public void signInAnonymously() {
        // [START signin_anonymously]
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInAnonymously:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        linkAccount();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInAnonymously:failure", task.getException());
                        updateUI(null);
                    }
                });
        // [END signin_anonymously]
    }

    private void linkAccount() {
        AuthCredential credential = EmailAuthProvider.getCredential("", "");

        // [START link_credential]
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "linkWithCredential:success");
                        FirebaseUser user = task.getResult().getUser();
                        updateUI(user);

                    } else {
                        Log.w(TAG, "linkWithCredential:failure", task.getException());
                        updateUI(null);
                    }
                });
        // [END link_credential]
    }

    private void updateUI(FirebaseUser user) {

    }
}