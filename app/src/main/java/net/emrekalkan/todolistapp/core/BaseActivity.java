package net.emrekalkan.todolistapp.core;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;

public abstract class BaseActivity<ViewModel extends BaseViewModel, DataBinding extends ViewDataBinding> extends AppCompatActivity {

    public ViewModel viewModel;
    public DataBinding binding;

    Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    protected abstract int getLayoutRes();
    protected abstract ViewModel getViewModel();

    public void initBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        this.viewModel = viewModel == null ? getViewModel() : viewModel;
        binding.setVariable(BR.viewModel, viewModel);
        //binding.executePendingBindings();
    }

    public void showToast(String message) {
        this.runOnUiThread(() -> {
            if (toast != null)
                toast.cancel();
            toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
            toast.show();
        });
    }
}
