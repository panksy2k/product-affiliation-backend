package com.product.affiliation.backend.util;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Util {
    /**
    * Uses a {@link ThreadLocalRandom} to generate a UUID. Faster, but not secure
    */
    public static UUID threadLocalRandomUUID() {
        byte[] data = new byte[16];
        ThreadLocalRandom.current().nextBytes(data);
        data[6] &= 0x0f; /* clear version */
        data[6] |= 0x40; /* set to version 4 */
        data[8] &= 0x3f; /* clear variant */
        data[8] |= 0x80; /* set to IETF variant */
        long msb = 0;
        long lsb = 0;
        for (int i = 0; i < 8; i++)
            msb = (msb << 8) | (data[i] & 0xff);
        for (int i = 8; i < 16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);
        return new UUID(msb, lsb);
    }

    public static <T> boolean isEmpty(Collection<T> tCollection) {
      return (tCollection == null || tCollection.isEmpty())? true : false;
    }
}
