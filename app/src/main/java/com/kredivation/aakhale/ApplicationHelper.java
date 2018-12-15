package com.kredivation.aakhale;

import com.google.gson.internal.Primitives;
import com.kredivation.aakhale.framework.AppController;

/**
 * @author AST Inc.
 */
public class ApplicationHelper {

    private static AppController applicationObj;

    public static AppController application() {
        return applicationObj;
    }

    public static <T> T application(Class<T> entity) {
        return Primitives.wrap(entity).cast(application());
    }

    public static void setApplicationObj(AppController applicationObj) {
        ApplicationHelper.applicationObj = applicationObj;
    }

}
