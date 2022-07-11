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

import java.util.List;

import br.edu.ifsuldeminas.mch.appFinal.databinding.UserFragmentBinding;
import br.edu.ifsuldeminas.mch.appFinal.db.UserDAO;
import br.edu.ifsuldeminas.mch.appFinal.domain.Cidade;
import br.edu.ifsuldeminas.mch.appFinal.domain.User;
import br.edu.ifsuldeminas.mch.appFinal.parsers.CidadeParser;
import br.edu.ifsuldeminas.mch.appFinal.parsers.CidadeService;
import br.edu.ifsuldeminas.mch.appFinal.parsers.CidadeServiceObserver;
import br.edu.ifsuldeminas.mch.appFinal.services.Conexao;

public class UserFragment extends Fragment implements CidadeServiceObserver{

    private User user;
    private UserFragmentBinding binding;
    private List<Cidade> cities;
    private Cidade city = null;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = UserFragmentBinding.inflate(inflater, container, false);
        textView = (TextView) binding.textRetorno;

        Tarefa tarefa = new Tarefa();
        tarefa.execute("https://viacep.com.br/ws/01001000/json/");
        return binding.getRoot();
    }

    private class Tarefa extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String retorno = Conexao.getDados(strings[0]);
            return retorno;
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CidadeService cityService = new CidadeService();
        cityService.register(this);

        CidadeParser cidadeParser = new CidadeParser();
        List<Cidade> listCidades = cidadeParser.getCities("cidade");


        binding.buttonSaveTaskId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText nomeTIET = binding.nome;
                TextInputEditText cepTIET = binding.cep;

                String nome = nomeTIET.getText().toString();
                nome = nome != null ? nome : "";

                String naturalidade = cepTIET.getText().toString();
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

    @Override
    public void serviceDone(List<Cidade> cities) {
        this.cities = cities;
//        binding.spinner.setAdapter((SpinnerAdapter) new CidadeAdapter(getContext(), cities));
        // Atualiza a lista
    }
}