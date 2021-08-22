package com.geekbrains.mynotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class StartFragmentAuth extends Fragment {

    // Используется, чтобы определить результат Activity регистрации
    // Google
    private static final int RC_SIGN_IN = 40404;
    private static final String TAG = "GoogleAuth";

    //private Navigation navigation;
    private Context myContext;
    private FragmentList fragmentList;

    // Клиент для регистрации пользователя через Google
    private GoogleSignInClient googleSignInClient;

    // кнопка регистрации через Google
    private Button buttonSignIn;
    private TextView emailView;
    private Button buttonSignOut;
    private Button continue_;

    //private OnFragmentListListener fragmentListListener;

    /*interface OnFragmentListListener{
        void replaceStartFragment();
    }*/

    public static StartFragmentAuth newInstance() {
        StartFragmentAuth fragmentAuth = new StartFragmentAuth();
        return fragmentAuth;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        initGoogleSign();
        initView(view);
        enableSign();
        return view;
    }

    // событие связки фрагмента с активити
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext = context;
        //fragmentListListener = (OnFragmentListListener)context;
        // получим навигацию по приложению, чтобы перейти  на фрагмент со списком карточек
        // получим активити
        //MainActivity activity = (MainActivity) context;
        //navigation = activity.getNavigation();
    }

    // сбрасывание связки фрагмента с активити
    @Override
    public void onDetach() {
        //navigation = null;
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        // проверим, входил ли пользователь в приложение через Google
        GoogleSignInAccount account = null;
        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        account = GoogleSignIn.getLastSignedInAccount(myContext);
        // }
        if (account != null) {
            // действия, при залогиненном пользователе
        }
    }

    //Здесь получим ответ от системы, что пользователь вошел
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // Когда сюда возвращается TASK, результаты аутентификаи уже готовы
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount account =
                    completedTask.getResult(ApiException.class);

            // Регистрация прошла успешно
            disableSign();
            updateUI("Hello, ".concat(account.getEmail()));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure
            // reason. Please refer to the GoogleSignInStatusCodes class
            // reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    // Запишем полученный Email
    private void updateUI(String str) {
        emailView.setText(str);
    }

    private void disableSign() {
        buttonSignIn.setEnabled(false);
        continue_.setEnabled(true);
    }

    // инициализация запроса на аутентификацию
    //@RequiresApi(api = Build.VERSION_CODES.M)
    private void initGoogleSign() {
        // конфигурация запроса на регистрацию пользователя, чтобы получить
        // идентификатор пользователя, его почту и основной профайл
        // (регулируется параметром)
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Получаем клиента для регистрации и данные по клиенту
        googleSignInClient = GoogleSignIn.getClient(myContext, gso);
    }

    // инициализация пользовательских элементов
    private void initView(View view) {
        // кнопка входа
        buttonSignIn = view.findViewById(R.id.sign_in_google);
        buttonSignIn.setOnClickListener(v -> {
            signIn();
        });
        emailView = view.findViewById(R.id.email);
        // кнопка выхода
        buttonSignOut = view.findViewById(R.id.sign_out_button);
        buttonSignOut.setOnClickListener(v -> {
            signOut();
        });
        // по нажатию кнопки "Продолжить" будем показывать фрагмент "fragment_list"
        continue_ = view.findViewById(R.id.continue_);
        continue_.setOnClickListener(v -> {
            // открываем фрагмент со списком заметок
            replaceStartFragment();
            //fragmentListListener.replaceStartFragment();
            //navigation.addFragment(StartFragmentAuth.newInstance(), false);
        });
    }

    private void replaceStartFragment() {

        // передадим параметры
        Bundle bundle = new Bundle();
        fragmentList = new FragmentList();
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .replace(R.id.fragment_list,fragmentList)
                .addToBackStack(null);
        fragmentTransaction.commit();
        //Получить менеджер фрагментов
        //FragmentManager fragmentManager = getChildFragmentManager();
        // Открыть транзакцию
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
        //       .replace(R.id.fragment_list_insert, frNotes)
        //      .addToBackStack(null)
        //     .commit();
        //fragmentTransaction.replace(R.id.fragment_list_insert, fragmentList);
        //fragmentTransaction.replace((orientation == Configuration.ORIENTATION_PORTRAIT)
        //       ?R.id.fragment_notes_insert :R.id.fragment_list_insert_land, fragment);
        //fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        //fragmentTransaction.commit();
    }

    // инициируем вход пользователя
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // инициируем выход пользователя
    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener((Activity) myContext, task -> {
                    // очищаем данные о входе
                    updateUI("");
                    enableSign();
                    // отключаем google ккаунт от приложения
                    revokeAccess();
                });
    }

    // при удалении google аккаунта должен быть выход из приложения
    private void revokeAccess() {
        googleSignInClient.revokeAccess()
                .addOnCompleteListener((Activity) myContext, task -> {

                });
    }

    // Разрешить аутентификацию и запретить остальные действия
    private void enableSign() {
        buttonSignIn.setEnabled(true);
        continue_.setEnabled(false);
    }
}
