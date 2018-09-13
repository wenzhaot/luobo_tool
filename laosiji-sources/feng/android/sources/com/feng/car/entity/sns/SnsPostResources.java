package com.feng.car.entity.sns;

import android.text.TextUtils;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.QiNiuInfo;
import com.feng.car.entity.thread.VideoUrl;
import com.feng.car.utils.HttpConstant;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class SnsPostResources {
    public String description = "";
    public int id = 0;
    public ImageInfo image = new ImageInfo();
    private transient Pattern patternLink = Pattern.compile("(\\[URL=(.[^\\[]*)\\])(.*?)(\\[\\/URL\\])");
    public int playcount = 0;
    public int playtime = 0;
    public QiNiuInfo qiniu = new QiNiuInfo();
    public String title = "";
    public int type = 1;
    public String url;
    public VideoUrl videourl = new VideoUrl();

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("type")) {
                this.type = object.getInt("type");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("title")) {
                this.title = object.getString("title");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.DESCRIPTION)) {
                Matcher matcher = this.patternLink.matcher(object.getString(HttpConstant.DESCRIPTION));
                StringBuffer sb = new StringBuffer();
                while (matcher.find()) {
                    matcher.appendReplacement(sb, matcher.group(3));
                }
                matcher.appendTail(sb);
                this.description = sb.toString();
                Matcher emoticon = HttpConstant.PATTERN_EMOTICON.matcher(this.description);
                while (emoticon.find()) {
                    String key = emoticon.group();
                    String value = (String) EmoticonsRule.sXhsEmoticonTextHashMap.get(key);
                    if (!TextUtils.isEmpty(value)) {
                        this.description = this.description.replace(key, value);
                    }
                }
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("image")) {
                this.image.parser(object.getJSONObject("image"));
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("videourl")) {
                this.videourl.parser(object.getJSONObject("videourl"));
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("url")) {
                if (object.get("url") instanceof String) {
                    this.url = object.getString("url");
                } else {
                    this.url = "";
                }
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("qiniu")) {
                this.qiniu.parser(object.getJSONObject("qiniu"));
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("playcount")) {
                this.playcount = object.getInt("playcount");
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
        try {
            if (object.has("playtime")) {
                this.playtime = object.getInt("playtime") * 1000;
            }
        } catch (Exception e222222222) {
            e222222222.printStackTrace();
        }
    }
}
