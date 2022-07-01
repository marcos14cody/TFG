package annotations;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Target(value={ElementType.TYPE})
public @interface CD_Class {
	String displayName() default "";
}
