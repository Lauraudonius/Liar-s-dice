package com.example.laurynas.liarsdice;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker2);
        NumberPicker numberPicker1 = (NumberPicker) findViewById(R.id.numberPicker);
        workWithNUmberPicker(numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(15);
        workWithNUmberPicker(numberPicker1);
        numberPicker1.setMinValue(1);
        numberPicker1.setMaxValue(6);
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

