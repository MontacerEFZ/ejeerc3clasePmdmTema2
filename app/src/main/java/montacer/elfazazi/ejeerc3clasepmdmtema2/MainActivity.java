package montacer.elfazazi.ejeerc3clasepmdmtema2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.query.In;

import java.sql.SQLException;
import java.util.ArrayList;

import montacer.elfazazi.ejeerc3clasepmdmtema2.Configuraciones.Configuracion;
import montacer.elfazazi.ejeerc3clasepmdmtema2.databinding.ActivityMainBinding;
import montacer.elfazazi.ejeerc3clasepmdmtema2.helpers.AlumnosHelper;
import montacer.elfazazi.ejeerc3clasepmdmtema2.modelos.Alumno;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Alumno> listaAlumnos;
    private AlumnosHelper helper;
    private Dao<Alumno, Integer> daoAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listaAlumnos = new ArrayList<>();
        helper = new AlumnosHelper(this, Configuracion.BD_NAME, null, Configuracion.BD_VERSION);

        if (helper != null){
            daoAlumno = helper.getDaoAlumnos();
            try {
                listaAlumnos.addAll(daoAlumno.queryForAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        actualizarNotaFinal();

        binding.btnInsertarMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarAlumno();
            }
        });

        binding.btnConsultarMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (binding.txtPosicionMain.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "falta el identificador", Toast.LENGTH_SHORT).show();
            }else {
                try {
                   Alumno a =  daoAlumno.queryForId(Integer.parseInt(binding.txtPosicionMain.getText().toString()));
                    if (a == null){
                        Toast.makeText(MainActivity.this, "Faltnan datos", Toast.LENGTH_SHORT).show();
                    }else{
                        binding.txtNombreMain.setText(a.getNombre());
                        binding.txtApellidosMain.setText(a.getApellidos());
                        binding.txtNota1Main.setText(Configuracion.NF.format(a.getNota1()));
                        binding.txtNota2Main.setText(Configuracion.NF.format(a.getNota2()));
                        binding.txtNota3Main.setText(Configuracion.NF.format(a.getNota3()));
                        binding.lbNotaFinalMain.setText("nota final: " + Configuracion.NF.format(a.getNotaFinal()));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            }
        });

        binding.btnModificarMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.btnEliminarMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void actualizarNotaFinal() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    float nota1 = Float.parseFloat(binding.txtNota1Main.getText().toString());
                    float nota2 = Float.parseFloat(binding.txtNota2Main.getText().toString());
                    float nota3 = Float.parseFloat(binding.txtNota3Main.getText().toString());
                    float notaFinal = (nota1 + nota2 + nota3) / 3;

                    binding.lbNotaFinalMain.setText("nota final: " + Configuracion.NF.format(notaFinal));
                }catch (Exception e){
                    //sin mensaje de error
                }
            }
        };

        binding.txtNota1Main.addTextChangedListener(textWatcher);
        binding.txtNota2Main.addTextChangedListener(textWatcher);
        binding.txtNota3Main.addTextChangedListener(textWatcher);
    }

    private void insertarAlumno() {
        if (binding.txtNombreMain.getText().toString().isEmpty() || binding.txtApellidosMain.getText().toString().isEmpty() ||
                binding.txtNota1Main.getText().toString().isEmpty() || binding.txtNota2Main.getText().toString().isEmpty() ||
                binding.txtNota3Main.getText().toString().isEmpty()){
            Toast.makeText(this, "faltan datos", Toast.LENGTH_SHORT).show();
        }else{
            Alumno a = new Alumno();
            a.setNombre(binding.txtNombreMain.getText().toString());
            a.setApellidos(binding.txtApellidosMain.getText().toString());
            a.setNota1(Float.parseFloat(binding.txtNota1Main.getText().toString()));
            a.setNota2(Float.parseFloat(binding.txtNota2Main.getText().toString()));
            a.setNota3(Float.parseFloat(binding.txtNota3Main.getText().toString()));

            try {
                daoAlumno.create(a);
                a.setId(daoAlumno.extractId(a));
                listaAlumnos.add(a);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            limpiar();

        }
    }

    private void limpiar() {
        binding.txtNombreMain.setText("");
        binding.txtApellidosMain.setText("");
        binding.txtNota1Main.setText("");
        binding.txtNota2Main.setText("");
        binding.txtNota3Main.setText("");
        binding.lbNotaFinalMain.setText("nota final: ");
    }
}