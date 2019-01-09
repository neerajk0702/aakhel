package com.kredivation.aakhale.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FilePickerHelper {
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;

    //open camera
    public static void cameraIntent(Context context, String fileName) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Utility.getExternalStorageFilePathCreateAppDirectory(context) + File.separator + fileName);
        String providerName = String.format(Locale.ENGLISH, "%s%s", context.getPackageName(), ".imagepicker.provider");
        Uri uri = FileProvider.getUriForFile(context, providerName, file);
        grantAppPermission(context, intent, uri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        ((Activity) context).startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
    }

    public static void grantAppPermission(Context context, Intent intent, Uri fileUri) {
        List<ResolveInfo> resolvedIntentActivities = context.getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
            String packageName = resolvedIntentInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, fileUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }


    //----------image compress
    public static Bitmap compressImage(String imagePath, int imgOrientation, float maxWidth, float maxHeight) {
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(imagePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (Exception exception) {
            //FNExceptionUtil.logException(exception);
        }
        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);

        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, paint);
        setTimeStampIntoImage(canvas, paint, middleY);
        Matrix matrix = new Matrix();
        matrix.postRotate(getRotateDegreeFromOrientation(imgOrientation));
        scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        return scaledBitmap;
    }

    public static int getRotateDegreeFromOrientation(int orientation) {
        int degree = 0;
        switch (orientation) {
            case ExifInterface.ORIENTATION_UNDEFINED:
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 270;
                break;
            default:
                break;
        }
        return degree;
    }

    public static int getExifRotation(File file) {
        if (file == null)
            return 0;
        try {
            ExifInterface exif = new ExifInterface(file.getAbsolutePath());
            return getRotateDegreeFromOrientation(
                    exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    //set time stamp into image
    private static void setTimeStampIntoImage(Canvas canvas, Paint paint, float middleY) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        String dateTime = sdf.format(Calendar.getInstance().getTime()); // reading local time in the system
        // paint.setAntiAlias(true);
        //paint.setFilterBitmap(true);
        paint.setColor(Color.RED);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(dateTime, 10, middleY / 3, paint);
    }
    // convert i/o stream into byte array

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024 * 16;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        Uri uri = null;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        // inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        if (path != null) {
            uri = Uri.parse(path);
        }
        return uri;
    }
  /*  //convert uri into file
    private Boolean addUriAsFile(final Uri uri, final String fileName) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {

                Boolean flag = false;
                // File sdcardPath = ASTUtil.getFilePath(getContext());
                //sdcardPath.mkdirs();
                File imgFile = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator, fileName);
                try {
                    InputStream iStream = getContext().getContentResolver().openInputStream(uri);
                    byte[] inputData = getBytes(iStream);

                    FileOutputStream fOut = new FileOutputStream(imgFile);
                    fOut.write(inputData);
                    //   bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    iStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Boolean flag) {
                super.onPostExecute(flag);

            }
        }.execute();

        return true;
    }*/

    public String getFilename() {
        //File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        File file = new File(Environment.getExternalStorageDirectory(), Contants.APP_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    /* private String getRealPathFromURI(Uri uri) {
         Uri contentUri = uri;//Uri.parse(contentURI);
         Cursor cursor = getActivity().getContentResolver().query(contentUri, null, null, null, null);
         if (cursor == null) {
             return contentUri.getPath();
         } else {
             cursor.moveToFirst();
             int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
             return cursor.getString(index);
         }
     }*/
    private Bitmap timestampItAndSave(Bitmap toEdit) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        String dateTime = sdf.format(Calendar.getInstance().getTime()); // reading local time in the system
        Bitmap dest = Bitmap.createBitmap(toEdit.getWidth(), toEdit.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas cs = new Canvas(dest);
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(10);
        //textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        textPaint.setTextAlign(Paint.Align.LEFT);
        Paint.FontMetrics metric = textPaint.getFontMetrics();
        int textHeight = (int) Math.ceil(metric.descent - metric.ascent);
        int y = (int) (textHeight - metric.descent);
        cs.drawBitmap(toEdit, 0, 0, textPaint);
        cs.drawText(dateTime, 0, y, textPaint);
        return dest;
    }
}
