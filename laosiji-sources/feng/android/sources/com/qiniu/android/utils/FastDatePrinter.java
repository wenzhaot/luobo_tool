package com.qiniu.android.utils;

import com.feng.car.utils.PermissionsConstant;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public final class FastDatePrinter {
    public static final int FULL = 0;
    public static final int LONG = 1;
    private static final int MAX_DIGITS = 10;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private final Locale mLocale;
    private transient int mMaxLengthEstimate;
    private final String mPattern;
    private transient Rule[] mRules;
    private final TimeZone mTimeZone;

    private interface Rule {
        void appendTo(Appendable appendable, Calendar calendar) throws IOException;

        int estimateLength();
    }

    private static class CharacterLiteral implements Rule {
        private final char mValue;

        CharacterLiteral(char value) {
            this.mValue = value;
        }

        public int estimateLength() {
            return 1;
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            buffer.append(this.mValue);
        }
    }

    private interface NumberRule extends Rule {
        void appendTo(Appendable appendable, int i) throws IOException;
    }

    private static class DayInWeekField implements NumberRule {
        private final NumberRule mRule;

        DayInWeekField(NumberRule rule) {
            this.mRule = rule;
        }

        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            int i = 7;
            int value = calendar.get(7);
            NumberRule numberRule = this.mRule;
            if (value != 1) {
                i = value - 1;
            }
            numberRule.appendTo(buffer, i);
        }

        public void appendTo(Appendable buffer, int value) throws IOException {
            this.mRule.appendTo(buffer, value);
        }
    }

    private static class Iso8601_Rule implements Rule {
        static final Iso8601_Rule ISO8601_HOURS = new Iso8601_Rule(3);
        static final Iso8601_Rule ISO8601_HOURS_COLON_MINUTES = new Iso8601_Rule(6);
        static final Iso8601_Rule ISO8601_HOURS_MINUTES = new Iso8601_Rule(5);
        final int length;

        Iso8601_Rule(int length) {
            this.length = length;
        }

        static Iso8601_Rule getRule(int tokenLen) {
            switch (tokenLen) {
                case 1:
                    return ISO8601_HOURS;
                case 2:
                    return ISO8601_HOURS_MINUTES;
                case 3:
                    return ISO8601_HOURS_COLON_MINUTES;
                default:
                    throw new IllegalArgumentException("invalid number of X");
            }
        }

        public int estimateLength() {
            return this.length;
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            int offset = calendar.get(15) + calendar.get(16);
            if (offset == 0) {
                buffer.append("Z");
                return;
            }
            if (offset < 0) {
                buffer.append('-');
                offset = -offset;
            } else {
                buffer.append('+');
            }
            int hours = offset / 3600000;
            FastDatePrinter.appendDigits(buffer, hours);
            if (this.length >= 5) {
                if (this.length == 6) {
                    buffer.append(':');
                }
                FastDatePrinter.appendDigits(buffer, (offset / PermissionsConstant.CODE_FOR_DOWN_WRITE_PERMISSION_BASE) - (hours * 60));
            }
        }
    }

    private static class PaddedNumberField implements NumberRule {
        private final int mField;
        private final int mSize;

        PaddedNumberField(int field, int size) {
            if (size < 3) {
                throw new IllegalArgumentException();
            }
            this.mField = field;
            this.mSize = size;
        }

        public int estimateLength() {
            return this.mSize;
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            appendTo(buffer, calendar.get(this.mField));
        }

        public final void appendTo(Appendable buffer, int value) throws IOException {
            FastDatePrinter.appendFullDigits(buffer, value, this.mSize);
        }
    }

    private static class StringLiteral implements Rule {
        private final String mValue;

        StringLiteral(String value) {
            this.mValue = value;
        }

        public int estimateLength() {
            return this.mValue.length();
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            buffer.append(this.mValue);
        }
    }

    private static class TextField implements Rule {
        private final int mField;
        private final String[] mValues;

        TextField(int field, String[] values) {
            this.mField = field;
            this.mValues = values;
        }

        public int estimateLength() {
            int max = 0;
            int i = this.mValues.length;
            while (true) {
                i--;
                if (i < 0) {
                    return max;
                }
                int len = this.mValues[i].length();
                if (len > max) {
                    max = len;
                }
            }
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            buffer.append(this.mValues[calendar.get(this.mField)]);
        }
    }

    private static class TimeZoneNumberRule implements Rule {
        static final TimeZoneNumberRule INSTANCE_COLON = new TimeZoneNumberRule(true);
        static final TimeZoneNumberRule INSTANCE_NO_COLON = new TimeZoneNumberRule(false);
        final boolean mColon;

        TimeZoneNumberRule(boolean colon) {
            this.mColon = colon;
        }

        public int estimateLength() {
            return 5;
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            int offset = calendar.get(15) + calendar.get(16);
            if (offset < 0) {
                buffer.append('-');
                offset = -offset;
            } else {
                buffer.append('+');
            }
            int hours = offset / 3600000;
            FastDatePrinter.appendDigits(buffer, hours);
            if (this.mColon) {
                buffer.append(':');
            }
            FastDatePrinter.appendDigits(buffer, (offset / PermissionsConstant.CODE_FOR_DOWN_WRITE_PERMISSION_BASE) - (hours * 60));
        }
    }

    private static class TwelveHourField implements NumberRule {
        private final NumberRule mRule;

        TwelveHourField(NumberRule rule) {
            this.mRule = rule;
        }

        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            int value = calendar.get(10);
            if (value == 0) {
                value = calendar.getLeastMaximum(10) + 1;
            }
            this.mRule.appendTo(buffer, value);
        }

        public void appendTo(Appendable buffer, int value) throws IOException {
            this.mRule.appendTo(buffer, value);
        }
    }

    private static class TwentyFourHourField implements NumberRule {
        private final NumberRule mRule;

        TwentyFourHourField(NumberRule rule) {
            this.mRule = rule;
        }

        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            int value = calendar.get(11);
            if (value == 0) {
                value = calendar.getMaximum(11) + 1;
            }
            this.mRule.appendTo(buffer, value);
        }

        public void appendTo(Appendable buffer, int value) throws IOException {
            this.mRule.appendTo(buffer, value);
        }
    }

    private static class TwoDigitMonthField implements NumberRule {
        static final TwoDigitMonthField INSTANCE = new TwoDigitMonthField();

        TwoDigitMonthField() {
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            appendTo(buffer, calendar.get(2) + 1);
        }

        public final void appendTo(Appendable buffer, int value) throws IOException {
            FastDatePrinter.appendDigits(buffer, value);
        }
    }

    private static class TwoDigitNumberField implements NumberRule {
        private final int mField;

        TwoDigitNumberField(int field) {
            this.mField = field;
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            appendTo(buffer, calendar.get(this.mField));
        }

        public final void appendTo(Appendable buffer, int value) throws IOException {
            if (value < 100) {
                FastDatePrinter.appendDigits(buffer, value);
            } else {
                FastDatePrinter.appendFullDigits(buffer, value, 2);
            }
        }
    }

    private static class TwoDigitYearField implements NumberRule {
        static final TwoDigitYearField INSTANCE = new TwoDigitYearField();

        TwoDigitYearField() {
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            appendTo(buffer, calendar.get(1) % 100);
        }

        public final void appendTo(Appendable buffer, int value) throws IOException {
            FastDatePrinter.appendDigits(buffer, value);
        }
    }

    private static class UnpaddedMonthField implements NumberRule {
        static final UnpaddedMonthField INSTANCE = new UnpaddedMonthField();

        UnpaddedMonthField() {
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            appendTo(buffer, calendar.get(2) + 1);
        }

        public final void appendTo(Appendable buffer, int value) throws IOException {
            if (value < 10) {
                buffer.append((char) (value + 48));
            } else {
                FastDatePrinter.appendDigits(buffer, value);
            }
        }
    }

    private static class UnpaddedNumberField implements NumberRule {
        private final int mField;

        UnpaddedNumberField(int field) {
            this.mField = field;
        }

        public int estimateLength() {
            return 4;
        }

        public void appendTo(Appendable buffer, Calendar calendar) throws IOException {
            appendTo(buffer, calendar.get(this.mField));
        }

        public final void appendTo(Appendable buffer, int value) throws IOException {
            if (value < 10) {
                buffer.append((char) (value + 48));
            } else if (value < 100) {
                FastDatePrinter.appendDigits(buffer, value);
            } else {
                FastDatePrinter.appendFullDigits(buffer, value, 1);
            }
        }
    }

    public FastDatePrinter(String pattern, TimeZone timeZone, Locale locale) {
        this.mPattern = pattern;
        this.mTimeZone = timeZone;
        this.mLocale = locale;
        init();
    }

    private static void appendDigits(Appendable buffer, int value) throws IOException {
        buffer.append((char) ((value / 10) + 48));
        buffer.append((char) ((value % 10) + 48));
    }

    /* JADX WARNING: Missing block: B:14:0x0031, code:
            if (r10 < 100) goto L_0x0050;
     */
    /* JADX WARNING: Missing block: B:15:0x0033, code:
            r9.append((char) ((r10 / 100) + 48));
            r10 = r10 % 100;
     */
    /* JADX WARNING: Missing block: B:16:0x003d, code:
            if (r10 < 10) goto L_0x0054;
     */
    /* JADX WARNING: Missing block: B:17:0x003f, code:
            r9.append((char) ((r10 / 10) + 48));
            r10 = r10 % 10;
     */
    /* JADX WARNING: Missing block: B:18:0x0049, code:
            r9.append((char) (r10 + 48));
     */
    /* JADX WARNING: Missing block: B:19:0x0050, code:
            r9.append('0');
     */
    /* JADX WARNING: Missing block: B:20:0x0054, code:
            r9.append('0');
     */
    /* JADX WARNING: Missing block: B:35:?, code:
            return;
     */
    private static void appendFullDigits(java.lang.Appendable r9, int r10, int r11) throws java.io.IOException {
        /*
        r8 = 100;
        r7 = 10;
        r6 = 48;
        r5 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        if (r10 >= r5) goto L_0x0058;
    L_0x000a:
        r3 = 4;
        r5 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        if (r10 >= r5) goto L_0x0019;
    L_0x000f:
        r3 = r3 + -1;
        if (r10 >= r8) goto L_0x0019;
    L_0x0013:
        r3 = r3 + -1;
        if (r10 >= r7) goto L_0x0019;
    L_0x0017:
        r3 = r3 + -1;
    L_0x0019:
        r2 = r11 - r3;
    L_0x001b:
        if (r2 <= 0) goto L_0x0023;
    L_0x001d:
        r9.append(r6);
        r2 = r2 + -1;
        goto L_0x001b;
    L_0x0023:
        switch(r3) {
            case 1: goto L_0x0049;
            case 2: goto L_0x003d;
            case 3: goto L_0x0031;
            case 4: goto L_0x0027;
            default: goto L_0x0026;
        };
    L_0x0026:
        return;
    L_0x0027:
        r5 = r10 / 1000;
        r5 = r5 + 48;
        r5 = (char) r5;
        r9.append(r5);
        r10 = r10 % 1000;
    L_0x0031:
        if (r10 < r8) goto L_0x0050;
    L_0x0033:
        r5 = r10 / 100;
        r5 = r5 + 48;
        r5 = (char) r5;
        r9.append(r5);
        r10 = r10 % 100;
    L_0x003d:
        if (r10 < r7) goto L_0x0054;
    L_0x003f:
        r5 = r10 / 10;
        r5 = r5 + 48;
        r5 = (char) r5;
        r9.append(r5);
        r10 = r10 % 10;
    L_0x0049:
        r5 = r10 + 48;
        r5 = (char) r5;
        r9.append(r5);
        goto L_0x0026;
    L_0x0050:
        r9.append(r6);
        goto L_0x003d;
    L_0x0054:
        r9.append(r6);
        goto L_0x0049;
    L_0x0058:
        r4 = new char[r7];
        r0 = 0;
        r1 = r0;
    L_0x005c:
        if (r10 == 0) goto L_0x006b;
    L_0x005e:
        r0 = r1 + 1;
        r5 = r10 % 10;
        r5 = r5 + 48;
        r5 = (char) r5;
        r4[r1] = r5;
        r10 = r10 / 10;
        r1 = r0;
        goto L_0x005c;
    L_0x006b:
        if (r1 >= r11) goto L_0x007d;
    L_0x006d:
        r9.append(r6);
        r11 = r11 + -1;
        goto L_0x006b;
    L_0x0073:
        r0 = r0 + -1;
        if (r0 < 0) goto L_0x0026;
    L_0x0077:
        r5 = r4[r0];
        r9.append(r5);
        goto L_0x0073;
    L_0x007d:
        r0 = r1;
        goto L_0x0073;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiniu.android.utils.FastDatePrinter.appendFullDigits(java.lang.Appendable, int, int):void");
    }

    private void init() {
        List<Rule> rulesList = parsePattern();
        this.mRules = (Rule[]) rulesList.toArray(new Rule[rulesList.size()]);
        int len = 0;
        int i = this.mRules.length;
        while (true) {
            i--;
            if (i >= 0) {
                len += this.mRules[i].estimateLength();
            } else {
                this.mMaxLengthEstimate = len;
                return;
            }
        }
    }

    protected List<Rule> parsePattern() {
        DateFormatSymbols symbols = new DateFormatSymbols(this.mLocale);
        List<Rule> rules = new ArrayList();
        String[] ERAs = symbols.getEras();
        String[] months = symbols.getMonths();
        String[] shortMonths = symbols.getShortMonths();
        String[] weekdays = symbols.getWeekdays();
        String[] shortWeekdays = symbols.getShortWeekdays();
        String[] AmPmStrings = symbols.getAmPmStrings();
        int length = this.mPattern.length();
        int[] indexRef = new int[1];
        int i = 0;
        while (i < length) {
            indexRef[0] = i;
            String token = parseToken(this.mPattern, indexRef);
            i = indexRef[0];
            int tokenLen = token.length();
            if (tokenLen == 0) {
                return rules;
            }
            Rule rule;
            switch (token.charAt(0)) {
                case '\'':
                    String sub = token.substring(1);
                    if (sub.length() != 1) {
                        rule = new StringLiteral(sub);
                        break;
                    }
                    rule = new CharacterLiteral(sub.charAt(0));
                    break;
                case 'D':
                    rule = selectNumberRule(6, tokenLen);
                    break;
                case 'E':
                    String[] strArr;
                    if (tokenLen < 4) {
                        strArr = shortWeekdays;
                    } else {
                        strArr = weekdays;
                    }
                    rule = new TextField(7, strArr);
                    break;
                case 'F':
                    rule = selectNumberRule(8, tokenLen);
                    break;
                case 'G':
                    rule = new TextField(0, ERAs);
                    break;
                case 'H':
                    rule = selectNumberRule(11, tokenLen);
                    break;
                case 'K':
                    rule = selectNumberRule(10, tokenLen);
                    break;
                case 'M':
                    if (tokenLen < 4) {
                        if (tokenLen != 3) {
                            if (tokenLen != 2) {
                                rule = UnpaddedMonthField.INSTANCE;
                                break;
                            }
                            rule = TwoDigitMonthField.INSTANCE;
                            break;
                        }
                        rule = new TextField(2, shortMonths);
                        break;
                    }
                    rule = new TextField(2, months);
                    break;
                case 'S':
                    rule = selectNumberRule(14, tokenLen);
                    break;
                case 'W':
                    rule = selectNumberRule(4, tokenLen);
                    break;
                case 'X':
                    rule = Iso8601_Rule.getRule(tokenLen);
                    break;
                case 'Y':
                case 'y':
                    if (tokenLen != 2) {
                        if (tokenLen < 4) {
                            tokenLen = 4;
                        }
                        rule = selectNumberRule(1, tokenLen);
                        break;
                    }
                    rule = TwoDigitYearField.INSTANCE;
                    break;
                case 'Z':
                    if (tokenLen != 1) {
                        if (tokenLen != 2) {
                            rule = TimeZoneNumberRule.INSTANCE_COLON;
                            break;
                        }
                        rule = Iso8601_Rule.ISO8601_HOURS_COLON_MINUTES;
                        break;
                    }
                    rule = TimeZoneNumberRule.INSTANCE_NO_COLON;
                    break;
                case 'a':
                    rule = new TextField(9, AmPmStrings);
                    break;
                case 'd':
                    rule = selectNumberRule(5, tokenLen);
                    break;
                case 'h':
                    rule = new TwelveHourField(selectNumberRule(10, tokenLen));
                    break;
                case 'k':
                    rule = new TwentyFourHourField(selectNumberRule(11, tokenLen));
                    break;
                case 'm':
                    rule = selectNumberRule(12, tokenLen);
                    break;
                case 's':
                    rule = selectNumberRule(13, tokenLen);
                    break;
                case 'u':
                    rule = new DayInWeekField(selectNumberRule(7, tokenLen));
                    break;
                case 'w':
                    rule = selectNumberRule(3, tokenLen);
                    break;
                default:
                    throw new IllegalArgumentException("Illegal pattern component: " + token);
            }
            rules.add(rule);
            i++;
        }
        return rules;
    }

    protected String parseToken(String pattern, int[] indexRef) {
        StringBuilder buf = new StringBuilder();
        int i = indexRef[0];
        int length = pattern.length();
        char c = pattern.charAt(i);
        if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) {
            buf.append('\'');
            boolean inLiteral = false;
            while (i < length) {
                c = pattern.charAt(i);
                if (c != '\'') {
                    if (!inLiteral && ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                        i--;
                        break;
                    }
                    buf.append(c);
                } else if (i + 1 >= length || pattern.charAt(i + 1) != '\'') {
                    inLiteral = !inLiteral;
                } else {
                    i++;
                    buf.append(c);
                }
                i++;
            }
        } else {
            buf.append(c);
            while (i + 1 < length && pattern.charAt(i + 1) == c) {
                buf.append(c);
                i++;
            }
        }
        indexRef[0] = i;
        return buf.toString();
    }

    protected NumberRule selectNumberRule(int field, int padding) {
        switch (padding) {
            case 1:
                return new UnpaddedNumberField(field);
            case 2:
                return new TwoDigitNumberField(field);
            default:
                return new PaddedNumberField(field, padding);
        }
    }

    @Deprecated
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj instanceof Date) {
            return format((Date) obj, toAppendTo);
        }
        if (obj instanceof Calendar) {
            return format((Calendar) obj, toAppendTo);
        }
        if (obj instanceof Long) {
            return format(((Long) obj).longValue(), toAppendTo);
        }
        String str;
        StringBuilder append = new StringBuilder().append("Unknown class: ");
        if (obj == null) {
            str = "<null>";
        } else {
            str = obj.getClass().getName();
        }
        throw new IllegalArgumentException(append.append(str).toString());
    }

    String format(Object obj) {
        if (obj instanceof Date) {
            return format((Date) obj);
        }
        if (obj instanceof Calendar) {
            return format((Calendar) obj);
        }
        if (obj instanceof Long) {
            return format(((Long) obj).longValue());
        }
        String str;
        StringBuilder append = new StringBuilder().append("Unknown class: ");
        if (obj == null) {
            str = "<null>";
        } else {
            str = obj.getClass().getName();
        }
        throw new IllegalArgumentException(append.append(str).toString());
    }

    public String format(long millis) {
        Calendar c = newCalendar();
        c.setTimeInMillis(millis);
        return applyRulesToString(c);
    }

    private String applyRulesToString(Calendar c) {
        return ((StringBuilder) applyRules(c, new StringBuilder(this.mMaxLengthEstimate))).toString();
    }

    private Calendar newCalendar() {
        return Calendar.getInstance(this.mTimeZone, this.mLocale);
    }

    public String format(Date date) {
        Calendar c = newCalendar();
        c.setTime(date);
        return applyRulesToString(c);
    }

    public String format(Calendar calendar) {
        return ((StringBuilder) format(calendar, new StringBuilder(this.mMaxLengthEstimate))).toString();
    }

    public StringBuffer format(long millis, StringBuffer buf) {
        Calendar c = newCalendar();
        c.setTimeInMillis(millis);
        return (StringBuffer) applyRules(c, (Appendable) buf);
    }

    public StringBuffer format(Date date, StringBuffer buf) {
        Calendar c = newCalendar();
        c.setTime(date);
        return (StringBuffer) applyRules(c, (Appendable) buf);
    }

    public StringBuffer format(Calendar calendar, StringBuffer buf) {
        return format(calendar.getTime(), buf);
    }

    public <B extends Appendable> B format(long millis, B buf) {
        Calendar c = newCalendar();
        c.setTimeInMillis(millis);
        return applyRules(c, (Appendable) buf);
    }

    public <B extends Appendable> B format(Date date, B buf) {
        Calendar c = newCalendar();
        c.setTime(date);
        return applyRules(c, (Appendable) buf);
    }

    public <B extends Appendable> B format(Calendar calendar, B buf) {
        if (!calendar.getTimeZone().equals(this.mTimeZone)) {
            calendar = (Calendar) calendar.clone();
            calendar.setTimeZone(this.mTimeZone);
        }
        return applyRules(calendar, (Appendable) buf);
    }

    @Deprecated
    protected StringBuffer applyRules(Calendar calendar, StringBuffer buf) {
        return (StringBuffer) applyRules(calendar, (Appendable) buf);
    }

    private <B extends Appendable> B applyRules(Calendar calendar, B buf) {
        try {
            for (Rule rule : this.mRules) {
                rule.appendTo(buf, calendar);
            }
        } catch (IOException e) {
        }
        return buf;
    }

    public String getPattern() {
        return this.mPattern;
    }

    public TimeZone getTimeZone() {
        return this.mTimeZone;
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public int getMaxLengthEstimate() {
        return this.mMaxLengthEstimate;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FastDatePrinter)) {
            return false;
        }
        FastDatePrinter other = (FastDatePrinter) obj;
        if (this.mPattern.equals(other.mPattern) && this.mTimeZone.equals(other.mTimeZone) && this.mLocale.equals(other.mLocale)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.mPattern.hashCode() + ((this.mTimeZone.hashCode() + (this.mLocale.hashCode() * 13)) * 13);
    }

    public String toString() {
        return "FastDatePrinter[" + this.mPattern + MiPushClient.ACCEPT_TIME_SEPARATOR + this.mLocale + MiPushClient.ACCEPT_TIME_SEPARATOR + this.mTimeZone.getID() + "]";
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        init();
    }
}
