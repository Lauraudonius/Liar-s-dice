package com.example.laurynas.liarsdice;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameMakerActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_maker);
        getListView().setBackgroundColor(Color.LTGRAY);
    }

    private List<String> players = new ArrayList<>();
    String string = "";
    public void onCreatePlayer(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Player's name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                players.add(input.getText().toString());
                string += input.getText().toString() + "\n\n\n\n";
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,players );
                getListView().setAdapter(arrayAdapter);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    public void toGame(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("Players", string);
        intent.putExtra("Number", players.size());
        if(!(players.size() == 0)) {
            startActivity(intent);
        }else Toast.makeText(getApplicationContext(), "0 players in the game", Toast.LENGTH_SHORT).show();
    }
}
