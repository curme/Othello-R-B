package curmetec.othello012;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by huizhan on 10/26/15.
 */
public class ChessBoxAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ChessBox> chessBoxList;

    public ChessBoxAdapter(Context context){
        mContext = context;
        chessBoxList = new ArrayList<ChessBox>();
    }

    public int getCount() {
        return chessOrder.length; // return max chess pieces count
    }

    public ChessBox getItem(int position) {
        //ChessBox c = chessBoxList.get(position+4);
        //System.out.println("GETItem "+c.identifier);
        return chessBoxList.get(position+4);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        //        ChessBox chessBox;
        //        if (convertView == null) {
        //            // if it's not recycled, initialize some attributes
        //            chessBox = new ChessBox(mContext);
        //        } else {
        //            chessBox = (ChessBox) convertView;
        //        }
        ChessBox chessBox = new ChessBox(mContext);
        chessBox.setImageResource(chessBox.getChesstype(chessOrder[position]));
        //System.out.println("getView " + chessBox.identifier);
        chessBoxList.add(chessBox);
        //System.out.println(chessBoxList.size());
        return chessBox;
    }

    // the init chessboard
    private int[] chessOrder = new int[]{
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,3,0,0,0,0,
            0,0,3,2,1,0,0,0,
            0,0,0,1,2,3,0,0,
            0,0,0,0,3,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
    };
}
