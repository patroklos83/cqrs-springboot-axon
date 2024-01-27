package com.patroclos.util;

import java.util.UUID;

public class ServiceUtils {

    public static UUID formatUuid(String id) {
        id = id.replace("-", "");
        String formatted = String.format(
                id.substring(0, 8) + "-" +
                        id.substring(8, 12) + "-" +
                        id.substring(12, 16) + "-" +
                        id.substring(16, 20) + "-" +
                        id.substring(20, 32)
        );
        return UUID.fromString(formatted);
    }
}
