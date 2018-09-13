package com.tencent.liteav.txcvodplayer.a;

import android.net.Uri;
import android.util.Log;
import com.feng.car.video.shortvideo.FileUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.ijk.media.player.IjkMediaMeta;
import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/* compiled from: TXCVodCacheMgr */
public class b {
    private static final String a = b.class.getSimpleName();
    private static b b = new b();
    private ArrayList<a> c;
    private HashSet<a> d;
    private String e;
    private int f;
    private String g;

    /* compiled from: TXCVodCacheMgr */
    static class a implements Serializable {
        String fileType;
        String path;
        Long time;
        String url;

        a() {
        }

        public String a() {
            return this.url;
        }

        public void a(String str) {
            this.url = str;
        }

        public String b() {
            return this.path;
        }

        public void b(String str) {
            this.path = str;
        }

        public Long c() {
            return this.time;
        }

        public void a(Long l) {
            this.time = l;
        }

        public void c(String str) {
            this.fileType = str;
        }

        public String d() {
            if (this.fileType == null && this.path != null) {
                if (this.path.endsWith("mp4")) {
                    return "mp4";
                }
                if (this.path.endsWith("m3u8.sqlite")) {
                    return IjkMediaMeta.IJKM_KEY_M3U8;
                }
            }
            return this.fileType;
        }
    }

    public static b a() {
        return b;
    }

    public void a(int i) {
        this.f = i;
    }

    public void a(String str) {
        this.g = str;
    }

    public void b(String str) {
        String concat;
        if (str.endsWith("/")) {
            concat = str.concat("txvodcache");
        } else {
            concat = str.concat("/txvodcache");
        }
        if (this.e == null || !this.e.equals(concat)) {
            this.e = concat;
            if (this.e != null) {
                new File(this.e).mkdirs();
                if (!b()) {
                    d();
                }
            }
        }
    }

    public a c(String str) {
        if (this.e == null || str == null) {
            return null;
        }
        File file = new File(this.e);
        if (!file.mkdirs() && !file.isDirectory()) {
            return null;
        }
        a aVar;
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            aVar = (a) it.next();
            if (aVar.url.equals(str)) {
                a(aVar);
                this.d.add(aVar);
                return new a(aVar.path, this.e, aVar.fileType);
            }
        }
        it = this.c.iterator();
        while (it.hasNext() && this.c.size() > this.f) {
            aVar = (a) it.next();
            if (!this.d.contains(aVar)) {
                b(aVar);
                it.remove();
            }
        }
        a e = e(str);
        if (e == null) {
            return null;
        }
        this.d.add(e);
        return new a(e.path, this.e, e.fileType);
    }

    public boolean d(String str) {
        Uri parse = Uri.parse(str);
        if (parse == null || parse.getPath() == null || parse.getScheme() == null || !parse.getScheme().startsWith("http")) {
            return false;
        }
        if (parse.getPath().endsWith(".mp4") || parse.getPath().endsWith(IjkMediaMeta.IJKM_KEY_M3U8) || parse.getPath().endsWith(".MP4") || parse.getPath().endsWith("M3U8")) {
            return true;
        }
        return false;
    }

    boolean b() {
        this.c = new ArrayList();
        this.d = new HashSet();
        try {
            for (Node firstChild = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(this.e + "/" + "tx_cache.xml")).getElementsByTagName("caches").item(0).getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                a aVar = new a();
                for (Node firstChild2 = firstChild.getFirstChild(); firstChild2 != null; firstChild2 = firstChild2.getNextSibling()) {
                    if (firstChild2.getNodeName().equals("path")) {
                        aVar.b(firstChild2.getFirstChild().getNodeValue());
                    } else if (firstChild2.getNodeName().equals("time")) {
                        aVar.a(Long.valueOf(Long.parseLong(firstChild2.getFirstChild().getNodeValue())));
                    } else if (firstChild2.getNodeName().equals("url")) {
                        aVar.a(firstChild2.getFirstChild().getNodeValue());
                    } else if (firstChild2.getNodeName().equals("fileType")) {
                        aVar.c(firstChild2.getFirstChild().getNodeValue());
                    }
                }
                this.c.add(aVar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    void c() {
        try {
            Node newDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Object createElement = newDocument.createElement("caches");
            newDocument.appendChild(createElement);
            Iterator it = this.c.iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                Element createElement2 = newDocument.createElement("cache");
                createElement.appendChild(createElement2);
                Node createElement3 = newDocument.createElement("path");
                createElement3.appendChild(newDocument.createTextNode(aVar.b()));
                createElement2.appendChild(createElement3);
                createElement3 = newDocument.createElement("time");
                createElement3.appendChild(newDocument.createTextNode(aVar.c().toString()));
                createElement2.appendChild(createElement3);
                createElement3 = newDocument.createElement("url");
                createElement3.appendChild(newDocument.createTextNode(aVar.a()));
                createElement2.appendChild(createElement3);
                createElement3 = newDocument.createElement("fileType");
                createElement3.appendChild(newDocument.createTextNode(aVar.d()));
                createElement2.appendChild(createElement3);
            }
            Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
            Source dOMSource = new DOMSource(newDocument);
            Result streamResult = new StreamResult();
            streamResult.setSystemId(new File(this.e, "tx_cache.xml").getAbsolutePath());
            newTransformer.transform(dOMSource, streamResult);
            System.out.println("File saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void a(a aVar) {
        aVar.time = Long.valueOf(System.currentTimeMillis());
        this.c.remove(aVar);
        this.c.add(aVar);
        c();
    }

    a e(String str) {
        a aVar = new a();
        aVar.url = str;
        aVar.time = Long.valueOf(System.currentTimeMillis());
        String f = f(str);
        Uri parse = Uri.parse(str);
        if (parse.getPath().endsWith(".mp4") || parse.getPath().endsWith(".MP4")) {
            if (this.g != null) {
                aVar.b(f + FileUtils.FILE_EXTENSION_SEPARATOR + this.g);
            } else {
                aVar.b(f + ".mp4");
            }
            aVar.c("mp4");
        } else if (!parse.getPath().endsWith(".m3u8") && !parse.getPath().endsWith(".M3U8")) {
            return null;
        } else {
            aVar.b(f + ".m3u8.sqlite");
            aVar.c(IjkMediaMeta.IJKM_KEY_M3U8);
        }
        this.c.add(aVar);
        c();
        return aVar;
    }

    public static String f(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
            StringBuilder stringBuilder = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                if ((b & 255) < 16) {
                    stringBuilder.append(PushConstants.PUSH_TYPE_NOTIFY);
                }
                stringBuilder.append(Integer.toHexString(b & 255));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private void d() {
        if (new File(this.e).listFiles().length > 0) {
            Log.w(a, "!!!警告：TXVodPlayer缓存目录不为空 " + this.e + "!!!");
        }
    }

    private void a(String str, String str2) {
        String str3 = this.e + "/" + str;
        new File(str3).delete();
        if (str2.equals("mp4")) {
            new File(str3.concat(".info")).delete();
        }
        Log.w(a, "delete " + str3);
    }

    private void b(a aVar) {
        a(aVar.b(), aVar.d());
    }

    public void a(String str, boolean z) {
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            a aVar = (a) it.next();
            if (aVar.b().equals(str)) {
                it.remove();
                if (z) {
                    b(aVar);
                    this.c.remove(aVar);
                    c();
                }
            }
        }
    }
}
