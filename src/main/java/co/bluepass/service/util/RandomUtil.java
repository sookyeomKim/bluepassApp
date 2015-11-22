package co.bluepass.service.util;

import org.apache.commons.lang.RandomStringUtils;

/**
 * The type Random util.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;

    private RandomUtil() {
    }

    /**
     * Generate password string.
     *
     * @return the string
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    /**
     * Generate activation key string.
     *
     * @return the string
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generate reset key string.
     *
     * @return the string
     */
    public static String generateResetKey() {
       return RandomStringUtils.randomNumeric(DEF_COUNT);
   }
}
