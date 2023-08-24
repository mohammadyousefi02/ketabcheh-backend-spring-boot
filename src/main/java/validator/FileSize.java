package validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Constraint(validatedBy = FileSizeValidator.class)
public @interface FileSize {
    long maxFileSize() default 1000000;
    String message() default "File size must be less than or equal to {maxFileSize}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
