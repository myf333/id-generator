package com.myf.id.service.util;

import java.util.Arrays;

/**
 * Created by maoyf0503 on 2019-11-11.
 *
 * @author maoyf0503
 */
public class CommonUtils {
    private static String[] SWITCH_ON_EXP = new String[]{"ON", "TRUE", "on", "true"};

    private static String[] SWITCH_OFF_EXP = new String[]{"OFF", "FALSE", "off", "false"};

    public static boolean isOn(String swtch) {
        return Arrays.asList(SWITCH_ON_EXP).contains(swtch);
    }

    public static boolean isPropKeyOn(String key) {
        String prop = System.getProperty(key);
        return Arrays.asList(SWITCH_ON_EXP).contains(prop);
    }
}
