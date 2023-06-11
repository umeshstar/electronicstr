package com.bikkadit.electronicstroe.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    //error message
    String message() default "Invalid Image name!!";

    //represent group
    Class<?>[] groups() default { };

    //additional information about annotation
    Class<? extends Payload>[] payload() default { };
}
