package com.shaoqunliu.jpa.util;

import com.shaoqunliu.reflection.POJOReflection;

public class ObjectCopyingUtil {

    public static <T> T copyNullProperties(T source, T target) {
        POJOReflection sourceReflection = new POJOReflection(source);
        POJOReflection targetReflection = new POJOReflection(target);
        targetReflection.forEachField(field -> {
            try {
                if (targetReflection.getValue(field.getName()) == null) {
                    targetReflection.setValue(field.getName(), sourceReflection.getValue(field.getName()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return target;
    }

    public static <T> T coverDifferentProperties(T source, T target) {
        POJOReflection sourceReflection = new POJOReflection(source);
        POJOReflection targetReflection = new POJOReflection(target);
        targetReflection.forEachField(field -> {
            try {
                Object s = sourceReflection.getValue(field.getName());
                Object t = targetReflection.getValue(field.getName());
                if (!s.equals(t)) {
                    targetReflection.setValue(field.getName(), s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return target;
    }
}
