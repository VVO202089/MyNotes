package com.geekbrains.mynotes.autorization;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.geekbrains.mynotes.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class GoogleAutorization {

    private static final int RC_SIGN_IN = 40404;
    private static final String TAG = "GoogleAuth";
    // Клиент для регистрации пользователя через Google
    private static GoogleSignInClient googleSignInClient;

    private Activity activity;

    public GoogleAutorization(Activity activity) {
        this.activity = activity;
    }

    public static int getRcSignIn() {
        return RC_SIGN_IN;
    }

    // инициируем вход пользователя
    public Intent signIn(GoogleSignInClient googleSignInClient) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        return signInIntent;
        //activity.startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    public void initGoogleSign() {
        // конфигурация запроса на регистрацию пользователя, чтобы получить
        // идентификатор пользователя, его почту и основной профайл
        // (регулируется параметром)
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Получаем клиента для регистрации и данные по клиенту
        googleSignInClient = GoogleSignIn.getClient(activity, gso);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }
    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount account =
                    completedTask.getResult(ApiException.class);

            // Регистрация прошла успешно
            //disableSign();
            //updateUI("Hello, ".concat(account.getEmail()));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure
            // reason. Please refer to the GoogleSignInStatusCodes class
            // reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
