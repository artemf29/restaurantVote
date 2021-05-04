package com.artemf29.core.util;

import com.artemf29.core.HasId;
import com.artemf29.core.util.exception.IllegalRequestDataException;
import com.artemf29.core.util.exception.NotFoundException;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
// https://github.com/JetBrains/intellij-community/blob/master/plugins/InspectionGadgets/src/inspectionDescriptions/OptionalUsedAsFieldOrParameterType.html
public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(Optional<T> optional, int id) {
        return checkNotFoundWithId(optional, "Entity with id=" + id + " not found");
    }

    public static <T> T checkNotFoundWithId(Optional<T> optional, String msg) {
        return optional.orElseThrow(() -> new NotFoundException(msg));
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static void checkSingleModification(int count, String msg) {
        if (count != 1) {
            throw new NotFoundException(msg);
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}
