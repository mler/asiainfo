package com.bdx.rainbow.urs.common;

/**
 * Created by core on 16/1/22.
 */
import org.springframework.beans.BeanUtils;
public class BeanCopyUtil {
    public static final void copyProperties(Object source, Object dest) {
        BeanUtils.copyProperties(source, dest);
    }

    public static final void copyProperties(Object[] sources, Object dest) {
        for (Object object : sources) {
            BeanUtils.copyProperties(object, dest);
        }
    }

    public static final void copyProperties(Object source, Object... dest) {
        for (Object object : dest) {
            BeanUtils.copyProperties(source, object);
        }
    }

}
