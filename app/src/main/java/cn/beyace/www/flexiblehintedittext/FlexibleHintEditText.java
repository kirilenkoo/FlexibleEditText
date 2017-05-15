package cn.beyace.www.flexiblehintedittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by huangzilong on 2017/5/12.
 */

public class FlexibleHintEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher {
    boolean customHintShow = false;
    int originPaddingRight;
    int originPaddingTop;
    int offsetForHint;
    public FlexibleHintEditText(Context context) {
        super(context);
    }

    public FlexibleHintEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.addTextChangedListener(this);
        originPaddingRight = getPaddingRight();
        originPaddingTop = getPaddingTop();
    }

    public FlexibleHintEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {


        super.onDraw(canvas);

        if(customHintShow) {
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(getTextSize());

            Rect bound = new Rect();
            paint.getTextBounds(getHint().toString(),0,getHint().length(),bound);
            Log.d("hint",bound.left+":"+bound.top+":"+bound.right+":"+bound.bottom);
            offsetForHint = bound.bottom - bound.top;
            setPadding(originPaddingRight,originPaddingTop,originPaddingRight,originPaddingTop+offsetForHint);
            getBackground().setBounds(0,0,canvas.getWidth(),canvas.getHeight()-offsetForHint);
            int y = getBaseline();
            int x = (int) (canvas.getWidth()-paint.measureText(getHint().toString())-originPaddingRight);
            //drawText y is the baseline position
            canvas.drawText(getHint().toString(), x, y, paint);
            canvas.save();
            canvas.drawText(getHint().toString(),originPaddingRight,canvas.getHeight(),paint);
        }else{
            setPadding(originPaddingRight,originPaddingTop,originPaddingRight,originPaddingTop);
            getBackground().setBounds(0,0,canvas.getWidth(),canvas.getHeight());
        }
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d("hint",start+":"+after);
        if(start==0&&after==0){
            customHintShow = false;
        }else{
            customHintShow = true;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
