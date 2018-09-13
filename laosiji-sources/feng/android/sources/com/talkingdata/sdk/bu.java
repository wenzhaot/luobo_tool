package com.talkingdata.sdk;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: td */
final class bu {
    private static final Map a = new ConcurrentHashMap();

    private static void a(Class cls) {
        try {
            Map hashMap = new HashMap();
            for (Method method : cls.getDeclaredMethods()) {
                if (method.getName().startsWith("onTDEBEvent") && method.getParameterTypes().length == 1) {
                    Class[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 1) {
                        aq.eForInternal("Method " + method + " must have one and only one argument.");
                    }
                    Class cls2 = parameterTypes[0];
                    if (cls2.isInterface()) {
                        aq.eForInternal("Method " + method + " must have a argument whose type is a class which can be instantialized.");
                    }
                    if ((method.getModifiers() & 1) == 0) {
                        aq.eForInternal("Method " + method + " must be 'public'.");
                    }
                    Set set = (Set) hashMap.get(cls2);
                    if (set == null) {
                        set = new HashSet();
                        hashMap.put(cls2, set);
                    }
                    set.add(method);
                }
            }
            a.put(cls, hashMap);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    static Map a(Object obj) {
        Map hashMap = new HashMap();
        try {
            Class cls = obj.getClass();
            if (!a.containsKey(cls)) {
                a(cls);
            }
            Map map = (Map) a.get(cls);
            if (!(map == null || map.isEmpty())) {
                for (Entry entry : map.entrySet()) {
                    Set hashSet = new HashSet();
                    for (Method bvVar : (Set) entry.getValue()) {
                        hashSet.add(new bv(obj, bvVar));
                    }
                    hashMap.put(entry.getKey(), hashSet);
                }
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        return hashMap;
    }

    private bu() {
    }
}
