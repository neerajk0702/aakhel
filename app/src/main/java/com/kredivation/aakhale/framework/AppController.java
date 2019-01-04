package com.kredivation.aakhale.framework;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.kredivation.aakhale.Constants;
import com.kredivation.aakhale.activity.SelectUserTypeActivity;
import com.kredivation.aakhale.utility.ASTEnum;
import com.kredivation.aakhale.utility.ASTObjectUtil;

/**
 * Created by Neeraj on 7/25/2017.
 */
public class AppController extends MultiDexApplication {
    private SelectUserTypeActivity _activity;
    public static final String TAG = AppController.class
            .getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static AppController mInstance;

    private Typeface _sTypeface;
    public volatile MenuItem lastMenuItem;
    private SharedPreferences sharedPref;
    private String packageName;
    private Typeface _fontRegular;
    private Typeface _fontBold;
    private Typeface _fontItalic;
    private Typeface _fontBoldItalic;
    private Typeface _fontSemiBold;
    private Typeface _fontSemiBoldItalic;
    private Typeface _fontLightItalic;
    private Typeface _fontExtraLightItalic;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public SelectUserTypeActivity getActivity() {
        return this._activity;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public int getResourceId(String resourceName, String defType) {
        return getResourceId(resourceName, defType, this.packageName());
    }

    public int getResourceId(String resourceName, String defType, String packageName) {
        return (!ASTObjectUtil.isNumber(resourceName) && ASTObjectUtil.isNonEmptyStr(resourceName)) ? this.getResources().getIdentifier(resourceName, defType, packageName) : 0;
    }

    public String packageName() {
        if (this.packageName == null) {
            PackageInfo info = this.packageInfo();
            this.packageName = info != null ? info.packageName : "com.kredivation.aakhale";
        }
        return this.packageName;
    }

    public PackageInfo packageInfo() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
           // FNExceptionUtil.logException(e2, getApplicationContext());
        }
        return info;
    }

    public Typeface getFontTypeFace(ASTEnum fontType) {
        switch (fontType) {
            case FONT_BOLD:
                if (this._fontBold == null) {
                    initFont(fontType);
                }
                return this._fontBold;
            case FONT_ITALIC:
                if (this._fontItalic == null) {
                    initFont(fontType);
                }
                return this._fontItalic;
            case FONT_BOLD_ITALIC:
                if (this._fontBoldItalic == null) {
                    initFont(fontType);
                }
                return this._fontBoldItalic;
            case FONT_SEMIBOLD:
                if (this._fontSemiBold == null) {
                    initFont(fontType);
                }
                return this._fontSemiBold;
            case FONT_SEMIBOLD_ITALIC:
                if (this._fontSemiBoldItalic == null) {
                    initFont(fontType);
                }
                return this._fontSemiBoldItalic;
            case FONT_LIGHT_ITALIC:
                if (this._fontLightItalic == null) {
                    initFont(fontType);
                }
                return this._fontLightItalic;
            case FONT_EXTRALIGHT_ITALIC:
                if (this._fontExtraLightItalic == null) {
                    initFont(fontType);
                }
                return this._fontExtraLightItalic;
            default:
                if (this._fontRegular == null) {
                    initFont(fontType);
                }
                return this._fontRegular;
        }
    }


    public void initFont(ASTEnum fontType) {
        switch (fontType) {
            case FONT_BOLD:
                this._fontBold = Typeface.createFromAsset(this.getAssets(), Constants.FONT_Bold);
                break;
            case FONT_BOLD_ITALIC:
                this._fontBoldItalic = Typeface.createFromAsset(this.getAssets(), Constants.FONT_medium);
                break;
            case FONT_ITALIC:
                this._fontItalic = Typeface.createFromAsset(this.getAssets(), Constants.FONT_Italic);
                break;
            case FONT_SEMIBOLD:
                this._fontSemiBold = Typeface.createFromAsset(this.getAssets(), Constants.FONT_Semi_Bold);
                break;
            case FONT_LIGHT_ITALIC:
                this._fontLightItalic = Typeface.createFromAsset(this.getAssets(), Constants.FONT_Light_Italic);
                break;
            case FONT_EXTRALIGHT_ITALIC:
                this._fontExtraLightItalic = Typeface.createFromAsset(this.getAssets(), Constants.FONT_Roboto_thin);
                break;
            default:
                this._fontRegular = Typeface.createFromAsset(this.getAssets(), Constants.FONT_Regular);
                break;

        }
    }
}
