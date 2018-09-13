package com.feng.library.emoticons.keyboard.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import com.feng.library.emoticons.keyboard.utils.imageloader.ImageBase.Scheme;
import com.stub.StubApp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

public class ImageLoader implements ImageBase {
    private static volatile Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");
    private static volatile ImageLoader instance;
    protected final Context context;

    public static ImageLoader getInstance(Context context) {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader(context);
                }
            }
        }
        return instance;
    }

    public ImageLoader(Context context) {
        this.context = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    public void displayImage(String uriStr, ImageView imageView) throws IOException {
        switch (Scheme.ofUri(uriStr)) {
            case FILE:
                displayImageFromFile(uriStr, imageView);
                return;
            case ASSETS:
                displayImageFromAssets(uriStr, imageView);
                return;
            case DRAWABLE:
                displayImageFromDrawable(uriStr, imageView);
                return;
            case HTTP:
            case HTTPS:
                displayImageFromNetwork(uriStr, imageView);
                return;
            case CONTENT:
                displayImageFromContent(uriStr, imageView);
                return;
            default:
                if (NUMBER_PATTERN.matcher(uriStr).matches()) {
                    displayImageFromResource(Integer.parseInt(uriStr), imageView);
                    return;
                } else {
                    displayImageFromOtherSource(uriStr, imageView);
                    return;
                }
        }
    }

    protected void displayImageFromFile(String imageUri, ImageView imageView) throws IOException {
        String filePath = Scheme.FILE.crop(imageUri);
        if (new File(filePath).exists()) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void displayImageFromAssets(String imageUri, ImageView imageView) throws IOException {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(this.context.getAssets().open(Scheme.ASSETS.crop(imageUri)));
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void displayImageFromDrawable(String imageUri, ImageView imageView) {
        String drawableIdString = Scheme.DRAWABLE.crop(imageUri);
        int resID = this.context.getResources().getIdentifier(drawableIdString, "mipmap", this.context.getPackageName());
        if (resID <= 0) {
            resID = this.context.getResources().getIdentifier(drawableIdString, "drawable", this.context.getPackageName());
        }
        if (resID > 0 && imageView != null) {
            imageView.setImageResource(resID);
        }
    }

    protected void displayImageFromResource(int resID, ImageView imageView) {
        if (resID > 0 && imageView != null) {
            imageView.setImageResource(resID);
        }
    }

    protected void displayImageFromNetwork(String imageUri, Object extra) throws IOException {
    }

    protected void displayImageFromContent(String imageUri, ImageView imageView) throws FileNotFoundException {
    }

    protected void displayImageFromOtherSource(String imageUri, ImageView imageView) throws IOException {
    }
}
