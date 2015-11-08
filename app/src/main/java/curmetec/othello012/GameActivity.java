package curmetec.othello012;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private int turn;

    // to set the turn
    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // 1. init chessboard
        // 2. set turn as the zero turn
        final Chessboard chessboard = (Chessboard) findViewById(R.id.chessboard);
        chessboard.setAdapter(new ChessBoxAdapter(this));
        setTurn(0);

        // init Scoreboard
        final Bar topBar = (Bar) findViewById(R.id.topBar);
        final Scoreboard redScoreboard = (Scoreboard) findViewById(R.id.redScoreboard);
        final Scoreboard blueScoreboard= (Scoreboard) findViewById(R.id.blueScoreboard);
        redScoreboard.setUserColor(1);  redScoreboard.setScoreboard(1, 2);
        blueScoreboard.setUserColor(2); blueScoreboard.setScoreboard(1, 2);

        // init buttons
        final OthelloButton btnUndoPiece = (OthelloButton) findViewById(R.id.btn_undo_piece);       btnUndoPiece.setButtonface(R.drawable.button_face_undo_piece);
        final OthelloButton btnHint      = (OthelloButton) findViewById(R.id.btn_hint);             btnHint.setButtonface(R.drawable.button_face_hint);
        final OthelloButton btnGiveUpRstt= (OthelloButton) findViewById(R.id.btn_give_up_restart);  btnGiveUpRstt.setButtonface(R.drawable.button_face_give_up);
        final OthelloButton btnExit      = (OthelloButton) findViewById(R.id.btn_exit);             btnExit.setButtonface(R.drawable.button_face_exit);

        // function when chessbox clicked
        chessboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                boolean doneFlag = chessboard.goChess(turn + 1, position);
                if (doneFlag) {
                    setTurn(turn + 1);
                    redScoreboard.setScoreboard( chessboard.getUserRound(turn + 1), chessboard.getRedPieceCount());
                    blueScoreboard.setScoreboard(chessboard.getUserRound(turn + 1), chessboard.getBluePieceCount());
                }
                if (chessboard.gameFinished) {
                    btnGiveUpRstt.setButtonface(R.drawable.button_face_restart);
                    btnGiveUpRstt.doubleSwitch = false;
                }
            }
        });

        // function when user choose undo piece
        btnUndoPiece.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if(chessboard.gameFinished) return;
                boolean doneFlag = chessboard.undo(turn);
                if (doneFlag) {
                    setTurn(turn - 1);
                    redScoreboard.setScoreboard(chessboard.getUserRound(turn + 1), chessboard.getRedPieceCount());
                    blueScoreboard.setScoreboard(chessboard.getUserRound(turn + 1), chessboard.getBluePieceCount());
                }
            }
        });

        // function when user switch hint
        btnHint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if(chessboard.gameFinished) return;
                chessboard.switchHint(turn);
            }
        });

        // function when user choose give up/restart a game
        btnGiveUpRstt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if (btnGiveUpRstt.doubleSwitch) {
                    // give up
                    chessboard.giveUp(turn+1);
                    btnGiveUpRstt.setButtonface(R.drawable.button_face_restart);
                    btnGiveUpRstt.doubleSwitch = false;
                } else {
                    // restart
                    setTurn(0);
                    chessboard.restart();
                    redScoreboard.setScoreboard(1, 2); blueScoreboard.setScoreboard(1, 2);
                    btnGiveUpRstt.setButtonface(R.drawable.button_face_give_up);
                    btnGiveUpRstt.doubleSwitch = true;
                }
            }
        });

        // function when user choose exit the game
        btnExit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent myIntent = new Intent(GameActivity.this, WelcomeActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                GameActivity.this.startActivity(myIntent);
            }
        });
    }
}
