package com.example.laurynas.liarsdice;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameActivity extends ListActivity {
    int amount = 0, number = 0, currentPlayer = 0;
    String[] players;
    List<int[]> listOfNumbers;
    boolean areOnesCalled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        int amountOfPlayers = 0;
        if(extras !=null) {
            amountOfPlayers = extras.getInt("Number");
        }
        players = new String[amountOfPlayers];
        if(extras !=null) {
            players = extras.getString("Players").split("\n\n\n\n");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(players[0]);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker2);
        NumberPicker numberPicker1 = (NumberPicker) findViewById(R.id.numberPicker);
        workWithNUmberPicker(numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(15);
        workWithNUmberPicker(numberPicker1);
        numberPicker1.setMinValue(1);
        numberPicker1.setMaxValue(6);
        listOfNumbers = new ArrayList<>();
        for(int i = 0;i < amountOfPlayers;i++){
            listOfNumbers.add(randomTheNumbers(5));
        }
        List<String> stringList =  new ArrayList<>();
        for(int i : listOfNumbers.get(currentPlayer)){
            stringList.add(String.valueOf(i));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1, stringList);
        getListView().setAdapter(arrayAdapter);
    }
    public void onLiar(View view){
        int all = 0;
        for(int[] arr : listOfNumbers){
            for(int i  = 0;i< arr.length;i++){
                if(arr[i] == number || areOnesCalled == false && arr[i] == 1)all++;
            }
        }
        String result, lastPlayer;
        if(currentPlayer == 0 )lastPlayer = players[players.length-1];
        else lastPlayer = players[currentPlayer-1];
        if(!(all >= amount))result = ("There were " + all + ", and it needed " + amount + ". " + players[currentPlayer] + " wins. You wanna play again?");
        else result = ("There were " + all + ", and it needed " + amount + ". " + lastPlayer + " wins. You wanna play again?");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(result)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        recreate();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            finishAffinity();
                        }
                    }
                }).show();

    }
    public void onCall(View view){
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker2);
        NumberPicker numberPicker1 = (NumberPicker) findViewById(R.id.numberPicker);

        int nowAmount = numberPicker.getValue();
        int nowNumber = numberPicker1.getValue();
        if(ifPossibleToCall(nowAmount, nowNumber)){
            String lastPlayer = players[currentPlayer];
            if(currentPlayer < players.length-1)currentPlayer++;
            else currentPlayer = 0;
            amount = nowAmount;
            number = nowNumber;
            if(nowNumber == 1)areOnesCalled = true;
            List<String> stringList =  new ArrayList<>();
            for(int i : listOfNumbers.get(currentPlayer)){
                stringList.add(String.valueOf(i));
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1, stringList);
            getListView().setAdapter(arrayAdapter);
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(players[currentPlayer] + "'s turn" + "\n" +
            "Last call by " + lastPlayer + ": " + amount + "*" + number);
        }else Toast.makeText(getApplicationContext(), "You can't call that", Toast.LENGTH_SHORT).show();
    }
    public boolean ifPossibleToCall(int nowAmount, int nowNumber){
        return (nowAmount > amount)||(nowNumber > number );
    }
    public int[] randomTheNumbers(int max){
        int[] array = new int[max];
        for(int i = 0;i < max;i++){
            Random random = new Random();
            array[i] = random.nextInt(6) + 1;
        }
        Arrays.sort(array);
        return array;
    }
    public void workWithNUmberPicker(NumberPicker numberPicker){
        setNumberPickerTextColor(numberPicker, Color.GREEN);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }

    public static void setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                }
                catch(NoSuchFieldException e){
                    Log.w("setNumberPickerTextCo", e);
                }
                catch(IllegalAccessException e){
                    Log.w("setNumberPickerText", e);
                }
                catch(IllegalArgumentException e){
                    Log.w("setNumberPickerText", e);
                }
            }
        }
    }

}

