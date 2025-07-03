package sars.pca.app.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 *
 * @author S2026080
 */
public class MergeUtility {
    public static Object mergeObjects(Object source, Object target) throws Exception {
        Field[] allFields = source.getClass().getDeclaredFields();
        for (Field field : allFields) {
            if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            if (!field.isAccessible() && Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
            }
            if (field.get(source) != null) {
                field.set(target, field.get(source));
            }
        }
        return target;
    }
}
