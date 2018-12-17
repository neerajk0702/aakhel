package com.kredivation.aakhale.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kredivation.aakhale.R;
import com.kredivation.aakhale.utility.ASTUIUtil;


/**
 * <h4>Created</h4> 4/11/2017
 *
 * @author Altametrics Inc.
 */
public class FNImageInfoTile extends LinearLayout implements View.OnClickListener {

    protected ASTTextView totalpage, lefticon, righticon;
    protected ASTImageView topimage;
    protected ASTImageView centerimage;
    protected CardView cardViewLayout;
    private FNImageInfoTile.OnComponentActionListener listener;
    private ASTTextView footerTitleView, footerSubtitleView;
    private CardView totalPageCardView;

    private String footerTitle;
    private String footerSubtitle;


    public FNImageInfoTile(Context context) {
        this(context, null);
    }

    public FNImageInfoTile(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FNImageInfoTile(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttributes(attrs);
        init(context);
    }

    private void init(Context context) {
        if (this.isInEditMode()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fn_image_info_tile, this, true);
        lefticon = this.findViewById(R.id.lefticon);
        righticon = this.findViewById(R.id.righticon);
        centerimage = this.findViewById(R.id.centerimage);
        topimage = this.findViewById(R.id.topimage);
        totalpage = this.findViewById(R.id.totalpage);
        footerTitleView = this.findViewById(R.id.footerTitle);
        footerSubtitleView = this.findViewById(R.id.footerSubtitle);
        lefticon.setOnClickListener(this);
        righticon.setOnClickListener(this);
        cardViewLayout = this.findViewById(R.id.cardViewLayout);
        totalPageCardView = this.findViewById(R.id.totalpageContainer);
        setFooterTitle();
    }

    private void loadAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.FNImageInfoTile, 0, 0);
            if (attr.hasValue(R.styleable.FNImageInfoTile_footer_title)) {
                this.footerTitle = attr.getString(R.styleable.FNImageInfoTile_footer_title);
            }
            attr.recycle();
        }
    }

    public void setTotlePage(String totlapage) {
        if (this.totalpage == null) {
            return;
        }
        this.totalpage.setText(totlapage);
    }

    public void setTotlePage(@StringRes int totlapage) {
        if (this.totalpage == null) {
            return;
        }
        this.totalpage.setText(totlapage);
    }

    public void setTotlePageColor(int colorId) {
        if (this.totalpage == null) {
            return;
        }
        int resId = ASTUIUtil.getColor(getContext(), colorId);
        if (resId != 0) {
            this.totalpage.setTextColor(resId);
        } else {
            this.totalpage.setTextColor(colorId);
        }
    }

    public void setcenterImage(String imgUrl) {
        if (this.centerimage == null) {
            return;
        }
        this.centerimage.setVisibility(View.VISIBLE);
        if (imgUrl != null) {
            centerimage.setURL(imgUrl, R.drawable.noimage);
        }
    }

    public void setcenterImage(Drawable icon) {
        if (this.centerimage == null) {
            return;
        }
        this.centerimage.setVisibility(View.VISIBLE);
        if (icon != null) {
            this.centerimage.setImageDrawable(icon);
        }

    }

    public void setcenterImage(int iconId) {
        if (this.centerimage == null) {
            return;
        }
        this.centerimage.setVisibility(View.VISIBLE);
        this.centerimage.setImageResource(iconId);
    }

    public void setTopImage(int iconId) {
        if (this.topimage == null) {
            return;
        }
        this.topimage.setVisibility(View.VISIBLE);
        this.topimage.setImageResource(iconId);

    }

    public void setTotalPageCardViewColor(@ColorRes int colorId) {
        if (totalPageCardView == null) {
            return;
        }
        int resId = ASTUIUtil.getColor(colorId);
        if (resId != 0) {
            totalPageCardView.setCardBackgroundColor(resId);
        }
    }

    public void setTopImage(String imgUrl) {
        if (this.topimage == null) {
            return;
        }

        this.topimage.setVisibility(View.VISIBLE);
        if (imgUrl != null) {
            topimage.setURL(imgUrl, R.drawable.noimage);
        }
    }

    public void setTopImage(Drawable icon) {
        if (this.topimage == null) {
            return;
        }
        this.topimage.setVisibility(View.VISIBLE);
        if (icon != null) {
            this.topimage.setImageDrawable(icon);
        }

    }

    public void setleftIcon(int iconId) {
        if (this.lefticon == null) {
            return;
        }
        this.lefticon.setVisibility(View.VISIBLE);
        this.lefticon.setText(iconId);

    }

    public void setleftIcon(String iconStr) {
        if (this.lefticon == null) {
            return;
        }
        this.lefticon.setVisibility(View.VISIBLE);
        this.lefticon.setText(iconStr);
    }

    public void setrightIcon(int iconId) {
        if (this.righticon == null) {
            return;
        }
        this.righticon.setVisibility(View.VISIBLE);
        this.righticon.setText(iconId);
    }

    public void setrightIcon(String iconStr) {
        if (this.righticon == null) {
            return;
        }
        this.righticon.setVisibility(View.VISIBLE);
        this.righticon.setText(iconStr);
    }

    public void setOnActionListener(FNImageInfoTile.OnComponentActionListener listener) {
        this.listener = listener;
    }

    public void hidePrevIcon(boolean isHidden) {
        lefticon.setVisibility(isHidden ? View.INVISIBLE : View.VISIBLE);
    }

    public void hideNextIcon(boolean isHidden) {
        righticon.setVisibility(isHidden ? View.INVISIBLE : View.VISIBLE);
    }

    public void hideTotalPageView() {
        findViewById(R.id.totalpageContainer).setVisibility(View.INVISIBLE);
    }

    public void disabledIcons() {
        lefticon.setEnabled(false);
        righticon.setEnabled(false);
    }

    public void hideAllNavigationViews() {
        lefticon.setVisibility(View.INVISIBLE);
        righticon.setVisibility(View.INVISIBLE);
        totalpage.setVisibility(View.INVISIBLE);
    }

    public void setFooterTitle() {
        this.setFooterTitle(footerTitle);
    }

    public void setFooterSubtitle() {
        this.setFooterSubtitle(footerSubtitle);
    }

    public void setFooterTitle(String footerTitle) {
        if (footerTitle == null) {
            footerTitleView.setVisibility(GONE);
            return;
        }
        this.footerTitle = footerTitle;
        footerTitleView.setVisibility(VISIBLE);
        footerTitleView.setText(footerTitle);
    }

    public void setFooterSubtitle(String footerSubtitle) {
        if (footerSubtitle == null) {
            footerSubtitleView.setVisibility(GONE);
            return;
        }
        this.footerSubtitle = footerSubtitle;
        footerSubtitleView.setVisibility(VISIBLE);
        footerSubtitleView.setText(footerSubtitle);
    }

    @Override
    public void onClick(View v) {
        if (listener == null) {
            return;
        }
        if (v.getId() == R.id.lefticon) {
            listener.onPreviousClick();
        } else if (v.getId() == R.id.righticon) {
            listener.onNextClick();
        }
    }


    public interface OnComponentActionListener {
        void onNextClick();

        void onPreviousClick();
    }


}