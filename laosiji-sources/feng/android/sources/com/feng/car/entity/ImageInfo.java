package com.feng.car.entity;

import android.text.TextUtils;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.HttpConstant;
import java.io.Serializable;
import org.json.JSONObject;

public class ImageInfo extends BaseInfo implements Serializable {
    public String description = "";
    public String hash = "";
    public int height = 0;
    public int id = 0;
    public String lowUrl = "";
    public String mime = FengConstant.JPG;
    public int mimetype = 1;
    public String size;
    public String url = "";
    public int width = 0;

    public String getImageMime() {
        switch (this.mimetype) {
            case 1:
                return FengConstant.JPG;
            case 2:
                return FengConstant.PNG;
            case 3:
                return FengConstant.GIF;
            case 4:
                return FengConstant.BMP;
            default:
                return FengConstant.JPG;
        }
    }

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            this.id = 0;
        }
        try {
            if (object.has("url") && !object.isNull("url")) {
                this.url = object.getString("url");
            }
        } catch (Exception e2) {
            this.url = "";
        }
        try {
            if (object.has("width")) {
                this.width = object.getInt("width");
            }
        } catch (Exception e3) {
            this.width = 0;
        }
        try {
            if (object.has("height")) {
                this.height = object.getInt("height");
            }
        } catch (Exception e4) {
            this.height = 0;
        }
        try {
            if (object.has("mimetype")) {
                this.mimetype = object.getInt("mimetype");
            }
        } catch (Exception e5) {
            this.mimetype = 1;
        }
        try {
            if (object.has(HttpConstant.DESCRIPTION)) {
                this.description = object.getString(HttpConstant.DESCRIPTION);
            }
        } catch (Exception e6) {
            this.description = "";
        }
        try {
            if (object.has("hash") && !object.isNull("hash")) {
                this.hash = object.getString("hash");
            }
        } catch (Exception e7) {
            this.hash = "";
        }
    }

    public void setMimetype(String mime) {
        if (!TextUtils.isEmpty(mime)) {
            int i = -1;
            switch (mime.hashCode()) {
                case -1487394660:
                    if (mime.equals(FengConstant.JPG)) {
                        i = 0;
                        break;
                    }
                    break;
                case -879272239:
                    if (mime.equals(FengConstant.BMP)) {
                        i = 3;
                        break;
                    }
                    break;
                case -879267568:
                    if (mime.equals(FengConstant.GIF)) {
                        i = 2;
                        break;
                    }
                    break;
                case -879258763:
                    if (mime.equals(FengConstant.PNG)) {
                        i = 1;
                        break;
                    }
                    break;
            }
            switch (i) {
                case 0:
                    this.mimetype = 1;
                    this.mime = FengConstant.JPG;
                    return;
                case 1:
                    this.mimetype = 2;
                    this.mime = FengConstant.PNG;
                    return;
                case 2:
                    this.mimetype = 3;
                    this.mime = FengConstant.GIF;
                    return;
                case 3:
                    this.mimetype = 4;
                    this.mime = FengConstant.BMP;
                    return;
                default:
                    return;
            }
        }
    }
}
