package com.example.jogodacapital;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    String[][] estados =
            {{"Acre","Rio Branco"},
            {"Alagoas","Maceió"},
            {"Amazonas","Manaus"},
            {"Bahia","Salvador"},
            {"Ceará","Fortaleza"},
            {"Espírito Santo","Vitória"},
            {"Goiás", "Goiânia"},
            {"Maranhão","São Luís"},
            {"Mato Grosso", "Cuiabá"},
            {"Mato Grosso do Sul", "Campo Grande"},
            {"Paraíba","João Pessoa"},
            {"Paraná", "Curitiba"},
            {"Pernambuco","Recife"},
            {"Piauí","Teresina"},
            {"Rio Grande do Norte","Natal"}};

    ArrayList<Integer> sorteados = new ArrayList<>();
    int pontuacao = 0, rodada=1, sorteado;
    EditText input;
    TextView estadoView, pontuacaoView, rodadaView, resultadoView, msgView;
    Random rand = new Random();
    Button reiniciarButton, adivinharButton, proxButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.editTextCapital);
        estadoView = findViewById(R.id.textViewEstado);
        pontuacaoView = findViewById(R.id.textViewPontuacao);
        rodadaView = findViewById(R.id.textViewRodada);
        resultadoView = findViewById(R.id.textViewResultado);
        msgView = findViewById(R.id.textViewMsg);
        reiniciarButton = findViewById(R.id.buttonReiniciar);
        adivinharButton = findViewById(R.id.buttonAdivinhar);
        proxButton = findViewById(R.id.buttonProx);
        sorteado = rand.nextInt(15);
        sorteados.add(sorteado);
        estadoView.setText(estados[sorteado][0]);
        pontuacaoView.setText(String.valueOf(pontuacao));
        proxButton.setEnabled(false);
    }

    public void adivinhar(View view){
        if(input.length() == 0){
            Toast.makeText(this, "Digite o nome da capital.",Toast.LENGTH_SHORT).show();
            return ;
        }
        adivinharButton.setEnabled(false);
        String capitalCerta = deAccent(estados[sorteado][1].toLowerCase());
        String capitalInserida = deAccent(input.getText().toString().toLowerCase());
        if(capitalCerta.equals(capitalInserida)){
            pontuacao+=10;
            pontuacaoView.setText(String.valueOf(pontuacao));
            resultadoView.setText("Certa resposta!");
        }else{
            resultadoView.setText("Você errou, a resposta certa é: " + estados[sorteado][1]);
        }
        if(rodada==5){
            msgView.setText("Fim de Jogo");
            proxButton.setEnabled(false);
            adivinharButton.setEnabled(false);
        }else{
            proxButton.setEnabled(true);
        }
    }

    public void proxima(View view){
        input.setText("");
        resultadoView.setText("");
        adivinharButton.setEnabled(true);
        proxButton.setEnabled(false);
        rodada+=1;
        rodadaView.setText("Rodada "+rodada+" de 5");
        sorteado = rand.nextInt(15);
        while(sorteados.contains(sorteado)){
            sorteado = rand.nextInt(15);
        }
        sorteados.add(sorteado);
        estadoView.setText(estados[sorteado][0]);
    }

    public void reiniciar(View view){
        input.setText("");
        rodada = 1;
        rodadaView.setText("Rodada "+rodada+" de 5");
        pontuacao = 0;
        pontuacaoView.setText(String.valueOf(pontuacao));
        sorteados = new ArrayList<>();
        proxButton.setEnabled(false);
        adivinharButton.setEnabled(true);
        msgView.setText("");
        resultadoView.setText("");
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }


}