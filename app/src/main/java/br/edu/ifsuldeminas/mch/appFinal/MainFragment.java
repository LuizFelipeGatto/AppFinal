package br.edu.ifsuldeminas.mch.appFinal;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import br.edu.ifsuldeminas.mch.appFinal.databinding.FragmentMainBinding;
import br.edu.ifsuldeminas.mch.appFinal.db.UserDAO;
import br.edu.ifsuldeminas.mch.appFinal.domain.User;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerForContextMenu(binding.todoList);

        binding.todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long l) {

                User user = (User) binding.todoList.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);

                NavController navController = Navigation.findNavController(
                        getActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.action_MainFragment_to_TaskFragment, bundle);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTasks();
    }

    private void updateTasks() {
        UserDAO dao = new UserDAO(getContext());
        List<User> users = dao.listAll();

        ArrayAdapter adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, users);

        binding.todoList.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem item = menu.add(R.string.delete_user);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info =
                        (AdapterView.AdapterContextMenuInfo) menuInfo;

                User taskSelected = (User) binding.todoList.getItemAtPosition(
                        info.position);

                UserDAO taskDAO = new UserDAO(getContext());
                taskDAO.delete(taskSelected);

                Toast.makeText(getContext(), R.string.user_deleted,
                        Toast.LENGTH_SHORT).show();

                updateTasks();
                return true;
            }
        });
    }
}