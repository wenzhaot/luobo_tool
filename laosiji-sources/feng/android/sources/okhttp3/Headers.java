package okhttp3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.annotation.Nullable;
import okhttp3.internal.http.HttpDate;

public final class Headers {
    private final String[] namesAndValues;

    Headers(Builder builder) {
        this.namesAndValues = (String[]) builder.namesAndValues.toArray(new String[builder.namesAndValues.size()]);
    }

    private Headers(String[] namesAndValues) {
        this.namesAndValues = namesAndValues;
    }

    @Nullable
    public String get(String name) {
        return get(this.namesAndValues, name);
    }

    @Nullable
    public Date getDate(String name) {
        String value = get(name);
        return value != null ? HttpDate.parse(value) : null;
    }

    public int size() {
        return this.namesAndValues.length / 2;
    }

    public String name(int index) {
        return this.namesAndValues[index * 2];
    }

    public String value(int index) {
        return this.namesAndValues[(index * 2) + 1];
    }

    public Set<String> names() {
        TreeSet<String> result = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        int size = size();
        for (int i = 0; i < size; i++) {
            result.add(name(i));
        }
        return Collections.unmodifiableSet(result);
    }

    public List<String> values(String name) {
        List<String> result = null;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (name.equalsIgnoreCase(name(i))) {
                if (result == null) {
                    result = new ArrayList(2);
                }
                result.add(value(i));
            }
        }
        if (result != null) {
            return Collections.unmodifiableList(result);
        }
        return Collections.emptyList();
    }

    public long byteCount() {
        long result = (long) (this.namesAndValues.length * 2);
        for (String length : this.namesAndValues) {
            result += (long) length.length();
        }
        return result;
    }

    public Builder newBuilder() {
        Builder result = new Builder();
        Collections.addAll(result.namesAndValues, this.namesAndValues);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof Headers) && Arrays.equals(((Headers) other).namesAndValues, this.namesAndValues);
    }

    public int hashCode() {
        return Arrays.hashCode(this.namesAndValues);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        int size = size();
        for (int i = 0; i < size; i++) {
            result.append(name(i)).append(": ").append(value(i)).append("\n");
        }
        return result.toString();
    }

    public Map<String, List<String>> toMultimap() {
        Map<String, List<String>> result = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        int size = size();
        for (int i = 0; i < size; i++) {
            String name = name(i).toLowerCase(Locale.US);
            List<String> values = (List) result.get(name);
            if (values == null) {
                values = new ArrayList(2);
                result.put(name, values);
            }
            values.add(value(i));
        }
        return result;
    }

    private static String get(String[] namesAndValues, String name) {
        for (int i = namesAndValues.length - 2; i >= 0; i -= 2) {
            if (name.equalsIgnoreCase(namesAndValues[i])) {
                return namesAndValues[i + 1];
            }
        }
        return null;
    }

    public static Headers of(String... namesAndValues) {
        if (namesAndValues == null) {
            throw new NullPointerException("namesAndValues == null");
        } else if (namesAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("Expected alternating header names and values");
        } else {
            int i;
            namesAndValues = (String[]) namesAndValues.clone();
            for (i = 0; i < namesAndValues.length; i++) {
                if (namesAndValues[i] == null) {
                    throw new IllegalArgumentException("Headers cannot be null");
                }
                namesAndValues[i] = namesAndValues[i].trim();
            }
            i = 0;
            while (i < namesAndValues.length) {
                String name = namesAndValues[i];
                String value = namesAndValues[i + 1];
                if (name.length() != 0 && name.indexOf(0) == -1 && value.indexOf(0) == -1) {
                    i += 2;
                } else {
                    throw new IllegalArgumentException("Unexpected header: " + name + ": " + value);
                }
            }
            return new Headers(namesAndValues);
        }
    }

    public static Headers of(Map<String, String> headers) {
        if (headers == null) {
            throw new NullPointerException("headers == null");
        }
        String[] namesAndValues = new String[(headers.size() * 2)];
        int i = 0;
        for (Entry<String, String> header : headers.entrySet()) {
            if (header.getKey() == null || header.getValue() == null) {
                throw new IllegalArgumentException("Headers cannot be null");
            }
            String name = ((String) header.getKey()).trim();
            String value = ((String) header.getValue()).trim();
            if (name.length() != 0 && name.indexOf(0) == -1 && value.indexOf(0) == -1) {
                namesAndValues[i] = name;
                namesAndValues[i + 1] = value;
                i += 2;
            } else {
                throw new IllegalArgumentException("Unexpected header: " + name + ": " + value);
            }
        }
        return new Headers(namesAndValues);
    }
}
