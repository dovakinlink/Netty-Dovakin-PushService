package org.dovakin.push.core.annotations;

import java.lang.annotation.*;

/**
 * Created by liuhuanchao on 2017/8/1.
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NGLSController {

    int type();
}
