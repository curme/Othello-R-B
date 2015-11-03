package curmetec.othello012;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by huizhan on 10/28/15.
 */
public class ButtonBar extends LinearLayout {

    public ButtonBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonBar(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int newHeightSpec = MeasureSpec.makeMeasureSpec(widthSize/7, widthMode);
        super.onMeasure(widthMeasureSpec, newHeightSpec);
    }
}
