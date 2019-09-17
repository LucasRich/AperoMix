package com.lucasri.aperomix.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lucasri.aperomix.R;
import com.lucasri.aperomix.View.adapter.PlayerAdapter;
import com.lucasri.aperomix.model.Player;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list) ListView playerListView;

    private Toolbar toolbar;
    public ArrayList<Player> playerList = new ArrayList<>();
    private PlayerAdapter playerAdapter;
    private int counter = 4;
    public static Context contextOfApplication;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        contextOfApplication = getApplicationContext();

        this.initPlayerListView();
        this.configureToolbar();
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void configureToolbar(){
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
    }

    private void initPlayerListView(){
        if (PlayerAdapter.playerList != null){
            playerList.addAll(PlayerAdapter.playerList);
        }else {
            for(int i = 1; i < 4; i++){
                Player player = new Player();
                player.setPlayerName("Joueur " + i);
                playerList.add(player);
            }
        }

        playerAdapter = new PlayerAdapter(this, playerList);
        playerListView.setAdapter(playerAdapter);
    }

    // ---------------------
    // ACTION
    // ---------------------

    @OnClick(R.id.startButton)
    public void onClickStartButton() {
        launchPmuGameContainer();
    }

    @OnClick(R.id.plus_one)
    public void onClickPlus() {
        addPlayer();
    }

    // ---------------------
    // UTILS
    // ---------------------

    private void addPlayer(){
        Player player = new Player();
        player.setPlayerName("Joueur " + counter);
        playerList.add(player);

        playerAdapter = new PlayerAdapter(this, playerList);
        playerListView.setAdapter(playerAdapter);
        counter++;
    }

    private void launchPapinGameActivity(){
        Intent myIntent = new Intent(MainActivity.this, PapinGame.class);
        this.startActivity(myIntent);
    }

    private void launchPmuGameContainer(){
        Intent myIntent = new Intent(MainActivity.this, GameContainer.class);
        this.startActivity(myIntent);
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }
}
