package com.meizu.cloud.pushsdk.pushtracer.tracker;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.meizu.cloud.pushsdk.pushtracer.utils.Logger;
import com.meizu.cloud.pushsdk.pushtracer.utils.Util;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Subject {
    private static String TAG = Subject.class.getSimpleName();
    private HashMap<String, Object> geoLocationPairs;
    private HashMap<String, String> mobilePairs;
    private HashMap<String, String> standardPairs;

    public static class SubjectBuilder {
        private Context context = null;

        public SubjectBuilder context(Context context) {
            this.context = context;
            return this;
        }

        public Subject build() {
            return new Subject(this);
        }
    }

    private Subject(SubjectBuilder builder) {
        this.standardPairs = new HashMap();
        this.geoLocationPairs = new HashMap();
        this.mobilePairs = new HashMap();
        setOsType();
        setOsVersion();
        setDeviceModel();
        setDeviceVendor();
        if (builder.context != null) {
            setContextualParams(builder.context);
        }
        Logger.i(TAG, "Subject created successfully.", new Object[0]);
    }

    public void setContextualParams(Context context) {
        setLocation(context);
        setCarrier(context);
    }

    private void addToMobileContext(String key, String value) {
        if (key != null && value != null && !key.isEmpty() && !value.isEmpty()) {
            this.mobilePairs.put(key, value);
        }
    }

    private void addToGeoLocationContext(String key, Object value) {
        if ((key != null && value != null && !key.isEmpty()) || ((value instanceof String) && !((String) value).isEmpty())) {
            this.geoLocationPairs.put(key, value);
        }
    }

    private void setDefaultTimezone() {
        setTimezone(Calendar.getInstance().getTimeZone().getID());
    }

    private void setDefaultLanguage() {
        setLanguage(Locale.getDefault().getDisplayLanguage());
    }

    private void setOsType() {
        addToMobileContext(Parameters.OS_TYPE, "android-" + VERSION.RELEASE);
    }

    private void setOsVersion() {
        addToMobileContext(Parameters.OS_VERSION, Build.DISPLAY);
    }

    private void setDeviceModel() {
        addToMobileContext(Parameters.DEVICE_MODEL, Build.MODEL);
    }

    private void setDeviceVendor() {
        addToMobileContext(Parameters.DEVICE_MANUFACTURER, Build.MANUFACTURER);
    }

    @TargetApi(19)
    public void setDefaultScreenResolution(Context context) {
        Display display = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point size = new Point();
        try {
            Display.class.getMethod("getSize", new Class[]{Point.class});
            display.getSize(size);
            setScreenResolution(size.x, size.y);
        } catch (NoSuchMethodException e) {
            Logger.e(TAG, "Display.getSize isn't available on older devices.", new Object[0]);
            setScreenResolution(display.getWidth(), display.getHeight());
        }
    }

    public void setLocation(Context context) {
        Location location = Util.getLocation(context);
        if (location == null) {
            Logger.e(TAG, "Location information not available.", new Object[0]);
            return;
        }
        addToGeoLocationContext(Parameters.LATITUDE, Double.valueOf(location.getLatitude()));
        addToGeoLocationContext(Parameters.LONGITUDE, Double.valueOf(location.getLongitude()));
        addToGeoLocationContext(Parameters.ALTITUDE, Double.valueOf(location.getAltitude()));
        addToGeoLocationContext(Parameters.LATLONG_ACCURACY, Float.valueOf(location.getAccuracy()));
        addToGeoLocationContext(Parameters.SPEED, Float.valueOf(location.getSpeed()));
        addToGeoLocationContext(Parameters.BEARING, Float.valueOf(location.getBearing()));
    }

    public void setCarrier(Context context) {
        String carrier = Util.getCarrier(context);
        if (carrier != null) {
            addToMobileContext(Parameters.CARRIER, carrier);
        }
    }

    public void setUserId(String userId) {
        this.standardPairs.put("uid", userId);
    }

    public void setScreenResolution(int width, int height) {
        this.standardPairs.put("res", Integer.toString(width) + "x" + Integer.toString(height));
    }

    public void setViewPort(int width, int height) {
        this.standardPairs.put(Parameters.VIEWPORT, Integer.toString(width) + "x" + Integer.toString(height));
    }

    public void setColorDepth(int depth) {
        this.standardPairs.put(Parameters.COLOR_DEPTH, Integer.toString(depth));
    }

    public void setTimezone(String timezone) {
        this.standardPairs.put(Parameters.TIMEZONE, timezone);
    }

    public void setLanguage(String language) {
        this.standardPairs.put(Parameters.LANGUAGE, language);
    }

    public void setIpAddress(String ipAddress) {
        this.standardPairs.put("ip", ipAddress);
    }

    public void setUseragent(String useragent) {
        this.standardPairs.put(Parameters.USERAGENT, useragent);
    }

    public void setNetworkUserId(String networkUserId) {
        this.standardPairs.put(Parameters.NETWORK_UID, networkUserId);
    }

    public void setDomainUserId(String domainUserId) {
        this.standardPairs.put(Parameters.DOMAIN_UID, domainUserId);
    }

    public Map<String, Object> getSubjectLocation() {
        return this.geoLocationPairs;
    }

    public Map<String, String> getSubjectMobile() {
        return this.mobilePairs;
    }

    public Map<String, String> getSubject() {
        return this.standardPairs;
    }
}
