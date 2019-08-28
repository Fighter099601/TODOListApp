package net.emrekalkan.todolistapp.ui.login;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.core.BaseActivity;
import net.emrekalkan.todolistapp.databinding.ActivityLoginBinding;
import net.emrekalkan.todolistapp.ui.main.MainActivity;

public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> implements LoginInterface {

    private LoginViewModel viewModel;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setClickInterface(this);
    }

    @Override
    public void signupClicked() {
        String username = binding.signupUsername.getText().toString();
        String password = binding.signupPassword.getText().toString();
        String passwordRepeat = binding.signupPasswordRepeat.getText().toString();

        if (viewModel.checkSignUpValidation(username, password, passwordRepeat)) {
            viewModel.doSignUp(username, password);
        }
    }

    @Override
    public void clearSignUpFields() {
        binding.signupUsername.setText("");
        binding.signupPassword.setText("");
        binding.signupPasswordRepeat.setText("");
    }

    @Override
    public void signInClicked() {
        String username = binding.loginUsername.getText().toString();
        String password = binding.loginPassword.getText().toString();

        if (viewModel.checkSignInInputValidations(username, password)) {
            viewModel.checkSignInCredentials(username, password);
        }
    }

    @Override
    public void doSignIn() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
