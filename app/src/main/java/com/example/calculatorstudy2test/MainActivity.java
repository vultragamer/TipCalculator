package com.example.calculatorstudy2test;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import java.text.NumberFormat;
import java.util.Locale;
public class MainActivity extends AppCompatActivity {
    boolean  dividirconta=false;
    float gorjeta_IntPorcent;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       final EditText preco= findViewById(R.id.preco);
       final TextView answerField = findViewById(R.id.resposta);
       final Button btn = findViewById(R.id.calcular_preco);
       final EditText campoGorjeta = findViewById(R.id.porcentGorjeta);
       final CheckBox checkBox = findViewById(R.id.checkBox);
       final CheckBox checkBoxEuro = findViewById(R.id.checkBoxEuro);
       final CheckBox checkBoxReal = findViewById(R.id.checkBoxReal);
       final CheckBox checkBoxDollar = findViewById(R.id.checkBoxDollar);
       LottieAnimationView animationView = findViewById(R.id.animacaoChuva);
       animationView.setAnimation(R.raw.chuva);

        checkBoxReal.setOnClickListener(v -> {
            checkBoxEuro.setChecked(false);
            checkBoxDollar.setChecked(false);
        });
        checkBoxDollar.setOnClickListener(v -> {
            checkBoxReal.setChecked(false);
            checkBoxEuro.setChecked(false);
        });
        checkBoxEuro.setOnClickListener(v -> {
            checkBoxReal.setChecked(false);
            checkBoxDollar.setChecked(false);
        });
       btn.setOnClickListener(v -> {
           animationView.playAnimation();

           functionCheckBox(checkBox);
           String precoRefeicao = preco.getText().toString();
           try {
               gorjeta_IntPorcent = Float.parseFloat(campoGorjeta.getText().toString())/100;
           }catch (Exception e){
               answerField.setText("Por favor insira uma gorjeta válida ou entre em contato com o desenvolvedor!");
           }
           if (checkBoxReal.isChecked()) {
               if (!precoRefeicao.contains("R$"))
                   precoRefeicao = "R$" + precoRefeicao;

               Locale brLocale = new Locale("en", "BR");
               NumberFormat nff = NumberFormat.getCurrencyInstance(brLocale);
               divisaoConta(dividirconta, gorjeta_IntPorcent, precoRefeicao, nff, answerField);
           }
           if (checkBoxDollar.isChecked()) {
               if (!precoRefeicao.contains("$"))
                   precoRefeicao = "$" + precoRefeicao;

               Locale euroLocale = new Locale("en", "US");
               NumberFormat nff = NumberFormat.getCurrencyInstance(euroLocale);
               divisaoConta(dividirconta, gorjeta_IntPorcent, precoRefeicao, nff, answerField);
           }
           if (checkBoxEuro.isChecked()) {
               if (!precoRefeicao.contains("€"))
                   precoRefeicao = "€" + precoRefeicao;

               Locale euroLocale = new Locale("en", "FR");
               NumberFormat nf = NumberFormat.getCurrencyInstance(euroLocale);
               divisaoConta(dividirconta, gorjeta_IntPorcent, precoRefeicao, nf, answerField);
           }
       });
    }
    void functionCheckBox(CheckBox checkBox){
        if(checkBox.isChecked())
            dividirconta=true;
        else
            dividirconta=false;
    }
    void divisaoConta(boolean dividirconta,float gorjeta_IntPorcent,String meal,NumberFormat nf,TextView answerField) {
        float emp = 0.0F;
        try {
            emp = nf.parse(meal).floatValue();
            if (dividirconta == false)
                emp *= 1 + gorjeta_IntPorcent;
            else
                emp *= (1 + gorjeta_IntPorcent) / 2;
            answerField.setText("o preço total é:" + nf.format(emp));
        } catch (Exception e) {
            answerField.setText("Favor iniciar com um número válido ou entrar em contato com o desenvolvedor! ");
        }
    }
}