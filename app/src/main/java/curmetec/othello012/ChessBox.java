package curmetec.othello012;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by huizhan on 10/26/15.
 */


/*
    @brief  1. set chess box style;
            2. change chess piece inside the chess box;
 */
public class ChessBox extends ImageView {

    private static int[] chessTypes;
    private static int count = 0;
    public int identifier;

    public ChessBox(Context context) {
        super(context);
        setChessTypes();
        count++;
        this.identifier = count;
    }

    public ChessBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setChessTypes();
    }

    private void setChessTypes(){
        chessTypes = new int[]{
                R.drawable.blank_box, R.drawable.red_box, R.drawable.blue_box, R.drawable.star_box,
                R.drawable.red_up, R.drawable.red_up_right, R.drawable.red_right, R.drawable.red_right_down,
                R.drawable.red_down, R.drawable.red_down_left, R.drawable.red_left, R.drawable.red_left_up,
                R.drawable.red_up_down, R.drawable.red_right_left, R.drawable.red_up_right_down,
                R.drawable.red_right_down_left, R.drawable.red_down_left_up, R.drawable.red_left_up_right,
                R.drawable.red_up_right_down_round, R.drawable.red_up_right_square, R.drawable.red_right_down_square,
                R.drawable.red_dot,
                R.drawable.blue_up, R.drawable.blue_up_right, R.drawable.blue_right, R.drawable.blue_right_down,
                R.drawable.blue_down, R.drawable.blue_down_left, R.drawable.blue_left, R.drawable.blue_left_up,
                R.drawable.blue_up_down, R.drawable.blue_right_left, R.drawable.blue_up_right_down,
                R.drawable.blue_right_down_left, R.drawable.blue_down_left_up, R.drawable.blue_left_up_right,
                R.drawable.blue_up_right_down_round, R.drawable.blue_up_right_square, R.drawable.blue_right_down_square,
                R.drawable.blue_dot};
    }

    public static int getChesstype(int chessType){
        return chessTypes[chessType];
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
