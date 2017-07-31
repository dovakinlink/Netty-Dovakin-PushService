package org.dovakin.push.core.annotations;

import java.lang.annotation.*;

/**
 * Created by liuhuanchao on 2017/7/28.
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestTask {

    String uri();
}
