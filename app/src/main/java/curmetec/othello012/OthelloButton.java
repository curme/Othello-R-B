package curmetec.othello012;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by huizhan on 10/28/15.
 */
public class OthelloButton extends ImageButton implements View.OnTouchListener {

    private int button_face;    // to state the button face, instruct the function of the button
    public boolean doubleSwitch; // to instruct if the button is muilt-functioned

    public OthelloButton(Context context) {
        super(context);
        initButton();
    }

    public OthelloButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initButton();
    }

    // init the button
    private void initButton(){
        button_face = R.drawable.button_face;
        doubleSwitch = true;
        this.setImageResource(button_face);
        this.setScaleType(ScaleType.FIT_START);
        this.setOnTouchListener(this);
    }

    // to set the face of the button
    public void setButtonface(int specificButtonFace){
        button_face = specificButtonFace;
        this.setImageResource(button_face);
        this.setScaleType(ScaleType.FIT_START);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ((ImageButton) v).setImageResource(button_face);
            ((ImageButton) v).setScaleType(ScaleType.FIT_END);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            ((ImageButton) v).setImageResource(button_face);
            ((ImageButton) v).setScaleType(ScaleType.FIT_START);
        }
        return false;
    }
}
