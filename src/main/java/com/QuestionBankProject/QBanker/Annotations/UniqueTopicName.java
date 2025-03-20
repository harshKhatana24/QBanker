package com.QuestionBankProject.QBanker.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueTopicNameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTopicName {

    String message() default "Topic name already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

