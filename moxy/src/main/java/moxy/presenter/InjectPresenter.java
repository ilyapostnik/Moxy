package moxy.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectPresenter {

    String EMPTY = "";

    String tag() default EMPTY;

    PresenterType type() default PresenterType.LOCAL;

    String presenterId() default EMPTY;
}
