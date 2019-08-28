package net.emrekalkan.todolistapp.ui.main;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.core.BaseActivity;
import net.emrekalkan.todolistapp.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements MainInterface {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }
    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    private NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.mainToolbar);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.mainToolbar, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavigationUI.onNavDestinationSelected(item, navController);
        return super.onOptionsItemSelected(item);
    }

    public void navigate(int action, Bundle bundle) {
        this.runOnUiThread(() -> navController.navigate(action, bundle));
    }

    public void setMenuItemVisibility(int menuId, boolean visibility) {
        this.runOnUiThread(() -> binding.mainToolbar.getMenu().findItem(menuId).setVisible(visibility));
    }
}
