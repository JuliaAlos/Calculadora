package edu.upc.dsa.calculadora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected EditText textNumber;
    protected EditText textData;
    private Double numA;
    private Double numB;
    private String numChar;
    private String operacio;
    private boolean piPress;
    private boolean modeDegree;
    private boolean newOp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout constraintLayout= findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable=(AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();


        numA = null;
        numB = null;
        numChar = null;
        operacio = null;
        piPress=false;
        modeDegree=true;
        newOp=false;

        textNumber = findViewById(R.id.textResult);
        textData = findViewById(R.id.textData);
        textNumber.setShowSoftInputOnFocus(false);
        textData.setShowSoftInputOnFocus(false);
        textNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getString(R.string.display).equals(textNumber.getText().toString())){
                    textNumber.setText("");
                }
            }
        });

    }

    public void onClick(View v) {
        if(newOp){
            numA=null;operacio =null;numB=null;
            textData.setText("");
            textNumber.setText("");
            newOp=false;
        }
        if(!piPress) {
            Button button = (Button) v;
            if (numA == null) {
                try {
                     numChar = button.getText().toString();
                    numA = Double.parseDouble(numChar);
                }catch(Exception e){
                    numChar = "0.";
                    numA = Double.parseDouble(numChar);
                }
                textNumber.setText(numChar);

            } else if (operacio == null) {
                numChar += button.getText().toString();
                textNumber.setText(numChar);
                numA = Double.parseDouble(numChar);
            } else if (numB == null) {
                try {
                    numChar = button.getText().toString();
                    numB = Double.parseDouble(numChar);
                }catch(Exception e){
                    numChar = "0.";
                    numB = Double.parseDouble(numChar);
                }
                textNumber.setText(numChar);

                if (operacio == "equal") {
                    operacio = null;
                    numA = Double.parseDouble(numChar);
                    textData.setText("");
                } else {
                    textData.setText(numA.toString() + operacio);
                    numB = Double.parseDouble(numChar);
                }
            } else {
                numChar += button.getText().toString();
                textNumber.setText(numChar);
                numB = Double.parseDouble(numChar);
            }
        }

        //Log.d("MYAPP", "He pulsado el boton");
    }
    public void onOperation(View v){
        Button button = (Button) v;

        if(operacio!=null && numB!=null) {
            switch (operacio) {
                case "+":
                    numA = numA + numB;
                    break;
                case "-":
                    numA = numA - numB;
                    break;
                case "×":
                    numA = numA * numB;
                    break;
                case "÷":
                    numA = numA / numB;
                    break;

            }
            numB = null;


        }
        textData.setText(numA.toString());
        operacio = button.getText().toString();
        textNumber.setText(operacio);
        newOp=false;
        piPress=false;

    }
    public void onEqual(View v){
        Button button = (Button) v;
        onOperation(v);
        operacio = "equal";
        textNumber.setText("= "+ numA.toString());
        textData.setText("");
        newOp=true;
    }
    public void onAC(View v){
        operacio=null;
        numA=null;
        numB=null;
        textNumber.setText("");
        textData.setText("");
    }
    public void onPi(View v){
        if(newOp){
            textData.setText("");
            numA=null;numB=null;operacio=null;
            newOp=false;
        }
        if(numA==null){
            numA=Math.PI;
            textNumber.setText("π");
        }
        else if(operacio==null){
            textNumber.setText(numA+"π");
            numA=numA*Math.PI;
        }
        else if(numB==null){
            numB=Math.PI;
            textNumber.setText("π");
        }
        else{
            textNumber.setText(numB+"π");
            numB=numB*Math.PI;
        }

        piPress=true;
    }
    public void onTrigo(View v){
        if(numA!=null) {
            Button button = (Button) v;
            String trigo = button.getText().toString();

            if (numB != null){
                operacio = "trigo";
                onOperation(v);
            }
            textData.setText(trigo+"("+numA+")");

            if (modeDegree)
                numA = numA / 360 * 2 * Math.PI;
            switch (trigo) {
                case "sen":
                    numA = Math.sin(numA);
                    break;
                case "cos":
                    numA = Math.cos(numA);
                    break;
                case "tan":
                    numA = Math.tan(numA);
                    break;
            }

            textNumber.setText("= " + numA);
            textData.setText("");
            newOp=true;
        }

    }
    public void onDegRad(View v){
        Button button=(Button) v;
        if(modeDegree) {
            modeDegree = false;
            button.setText("rad");
        }
        else {
            modeDegree = true;
            button.setText("°");
        }
    }
}