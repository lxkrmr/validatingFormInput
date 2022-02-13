package com.example.validating;


import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.Set;

import javax.validation.ConstraintViolation;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;


@SuppressWarnings("UnusedReturnValue")
public class ConstraintViolationSetAssert<T>
    extends AbstractAssert<ConstraintViolationSetAssert<T>, Set<? extends ConstraintViolation<T>>> {

    public ConstraintViolationSetAssert(Set<? extends ConstraintViolation<T>> actual) {
        super(actual, ConstraintViolationSetAssert.class);
    }

    public static <T> ConstraintViolationSetAssert<T> assertThat(Set<? extends ConstraintViolation<T>> actual) {
        return new ConstraintViolationSetAssert<>(actual);
    }

    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public ConstraintViolationSetAssert<T> hasViolationOnPath(String path) {
        isNotNull();

        // check condition
        if (!containsViolationWithPath(actual, path)) {
            failWithMessage("There was no violation with path <%s>. Violation paths: <%s>",
                            path,
                            actual.stream().map(violation -> violation.getPropertyPath().toString())
                                  .collect(toList()));
        }

        return this;
    }

    public ConstraintViolationSetAssert<T> hasNotNullViolationOnPath(String path) {
        return hasViolationOnPathWithMessageTemplate(path, "javax.validation.constraints.NotNull.message");
    }

    public ConstraintViolationSetAssert<T> hasNotEmptyViolationOnPath(String path) {
        return hasViolationOnPathWithMessageTemplate(path, "javax.validation.constraints.NotEmpty.message");
    }

    public ConstraintViolationSetAssert<T> isValid() {
        Assertions.assertThat(actual).isEmpty();
        return this;
    }

    public ConstraintViolationSetAssert<T> hasViolationOnPathWithMessageTemplate(String path, String messageTemplate) {
        isNotNull();

        // check condition
        if (!containsViolationWithPathAndMessageTemplate(actual, path, messageTemplate)) {
            failWithMessage("There was no violation with path <%s> and message template <{%s}>. Violation paths: <%s>",
                            path,
                            messageTemplate,
                            actual.stream().map(violation -> format("%s -> %s",
                                                                    violation.getPropertyPath().toString(),
                                                                    violation.getMessageTemplate()))
                                  .collect(toList()));
        }

        return this;
    }

    private boolean containsViolationWithPath(Set<? extends ConstraintViolation<T>> violations, String path) {
        return violations.stream()
                         .anyMatch(violation -> matchesPath(violation, path));
    }

    private boolean containsViolationWithPathAndMessageTemplate(Set<? extends ConstraintViolation<T>> violations,
                                                                String path,
                                                                String messageTemplate) {
        return violations.stream()
                         .anyMatch(violation -> matchesPath(violation, path)
                             && matchesMessage(violation, messageTemplate));
    }

    private boolean matchesPath(ConstraintViolation<?> constraintViolation, String path) {
        return constraintViolation.getPropertyPath().toString().matches(path);
    }

    private boolean matchesMessage(ConstraintViolation<T> violation, String messageTemplate) {
        return violation.getMessageTemplate().equals(format("{%s}", messageTemplate));
    }
}

