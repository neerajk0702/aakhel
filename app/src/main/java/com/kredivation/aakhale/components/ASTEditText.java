package com.kredivation.aakhale.components;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.utility.ASTEnum;
import com.kredivation.aakhale.utility.ASTUIUtil;


/**
 * <h4>Created</h4> 16/02/17
 *
 * @author AST Inc.
 */
public class ASTEditText extends AppCompatEditText {

	public ASTEditText(Context context) {
		super(context);
		init();
	}

	public ASTEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ASTEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		if (this.isInEditMode()) {
			return;
		}
		if (getTypeface() != null) {
			ASTUIUtil.setFontTypeFace(this, getTypeface().getStyle());
		} else {
			this.setTypeFace(ASTEnum.FONT_REGULAR);
		}
		if (getTextColors() == null) {
			this.setTextColor(ASTUIUtil.getColor(R.color.black));
		}
	}

	public void setTypeFace(ASTEnum fontTypeFace) {
		ASTUIUtil.setFontTypeFace(this, fontTypeFace);
	}
}
