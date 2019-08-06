package com.springboot.mvc.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
// 使用了@Phone注解的字段和方法,要遵循PhoneValidator.class的约束,即需要通过PhoneValidator的验证.
@Constraint(validatedBy = PhoneValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberConstraint {
    String message() default "{Invalid phone number}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}