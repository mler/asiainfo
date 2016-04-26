package com.bdx.rainbow.mapp.core.util;

/**
 * <p>Filename: com.ailk.butterfly.mapp.core.util.ThrowableUtil.java<p>
 * <p>Date: 2015-07-02 22:54.</p>
 *
 * @author <a href="mailto:stxpons@gmail.com">stxpons@gmail.com</a>
 * @version V1.0.0
 */
public abstract class ThrowableUtils {
    public static String getMessage(final Throwable e) {
        if (null != e.getMessage()) {
            return e.getMessage();
        } else {
            return e.toString();
        }
    }
}
