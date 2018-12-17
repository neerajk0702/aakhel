package com.kredivation.aakhale.components;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.DimenRes;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.utility.ASTEnum;
import com.kredivation.aakhale.utility.ASTUIUtil;
import com.kredivation.aakhale.utility.FontManager;


/**
 * <h4>Created</h4> 16/02/17
 *
 * @author AST Inc.
 */
public class ASTFontTextIconView extends AppCompatTextView {

    public ASTFontTextIconView(Context context) {
        super(context);
        init();
    }

    public ASTFontTextIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ASTFontTextIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (this.isInEditMode()) {
            return;
        }
        Typeface aakhel_font = FontManager.getFontTypefaceMaterialDesignIcons(getContext(), "fonts/aakhel.ttf");

        if (getTypeface() != null) {
            this.setTypeface(aakhel_font);
        } else {
            this.setTypeface(aakhel_font);
        }
        if (getTextColors() == null) {
            this.setTextColor(ASTUIUtil.getColor(R.color.black));
        }
    }

    public void setTypeFace(ASTEnum fontTypeFace) {
        ASTUIUtil.setFontTypeFace(this, fontTypeFace);
    }

    public void setTextDimen(@DimenRes int dimenID) {
        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, ASTUIUtil.getDimension(dimenID));
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }
}
