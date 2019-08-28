package net.emrekalkan.todolistapp.ui.login;

public interface LoginInterface {
    void signupClicked();
    void showToast(String message);
    void clearSignUpFields();

    void signInClicked();

    void doSignIn();
}
