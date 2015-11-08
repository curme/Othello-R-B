package curmetec.othello012;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by huizhan on 10/26/15.
 */

/**
 *   @brief provide 5 main services:
 *           1. go chess
 *           2. undo piece put
 *           3. switch on/off the hint switch
 *           4. current user give up the game
 *           5. restart the whole game
 */
public class Chessboard extends GridView {

    private Context chessboardContext;
    private int[][] chessPosition;          // to stash the chess positions of each turn
    private int[][] canPtPosition;          // to stash the positions can put piece of each turn
    private int[]   userRound;              // to stash the user color of each turn
    private int     redCount, blueCount;    // to state the current piece count of each color
    private boolean hintSwitch;             // to instruct if the hint function is opened by user
    public  boolean gameFinished;           // to instruct if the current game over the chessboard is finished

    public Chessboard(Context context) {
        super(context);
        initGame(context);
    }

    public Chessboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGame(context);
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    // init game
    private void initGame(Context context){
        chessboardContext = context;
        initChessPosition();
        initCanPtPosition();
        initUserRound();
        initPieceCount();
        hintSwitch = true;
        gameFinished=false;
        MediaPlayer.create(chessboardContext, R.raw.start).start();
    }

    // finish game
    private void finishGame(int turn){
        System.out.println("Somebody win!");
        countChessPieces(turn);
        if(redCount>blueCount)      refreshChessboard(62);
        else if(redCount<blueCount) refreshChessboard(63);
        else                        refreshChessboard(61);
        gameFinished = true;
        MediaPlayer.create(chessboardContext, R.raw.win).start();
    }

    // init very beginning chessboard
    private void initChessPosition(){
        chessPosition    = new int[64][64];
        chessPosition[0] = new int[]{       // initial game print
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,2,1,0,0,0,
                0,0,0,1,2,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,
        };
        chessPosition[61]= new int[]{       // draw game print
                0, 0, 0, 0, 0, 0, 0, 0,
                20,9, 7 ,9 ,33,26,26,0,
                14,18,12,12,30,32,34,0,
                19,11,5 ,11,22,22,22,0,
                26,26,26,26,7 ,9 ,8, 8,
                30,30,30,30,12,12,12,4,
                23,35,29,22,4, 5 ,11,21,
                0, 0, 0, 0, 0, 0, 0, 0,
        };
        chessPosition[62]= new int[]{       // red win print
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 20,9, 7, 10,20,9, 0,
                0, 14,18,14,10,12,12,0,
                0, 4, 4, 5, 10,19,11,0,
                8, 8, 8, 8, 7, 9, 8, 8,
                12,12,12,12,12,12,12,4,
                5, 17,11,4, 4, 5, 11,21,
                0, 0, 0, 0, 0, 0, 0, 0,
        };
        chessPosition[63]= new int[]{       // blue win print
                0, 0, 0, 0, 0, 0, 0, 0,
                38,27,26,0, 26,26,25,28,
                32,36,30,0, 30,30,32,28,
                37,29,23,28,23,29,23,28,
                26,26,26,26,25,27,26,26,
                30,30,30,30,30,30,30,22,
                23,35,29,22,22,23,29,39,
                0, 0, 0, 0, 0, 0, 0, 0,
        };
    }

    // init very beginning can put positions
    private void initCanPtPosition(){
        canPtPosition    = new int[66][64];
        canPtPosition[1] = new int[]{
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,1,0,0,0,0,
                0,0,1,0,0,0,0,0,
                0,0,0,0,0,1,0,0,
                0,0,0,0,1,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,
        };
    }

    // init user round
    private void initUserRound(){
        userRound    = new int[62];
        userRound[0] = -1;
        userRound[61]= -1;
        userRound[1] =  1;   // 1 means red user, 2 means blue user, -1 means nobody to go chess
    }

    // init pieces count
    private void initPieceCount(){
        redCount = 2; blueCount= 2;
    }

    // get row of position
    private int getRow(int position){
        return position / 8;  // row start from 0 till to 7
    }

    // get col of position
    private int getCol(int position){
        return position % 8;  // column start from 0 till to 7
    }

    // get position of a point (row, col)
    private int getPosition(int row, int column){
        return 8 * row + column;
    }

    // get user color of the turn
    public int getUserRound(int turn){
        return userRound[turn];
    }

    // get red piece count
    public int getRedPieceCount(){
        return redCount;
    }

    // get blue piece count
    public int getBluePieceCount(){
        return blueCount;
    }

    // count pieces count
    private void countChessPieces(int turn){
        int total = turn+4;
        int redTmp= 0;
        for(int i:chessPosition[turn]) if(i==1) redTmp += 1;
        redCount = redTmp;
        blueCount= total-redTmp;
    }

    // eat/recolor the pieces of rival
    private void recolorRivalPieces(int turn, int position, int userColor){
        int row = getRow(position); int col = getCol(position);
        int r = 7-col; int dn = 7-row;
        java.util.Stack candidate = new java.util.Stack();

        // up
        candidate.push(position);
        for(int i = 1; i <= row; i++){
            int tmpP = getPosition(row-i, col);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))candidate.push(tmpP);
            if(tmpC == userColor)    break;
            if(tmpC == 0 || i == row){while((int)candidate.pop()!=position){} break;}
        }

        // up-right
        candidate.push(position);
        for(int i = 1; i <= Math.min(r, row); i++){
            int tmpP = getPosition(row-i, col+i);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))candidate.push(tmpP);
            if(tmpC == userColor)    break;
            if(tmpC == 0 || i == Math.min(r, row)){while((int)candidate.pop()!=position){} break;}
        }

        // right
        candidate.push(position);
        for(int i = 1; i <= r; i++){
            int tmpP = getPosition(row, col+i);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))candidate.push(tmpP);
            if(tmpC == userColor)    break;
            if(tmpC == 0 || i == r) {while((int)candidate.pop()!=position){} break;}
        }

        // right-down
        candidate.push(position);
        for(int i = 1; i <= Math.min(r, dn); i++){
            int tmpP = getPosition(row+i, col+i);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))candidate.push(tmpP);
            if(tmpC == userColor)    break;
            if(tmpC == 0 || i == Math.min(r, dn)){while((int)candidate.pop()!=position){} break;}
        }

        // down
        candidate.push(position);
        for(int i = 1; i <= dn; i++){
            int tmpP = getPosition(row+i, col);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))candidate.push(tmpP);
            if(tmpC == userColor)    break;
            if(tmpC == 0 || i == dn){while((int)candidate.pop()!=position){} break;}
        }

        // down-left
        candidate.push(position);
        for(int i = 1; i <= Math.min(dn, col); i++){
            int tmpP = getPosition(row+i, col-i);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))candidate.push(tmpP);
            if(tmpC == userColor)    break;
            if(tmpC == 0 || i == Math.min(dn, col)){while((int)candidate.pop()!=position){} break;}
        }

        // left
        candidate.push(position);
        for(int i = 1; i <= col; i++){
            int tmpP = getPosition(row, col-i);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))candidate.push(tmpP);
            if(tmpC == userColor)    break;
            if(tmpC == 0 || i == col){while((int)candidate.pop()!=position){} break;}
        }

        // left-up
        candidate.push(position);
        for(int i = 1; i <= Math.min(col, row); i++){
            int tmpP = getPosition(row-i, col-i);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))candidate.push(tmpP);
            if(tmpC == userColor)    break;
            if(tmpC == 0 || i == Math.min(col, row)){while((int)candidate.pop()!=position){} break;}
        }

        while(!candidate.isEmpty())
            chessPosition[turn][(int)candidate.pop()] = userColor;
    }

    // refresh the can put position matrix
    private void judgeCanPtPosition(int turn, int userColor){
        int length = chessPosition[turn].length;
        canPtPosition[turn+1] = new int[64];
        for(int i = 0; i < length; i++){
            if(ifCanPut(turn, i, userColor))canPtPosition[turn+1][i] = 1;
            else canPtPosition[turn+1][i] = 0;
        }
    }

    // to check if a position can put piece
    private boolean ifCanPut(int turn, int position, int userColor){
        if(chessPosition[turn][position]!=0) return false;
        int row = getRow(position); int col = getCol(position);
        int r = 7-col; int dn = 7-row;

        // up
        for(int i = 1; i <= row; i++){
            int tmpP = getPosition(row-i, col);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))continue;
            if(tmpC == userColor){if(i>1) return true; break;}
            if(tmpC == 0 || i == row)break;
        }

        // up-right
        for(int i = 1; i <= Math.min(r, row); i++){
            int tmpP = getPosition(row-i, col+i);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))continue;
            if(tmpC == userColor){if(i>1) return true; break;}
            if(tmpC == 0 || i == Math.min(r, row))break;
        }

        // right
        for(int i = 1; i <= r; i++){
            int tmpP = getPosition(row, col+i);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))continue;
            if(tmpC == userColor){if(i>1) return true; break;}
            if(tmpC == 0 || i == r)break;
        }

        // right-down
        for(int i = 1; i <= Math.min(r, dn); i++){
            int tmpP = getPosition(row+i, col+i);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))continue;
            if(tmpC == userColor){if(i>1) return true; break;}
            if(tmpC == 0 || i == Math.min(r, dn))break;
        }

        // down
        for(int i = 1; i <= dn; i++){
            int tmpP = getPosition(row+i, col);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))continue;
            if(tmpC == userColor){if(i>1) return true; break;}
            if(tmpC == 0 || i == dn)break;
        }

        // down-left
        for(int i = 1; i <= Math.min(dn, col); i++){
            int tmpP = getPosition(row+i, col-i);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))continue;
            if(tmpC == userColor){if(i>1) return true; break;}
            if(tmpC == 0 || i == Math.min(dn, col))break;
        }

        // left
        for(int i = 1; i <= col; i++){
            int tmpP = getPosition(row, col-i);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))continue;
            if(tmpC == userColor){if(i>1) return true; break;}
            if(tmpC == 0 || i == col)break;
        }

        // left-up
        for(int i = 1; i <= Math.min(col, row); i++){
            int tmpP = getPosition(row-i, col-i);
            int tmpC = chessPosition[turn][tmpP];
            if(tmpC == (3-userColor))continue;
            if(tmpC == userColor){if(i>1) return true; break;}
            if(tmpC == 0 || i == Math.min(col, row))break;
        }

        return false;
    }

    // refresh the whole cessboard
    private void refreshChessboard(int turn){
        int length = chessPosition[turn].length;

        // print the piece pasition matrix
        //System.out.println("@@ user "+userRound[turn]+" @@@@@@@@@@@@@@@@@@@@@@@@@@");System.out.println("chessboard of turn " + turn);
        //for(int i = 0; i < length; i+=8){System.out.println(chessPosition[turn][i] + " " + chessPosition[turn][i + 1] + " " + chessPosition[turn][i + 2] + " " + chessPosition[turn][i + 3] + " "+ chessPosition[turn][i + 4] + " " + chessPosition[turn][i + 5] + " " + chessPosition[turn][i + 6] + " " + chessPosition[turn][i+7] + " ");}
        // print the can put matrix
        //System.out.println("@@ user "+userRound[turn]+" @@@@@@@@@@@@@@@@@@@@@@@@@@");System.out.println("positions can put pieces of turn " + turn);
        //for(int i = 0; i < length; i+=8){System.out.println(canPtPosition[turn+1][i]+" "+canPtPosition[turn+1][i+1]+" "+canPtPosition[turn+1][i+2]+" "+canPtPosition[turn+1][i+3]+" "+ canPtPosition[turn+1][i+4]+" "+canPtPosition[turn+1][i+5]+" "+canPtPosition[turn+1][i+6]+" "+canPtPosition[turn+1][i+7]+" ");}

        // refresh the pieces
        for(int i = 0; i < length; i++) ((ChessBox) this.getItemAtPosition(i)).setImageResource(ChessBox.getChesstype(chessPosition[turn][i]));
        // refresh the hints
        if (hintSwitch) for(int i = 0; i < length; i++) if(canPtPosition[turn+1][i] == 1) ((ChessBox)this.getItemAtPosition(i)).setImageResource(R.drawable.star_box);
    }

    // to judge the user color of next round of if the game meet the final situation
    private void setNextUserRound(int turn, int userColor){
        int sum = 0;
        for(int i:canPtPosition[turn+1]) sum += i;
        if(sum > 0) userRound[turn+1] = (3-userColor);
        else{
            judgeCanPtPosition(turn, userColor);
            for(int i:canPtPosition[turn+1]) sum += i;
            if(sum > 0){userRound[turn+1] = userColor;refreshChessboard(turn);
                        MediaPlayer.create(chessboardContext, R.raw.pass).start();}
            else finishGame(turn);
        }
    }

    ////////////////////////////////
    // CORE METHODS: 5 main services provided by chessboard
    ////////////////////////////////

    // go chess
    public boolean goChess(int turn, int position){
        // judge user color ( next chess piece color: r|b )
        int userColor = userRound[turn];
        MediaPlayer.create(chessboardContext, R.raw.piece_sound).start();

        // load chessboard of last turn into the chessboard of this turn
        chessPosition[turn] = new int[64];
        for(int i = 0; i < 64; i++) chessPosition[turn][i] = chessPosition[turn-1][i];

        // judge if could put chess piece two situations cannot put chess :
        // 1. box has been occupied; 2. can not eat counterpart's chess piece
        if(chessPosition[turn][position]!=0 || canPtPosition[turn][position]==0) return false;

        // re-colored rival chess pieces
        recolorRivalPieces(turn, position, userColor);

        // judge the can put chess pieces of next time
        judgeCanPtPosition(turn, (3 - userColor));

        // then refresh the whole chessboard
        refreshChessboard(turn);

        // then count the chess pieces
        countChessPieces(turn);

        // then set the next user round
        setNextUserRound(turn, userColor);

        return true;
    };

    // undo piece put
    public boolean undo(int turn){
        if(turn <= 0) return false;

        // refresh the whole chessboard
        refreshChessboard(turn-1);

        // count the chess pieces
        countChessPieces(turn-1);

        return true;
    }

    // switch on/off the hint button
    public void switchHint(int turn){
        // switch the hintSwitch
        hintSwitch = hintSwitch ? false : true;

        // refresh the whole chessboard
        refreshChessboard(turn);
    }

    // current user color give up the game
    public void giveUp(int turn){
        int userColor = userRound[turn];
        refreshChessboard(chessPosition.length-userColor);
        //turn = chessPosition.length-userColor;
        for(int i = 0; i < 64; i+=8){System.out.println(chessPosition[turn][i] + " " + chessPosition[turn][i + 1] + " " + chessPosition[turn][i + 2] + " " + chessPosition[turn][i + 3] + " "+ chessPosition[turn][i + 4] + " " + chessPosition[turn][i + 5] + " " + chessPosition[turn][i + 6] + " " + chessPosition[turn][i+7] + " ");}
        gameFinished = true;
    }

    // restart the whole game
    public void restart(){
        initGame(chessboardContext);
        refreshChessboard(0);
    }
}

