package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value={ElementType.FIELD})
public @interface CD_OneToMany {
	Class<?> many();
	String connectionName() default "";
}
