package com.lucasri.aperomix.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lucasri.aperomix.R;
import com.lucasri.aperomix.view.adapter.PlayerAdapter;
import com.lucasri.aperomix.utils.InitGame;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PapinGame extends AppCompatActivity {

    @BindView(R.id.nameGame) TextView nameGame;
    @BindView(R.id.nameGame_doubleScreen) TextView nameGameDoubleScreen;
    @BindView(R.id.nameGame_doubleScreen_inverse) TextView nameGameDoubleScreenInverse;
    @BindView(R.id.game_txt_alone) TextView gameTxtAlone;
    @BindView(R.id.game_txt_doubleScreen) TextView gameTxtDoubleScreen;
    @BindView(R.id.game_txt_inverse) TextView gameTxtDoubleScreenInverse;
    @BindView(R.id.counter) TextView counter;
    @BindView(R.id.counter_doubleScreen) TextView counterDoubleScreen;
    @BindView(R.id.counter_doubleScreen_inverse) TextView counterDoubleScreenInverse;
    @BindView(R.id.line_double_screen) View line;
    @BindView(R.id.previous_rule) ImageView imgPreviousRule;
    @BindView(R.id.next_rule) ImageView imgNextRule;
    @BindView(R.id.info_btn) ImageView infoBtn;
    @BindView(R.id.bottom_btn) Button bottomBtn;


    private Toolbar toolbar;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    private ArrayList<String> playerList = new ArrayList<String>();
    private ArrayList<String> ruleList = new ArrayList<String>();
    private ArrayList<String> passedRuleList = new ArrayList<String>();

    private int playerCounter = 0;
    private int fakePlayerCounter;
    private int passedRuleCounter;
    private int count = 0;
    private int fakeCount;
    private int random;

    private String infoRule;
    private String previousRule = "null";
    private String caseSong;
    private String btnTxt;

    private boolean doubleScreen;
    private boolean boyAndGirl = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_papin_game);
        ButterKnife.bind(this);

        gameTxtAlone.setText("Cliquez sur l'écran pour commencer");
        infoBtn.setVisibility(View.INVISIBLE);
        bottomBtn.setText("Tutoriel");

        this.configureToolbar();
        this.initScreen();
        this.initNavigateArrow();
        this.askBoyAndGirl();
        this.initPlayerList();
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void configureToolbar(){
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("PAPIN GAME");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void initPlayerList(){
        /*for (int i = 0; i < PlayerAdapter.Companion.getPlayerList().size(); i++){
            playerList.add(PlayerAdapter.Companion.getPlayerList().get(i).getPlayerName());
        }*/
    }

    public void initScreen(){
        nameGame.setVisibility(View.VISIBLE);
        gameTxtAlone.setVisibility(View.VISIBLE);
        counter.setVisibility(View.VISIBLE);

        nameGameDoubleScreen.setVisibility(View.INVISIBLE);
        nameGameDoubleScreenInverse.setVisibility(View.INVISIBLE);
        gameTxtDoubleScreen.setVisibility(View.INVISIBLE);
        gameTxtDoubleScreenInverse.setVisibility(View.INVISIBLE);
        counterDoubleScreen.setVisibility(View.INVISIBLE);
        counterDoubleScreenInverse.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);
    }

    public void initNavigateArrow(){
        imgNextRule.setVisibility(View.INVISIBLE);
        imgPreviousRule.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_papin_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        leaveGame();
    }

    // ---------------------
    // ACTION
    // ---------------------

    @OnClick(R.id.game_view)
    public void onClickGameView() {
        if (ruleList.isEmpty()){ InitGame.initRuleList(ruleList, boyAndGirl); }
        bottomBtn.setVisibility(View.INVISIBLE);
        infoBtn.setVisibility(View.VISIBLE);
        if (count == fakeCount){
            imgNextRule.setVisibility(View.INVISIBLE);
            imgPreviousRule.setVisibility(View.INVISIBLE);
            count++;
            fakeCount = count;
            if (count > 52){
                gameFinish();
            } else {
                displayCounter(count);
                if (!playerList.isEmpty()) {
                    displayPlayerName(playerCounter);
                    fakePlayerCounter = playerCounter;
                    playerCounter++;
                    if (playerCounter == playerList.size()) {
                        playerCounter = 0;
                    }
                }
                displayRule();
            }
            passedRuleCounter = passedRuleList.size()-1;
        } else {
            Toast.makeText(getApplication(), "Revenez au dernier tour de jeu", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.previous_rule)
    public void onClickPreviousRule(){
        displayPreviousRule();
        bottomBtn.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.next_rule)
    public void onClickNextRule(){
        displayNextRule();
        bottomBtn.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.info_btn)
    public void onClickInfoBtn(){
        displaydialog(infoRule);
    }

    @OnClick(R.id.bottom_btn)
    public void onClickCountDownBtn(){
        playCountDown();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.doubleScreen:
                if (count != 0){
                    if (!doubleScreen){
                        doubleScreen();
                        doubleScreen = true;
                    }
                    else {
                        initScreen();
                        doubleScreen = false;
                    }
                } else {
                    Toast.makeText(getApplication(), "La partie n'a pas encore commencé", Toast.LENGTH_LONG).show();
                }

                return true;
            case R.id.previousRule:
                if (count != 0){
                    initScreen();
                    imgNextRule.setVisibility(View.VISIBLE);
                    imgPreviousRule.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplication(), "La partie n'a pas encore commencé", Toast.LENGTH_LONG).show();
                }

                return true;
            case android.R.id.home:
                leaveGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ---------------------
    // UTILS
    // ---------------------

    private void leaveGame(){
        new AlertDialog.Builder(this)
                .setMessage("Êtes vous sûr de vouloir quitter la partie ?")
                .setPositiveButton("Quitter", (dialog, which) -> {
                    launchMainActivity();
                })
                .setNegativeButton("Annuler", (dialog, which) -> {
                })
                .show();
    }

    private void displayRule(){
        random = random();
        if (ruleList.get(random) == previousRule && ruleList.size() > 4){
            random = random();
        }
        gameTxtAlone.setText(ruleList.get(random));
        gameTxtDoubleScreen.setText(ruleList.get(random));
        gameTxtDoubleScreenInverse.setText(ruleList.get(random));
        passedRuleList.add(ruleList.get(random));
        displayInfo(ruleList.get(random));
        previousRule = ruleList.get(random);
        ruleList.remove(random);
    }

    private void displayPreviousRule(){
        if (passedRuleCounter > 0){
            fakePlayerCounter--;
            if (fakePlayerCounter < 0) {
                fakePlayerCounter = playerList.size()-1;
            }
            displayPlayerName(fakePlayerCounter);

            passedRuleCounter--;
            gameTxtAlone.setText(passedRuleList.get(passedRuleCounter));
            gameTxtDoubleScreen.setText(passedRuleList.get(passedRuleCounter));
            gameTxtDoubleScreenInverse.setText(passedRuleList.get(passedRuleCounter));
            displayInfo(passedRuleList.get(passedRuleCounter));
            fakeCount--;
            displayCounter(fakeCount);
        }
    }

    private void displayNextRule(){
        if (passedRuleCounter < passedRuleList.size()-1){
            fakePlayerCounter++;
            if (fakePlayerCounter > playerList.size()-1) {
                fakePlayerCounter = 0;
            }
            displayPlayerName(fakePlayerCounter);

            passedRuleCounter++;
            gameTxtAlone.setText(passedRuleList.get(passedRuleCounter));
            gameTxtDoubleScreen.setText(passedRuleList.get(passedRuleCounter));
            gameTxtDoubleScreenInverse.setText(passedRuleList.get(passedRuleCounter));
            displayInfo(passedRuleList.get(passedRuleCounter));
            fakeCount++;
            displayCounter(fakeCount);
        } else {
            imgNextRule.setVisibility(View.INVISIBLE);
            imgPreviousRule.setVisibility(View.INVISIBLE);
            displaydialog("LA PARTIE CONTINUE !");
        }
    }

    private void displaydialog(String message){
        //CREATION
        AlertDialog.Builder papinDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.info_dialog, (ViewGroup)findViewById(R.id.infoDialog));
        papinDialog.setView(layout);

        //DECLARATION
        TextView textView = (TextView) layout.findViewById(R.id.text_info);

        //INIT INPUT
        textView.setText(message);

        //DISPLAY DIALOG
        papinDialog.create().show();
    }

    private void gameFinish(){
        count = 52;
        gameTxtAlone.setText("Partie fini!");
        gameTxtDoubleScreen.setText("Partie fini!");
        gameTxtDoubleScreenInverse.setText("Partie fini!");
        nameGame.setText("PAPIN GAME");
        nameGameDoubleScreen.setText("PAPIN GAME");
        nameGameDoubleScreenInverse.setText("PAPIN GAME");
    }

    private void askBoyAndGirl(){
        new AlertDialog.Builder(this)
                .setTitle("Mixité ?")
                .setMessage("Êtes vous un groupe mixte ? Juste des garçons, ou juste des filles ?")
                .setPositiveButton("Mixte", (dialog, which) -> {
                    boyAndGirl = true;
                })
                .setNegativeButton("Non Mixte", (dialog, which) -> {
                    boyAndGirl = false;
                })
                .show();
    }

    private void displayCounter(int count){
        counter.setText(String.valueOf(count) + "/52");
        counterDoubleScreen.setText(String.valueOf(count) + "/52");
        counterDoubleScreenInverse.setText(String.valueOf(count) + "/52");
    }

    private void displayPlayerName(int playerCounter){
        nameGame.setText(playerList.get(playerCounter));
        nameGameDoubleScreen.setText(playerList.get(playerCounter));
        nameGameDoubleScreenInverse.setText(playerList.get(playerCounter));
    }

    private int random(){
        Double nbRandom = Math.random()* (ruleList.size() - 0);
        int trueNbRandom = nbRandom.intValue();
        return trueNbRandom;
    }

    public void doubleScreen(){
        nameGame.setVisibility(View.INVISIBLE);
        gameTxtAlone.setVisibility(View.INVISIBLE);
        counter.setVisibility(View.INVISIBLE);

        nameGameDoubleScreen.setVisibility(View.VISIBLE);
        nameGameDoubleScreenInverse.setVisibility(View.VISIBLE);
        gameTxtDoubleScreen.setVisibility(View.VISIBLE);
        gameTxtDoubleScreenInverse.setVisibility(View.VISIBLE);
        counterDoubleScreen.setVisibility(View.VISIBLE);
        counterDoubleScreenInverse.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
    }

    public void playCountDown(){
        mediaPlayer = MediaPlayer.create(this, R.raw.count_down);
        mediaPlayer.start();
    }

    public void playLeverLesDeuxBras(){
        mediaPlayer = MediaPlayer.create(this, R.raw.lever_les_deux_bras);
        mediaPlayer.start();
    }
    public void playLeverLaJambeGauche(){
        mediaPlayer = MediaPlayer.create(this, R.raw.lever_la_jambe_gauche);
        mediaPlayer.start();
    }
    public void playLeverLeBrasDroit(){
        mediaPlayer = MediaPlayer.create(this, R.raw.lever_le_bras_droit);
        mediaPlayer.start();
    }
    public void playLeverLeBrasGauche(){
        mediaPlayer = MediaPlayer.create(this, R.raw.lever_le_bras_gauche);
        mediaPlayer.start();
    }

    private void displayInfo(String rule){
        switch (rule){
            case "INVENTE UNE REGLE" :
                infoRule = "Le joueur invente une règle. Exemple : Boire de la main gauche, si quelqu'un ne respect pas cette règle il boit une gorgée";
                break;
            case "DONNE 2 GORGEES" :
                infoBtn.setVisibility(View.INVISIBLE);
                break;
            case "BOIS 2 GORGEES" :
                infoBtn.setVisibility(View.INVISIBLE);
                break;
            case "DONNE 3 GORGEES" :
                infoBtn.setVisibility(View.INVISIBLE);
                break;
            case "BOIS 3 GORGEES" :
                infoBtn.setVisibility(View.INVISIBLE);
                break;
            case "LES GARCONS BOIVENT" :
                infoRule = "Les garçons boivent 1 gorgée";
                break;
            case "LES FILLES BOIVENT" :
                infoRule = "Les filles boivent 1 gorgée";
                break;
            case "ACOLYTE" :
                infoRule = "Choisisser un joueur qui deviendra votre acolyte, celui-ci deviendra votre partenaire de boisson. A chaque fois que l'un de vous deux bois, l'autre l'accompagne (il ne peut y avoir qu'un seul binôme d'acolyte, si la règle tombe sur un autre joueur lui et son partenaire prendront la place des précédents)";
                break;
            case "REFERENDUM" :
                infoRule = "Désigner du doigt tous en même temps un joueur, le joueur avec le plus de voie boit une gorgée !";
                bottomBtn.setVisibility(View.VISIBLE);
                bottomBtn.setText("Compte à rebours");
                caseSong = "countdown";
                break;
            case "RAPIDITE : BRAS GAUCHE" :
                infoRule = "Le dernier à lever son bras gauche boit une gorgée";
                playLeverLeBrasGauche();
                break;
            case "RAPIDITE : BRAS DROIT" :
                infoRule = "Le dernier à lever son bras droit boit une gorgée";
                playLeverLeBrasDroit();
                break;
            case "RAPIDITE : JAMBE GAUCHE" :
                infoRule = "Le dernier à lever sa jambe gauche boit une gorgée";
                playLeverLaJambeGauche();
                break;
            case "RAPIDITE : LES BRAS" :
                infoRule = "Le dernier à lever ses deux bras boit une gorgée";
                playLeverLesDeuxBras();
                break;
            case "CHOISIS UN THEME" :
                infoRule = "Le joueur choisi un thème exemple : les fleurs. Ensuite chacun votre tour donner un nom de fleur. Le joueur qui perd est celui qui ne trouve plus de nom, donne un nom déja utilisé ou est trop long à trouver un nom. Le joueur perdant boit une gorgée (le joueur dont c'est le tour commence, le sens de rotation est le même que celui de jeu)";
                break;
            case "FREEZE" :
                infoRule = "Le joueur peut à nimporte quel moment crier FREEZE, je premier joueur à bouger perd et boit une gorgée. Le FREEZE est à usage unique (cligner ou sourire est considéré comme bouger)";
                break;
            case "PAPIN GAME" :
                infoRule = "REGLE DU PAPIN GAME : Le joueur dont c'est le tour commence. Le joueur donne un objet imaginaire à un des autres joueurs en prononçant le mot suivant TIENS, le joueur recevant devra répondre MERCI CEST DE LA PART DE QUI au joueur lui ayant donné l'objet qui lui répondra CEST DE LA PART DE PAPIN il répondra alors MERCI et donnera l'objet à un autre joueur qui répondra à son tout MERCI CEST DE LA PART DE QUI au joueur qui lui a donné qui lui-même demandera au joueur qui lui a donné demandant CEST DE LA PART DE QUI et lui transmettra la réponse (CEST DE LA PART DE PAPIN). le jeu s'arrête lorsqu'un joueur se trompe de phrase, demande CEST DE LA PART DE QUI ou tout autre phrase du jeu au mauvais joueur.  Afin que le jeu soit plus fun ne suivait pas l'ordre chronologique, faite des croisements ou donner l'objet à la personne vous ayant lui-même  donné l'objet ce qui compliquera grandement les choses ! vous pouvez changer le mot PAPIN par le mot que vous voulez (uniquement le joueur qui a lancé le papin décide du mot et peut change quand il veut)";
                break;
            case "DAME DU REGARD" :
                infoRule = "Le joueur devient la dame du regard, chaque joueur regardant la dame du regard dans les yeux boit une gorgée (Le joueur reste la dame du regard jusqu'a ce qu'un autre joueur soit désigné à sa place)";
                break;
            case "DAME DES QUESTIONS" :
                infoRule = "Le joueur devient la dame des questions, chaque joueur répondant à la dame des question dans les yeux boit une gorgée (Le joueur reste la dame des question jusqu'a ce qu'un autre joueur soit désigné à sa place)";
                break;
            case "ROI DES POUCES" :
                infoRule = "Le joueur devient le roi des pouces, le roi des pouces peut poser son pouce où il veut, le dernier à faire de même boit. Pouvoir à usage unique (S'il pose son pouce sur son téléphone, les joueurs peuvent poser leur pouce sur leur propre téléphone mais s'il pose son pouce sur la bouteille d'eau, tous les joueurs doivent poser leur pouce sur la même bouteille)";
                break;
            case "JUSTE PRIX" :
                infoRule = "Le joueur choisit un objet et une caractéristique de son choix, les autres joueurs doivent deviner sa caractéristique, le plus éloigné boit. Ex : La taille de la tour Eiffel";
                break;
            case "DUEL DE CHI FOU MI" :
                infoRule = "Le joueur défi le joueur de son choix, le premier arriver à 3 manche gagnè gagne, c'est donc l'autre joueur qui boit";
                break;
        }
    }

    private void launchMainActivity(){
        Intent myIntent = new Intent(PapinGame.this, MainActivity.class);
        this.startActivity(myIntent);
    }
}