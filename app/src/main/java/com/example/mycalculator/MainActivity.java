package com.example.mycalculator;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    /** Переменные */
    TextView resultField; //результат
    EditText numberField; //вводимое число
    TextView operationField; //операция
    Double operand = null; //операнд
    String lastOperation = "="; //последняя операция


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Связываем поля с переменными
        resultField = (TextView) findViewById(R.id.resultField);
        numberField = (EditText) findViewById(R.id.numberField);
        operationField = (TextView) findViewById(R.id.operationField);
    }

    //Сохранение состояния
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("OPERATION", lastOperation);
        if (operand != null) {
            outState.putDouble("OPERAND", operand);
        }
        super.onSaveInstanceState(outState);
    }

    //получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    //Обработчик нажатия кнопок цифр
    public void onNumberClick(View view){
        Button button = (Button) view;
        numberField.append(button.getText());

        if (lastOperation.equals("=") && operand != null) {
            operand = null;
        }
    }

    //Обработчик нажатия кнопок операций
    public void onOperationClick(View view) {
        Button button = (Button) view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();
        //Если что-нибудь введено
        if (number.length()>0) {
            number = number.replace(",", ".");
            try {
                performOperation(Double.valueOf(number), op);
            } catch (NumberFormatException ex){
                numberField.setText("Ошибка!");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }

    //Операция выполнения арифметики
    private void performOperation (Double number, String operation){
        //Если операнд ранее не установлен
        if (operand == null){
            operand = number;
        } else {
            if (lastOperation.equals("=")){
                lastOperation = operation;
            }
            switch (lastOperation) {
                case "=":
                    operand = number;
                    break;
                case "/":
                    if (number ==0) {
                        operand = 0.0;
                    } else {
                        operand /= number;
                    }
                    break;
                case "*":
                    operand *= number;
                    break;
                case "+":
                    operand += number;
                    break;
                case "-":
                    operand -= number;
                    break;
            }
        }
        resultField.setText(operand.toString().replace(".", ","));
        numberField.setText("");
    }
}
