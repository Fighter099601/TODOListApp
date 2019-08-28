package net.emrekalkan.todolistapp.core;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import net.emrekalkan.todolistapp.BR;
import net.emrekalkan.todolistapp.ui.main.MainActivity;

public abstract class BaseFragment<ViewModel extends BaseViewModel, DataBinding extends ViewDataBinding> extends Fragment {

    protected ViewModel viewModel;
    protected DataBinding binding;
    protected Context context;
    protected Activity activity;

    private Toast toast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = getViewModel();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setVariable(BR.viewModel, viewModel);
        binding.setLifecycleOwner(this);
        //binding.executePendingBindings();
        init();
    }

    protected abstract void init();
    protected abstract int getLayoutRes();
    protected abstract ViewModel getViewModel();

    public void showToast(String message) {
        activity.runOnUiThread(() -> {
            if (toast != null)
                toast.cancel();
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    public void popBack() {
        activity.runOnUiThread(() -> activity.onBackPressed());
    }
}
