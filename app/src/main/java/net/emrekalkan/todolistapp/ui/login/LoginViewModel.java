package net.emrekalkan.todolistapp.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.TODOListApp;
import net.emrekalkan.todolistapp.core.BaseViewModel;
import net.emrekalkan.todolistapp.db.entity.UserEntity;
import net.emrekalkan.todolistapp.utils.Constants;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel<LoginInterface> {

    public LoginViewModel(@NonNull Application application) {
        super(application);
        ((TODOListApp) application).component.inject(this);
    }

    private ObservableField<Integer> layoutType = new ObservableField<>(0);
    public ObservableField<Integer> getLayoutType() {
        return layoutType;
    }

    public void textSignUpClicked() {
        layoutType.set(Constants.LoginLayoutType.SIGN_UP);
    }

    public void textSignInClicked() {
        layoutType.set(Constants.LoginLayoutType.SIGN_IN);
    }

    boolean checkSignUpValidation(String username, String password, String passwordRepeat) {
        String message = "";
        boolean isValid = true;

        if (username.equals("") || username.length() < 3) {
            message = "Username must be at least 3 characters";
            isValid = false;
        }else if (password.equals("")) {
            message = "Please enter a valid password";
            isValid = false;
        }else if (password.length() < 6) {
            message = "Password must be at least 6 characters";
            isValid = false;
        }else if (!password.equals(passwordRepeat)) {
            message = "Password and password repeat must be equal";
            isValid = false;
        }

        if (!message.equals("")) {
            getClickInterface().showToast(message);
        }
        return isValid;
    }

    public void signUp() {
        getClickInterface().signupClicked();
    }

    void doSignUp(String username, String password) {
        disposable.add(
                Single.just(userRepo)
                .subscribeOn(Schedulers.io())
                .subscribe(userRepo -> {
                    String mUsername = userRepo.checkUsername(username);
                    if (mUsername == null) {
                        UserEntity userEntity = new UserEntity();
                        userEntity.setUsername(username);
                        userEntity.setPassword(password);
                        userRepo.insertUser(userEntity);
                        getClickInterface().showToast(getApplication().getString(R.string.signUpSuccess));
                        layoutType.set(Constants.LoginLayoutType.SIGN_IN);
                        getClickInterface().clearSignUpFields();
                    }else {
                        getClickInterface().showToast(getApplication().getString(R.string.userAlreadyExists));
                    }
                }, Throwable::fillInStackTrace)
        );
    }

    public void signIn() {
        getClickInterface().signInClicked();
    }

    void checkSignInCredentials(String username, String password) {
        disposable.add(
                Single.just(userRepo)
                        .subscribeOn(Schedulers.io())
                        .subscribe(userRepo -> {
                            UserEntity userEntity = userRepo.checkUserCredentials(username, password);
                            if (userEntity != null) {
                                userSharedPreferencesHelper.setSharedPreferences(userEntity);
                                getClickInterface().doSignIn();
                            } else {
                                getClickInterface().showToast(getApplication().getString(R.string.signInCredentialsFailed));
                            }
                        }, Throwable::printStackTrace)
        );
    }

    boolean checkSignInInputValidations(String username, String password) {
        if (username.equals("")) {
            getClickInterface().showToast(getApplication().getString(R.string.signInUsernameFailed));
            return false;
        }
        if (password.equals("")) {
            getClickInterface().showToast(getApplication().getString(R.string.signInPasswordFailed));
            return false;
        }

        return true;
    }
}
