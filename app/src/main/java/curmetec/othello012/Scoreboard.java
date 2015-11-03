package curmetec.othello012;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by huizhan on 10/28/15.
 */
public class Scoreboard extends LinearLayout {

    private int userColor;  // to state the color of the scoreboard
    private int chessCount; // to state the piece count of the color

    public Scoreboard(Context context) {
        super(context);
    }

    public Scoreboard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // set the user color of the scoreboard
    public void setUserColor(int userColor){
        this.userColor = userColor;  // 1 means red; 2 means blue
    }

    // to refresh the scoreboard
    public void setScoreboard(int userRound, int chessCount){
        this.chessCount = chessCount;
        ImageView image = (ImageView) this.getChildAt(0);
        TextView  text  = (TextView)  this.getChildAt(1);
        if(userRound==userColor)image.setImageResource(R.drawable.star_bright);
        else                    image.setImageResource(R.drawable.star_van);
        text.setText(Integer.toString(chessCount));
    }
}
