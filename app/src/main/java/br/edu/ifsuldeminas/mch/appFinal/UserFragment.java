package br.edu.ifsuldeminas.mch.appFinal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.util.List;


import br.edu.ifsuldeminas.mch.appFinal.databinding.UserFragmentBinding;
import br.edu.ifsuldeminas.mch.appFinal.db.UserDAO;
import br.edu.ifsuldeminas.mch.appFinal.domain.Cidade;
import br.edu.ifsuldeminas.mch.appFinal.domain.User;
import br.edu.ifsuldeminas.mch.appFinal.services.Conexao;


public class UserFragment extends Fragment{

    private User user;
    private UserFragmentBinding binding;
    private List<Cidade> cities;
    private Cidade city = null;
    private TextView textView;
    String retorno = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = UserFragmentBinding.inflate(inflater, container, false);
        textView = (TextView) binding.textRetorno;
        binding.buttonSaveTaskId.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);

        return binding.getRoot();
    }

    private class Tarefa extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                retorno = Conexao.getDados(strings[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return retorno;
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tarefa tarefa = new Tarefa();
                TextInputEditText cepTIET = binding.cep;
                String cep = cepTIET.getText().toString();
                tarefa.execute("https://viacep.com.br/ws/"+cep+"/json/");
                binding.buttonSaveTaskId.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
            }
        });


        binding.buttonSaveTaskId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText nomeTIET = binding.nome;
                TextInputEditText cepTIET = binding.cep;

                String nome = nomeTIET.getText().toString();
                nome = nome != null ? nome : "";

                String naturalidade = retorno;
                naturalidade = naturalidade != null ? naturalidade : "";

                if(nome.equals("")){
                    Toast.makeText(getContext(), R.string.nome_empty,
                            Toast.LENGTH_SHORT).show();
                } else {
                    if(user == null) {
                        User user = new User(0, nome, naturalidade);

                        UserDAO dao = new UserDAO(getContext());
                        dao.save(user);

                        Toast.makeText(getContext(), R.string.nome_saved,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        user.setNome(nome);
                        user.setNaturalidade(naturalidade);

                        UserDAO dao = new UserDAO(getContext());
                        dao.update(user);

                        Toast.makeText(getContext(), R.string.nome_updated,
                                Toast.LENGTH_SHORT).show();
                    }
                }

                NavController navController = Navigation.findNavController(
                        getActivity(), R.id.nav_host_fragment_content_main);

                navController.navigate(R.id.action_TaskFragment_to_MainFragment);
                navController.popBackStack(R.id.UserFragment, true);
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

        Object taskArguments = null;
        if(getArguments() != null){
            taskArguments = getArguments().getSerializable("user");
        }
        if (taskArguments != null) {
            user = (User) taskArguments;
            TextInputEditText nome = binding.nome;
            nome.setText(nome.getText());
            TextInputEditText cep = binding.cep;
            cep.setText(cep.getText());
        }
    }

}