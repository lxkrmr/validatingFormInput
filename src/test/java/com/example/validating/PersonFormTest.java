package com.example.validating;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static com.example.validating.ConstraintViolationSetAssert.assertThat;


class PersonFormTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void givenValidNameAndAge_whenValidating_thenIsValid() {
        // given
        var personForm = new PersonForm("<name>", 18);

        // when
        var result = validator.validate(personForm);

        // then
        assertThat(result).isValid();
    }

    @Test
    void givenTooShortName_whenValidating_thenIsInvalid() {
        // given
        var personForm = new PersonForm("a", 18);

        // when
        var result = validator.validate(personForm);

        // then
        assertThat(result).hasViolationOnPath("name");
    }

    @Test
    void givenTooLongName_whenValidating_thenIsInvalid() {
        // given
        var tooLongName = StringUtils.repeat("a", 31);
        var personForm = new PersonForm(tooLongName, 18);

        // when
        var result = validator.validate(personForm);

        // then
        assertThat(result).hasViolationOnPath("name");
    }

    @Test
    void givenNoName_whenValidating_thenIsInvalid() {
        // given
        var personForm = new PersonForm(null, 18);

        // when
        var result = validator.validate(personForm);

        // then
        assertThat(result).hasNotNullViolationOnPath("name");
    }

    @Test
    void givenAgeTooYoung_whenValidating_thenIsInvalid() {
        // given
        var personForm = new PersonForm("<name>", 17);

        // when
        var result = validator.validate(personForm);

        // then
        assertThat(result).hasViolationOnPath("age");
    }

    @Test
    void givenNoAge_whenValidating_thenIsInvalid() {
        // given
        var personForm = new PersonForm("<name>", null);

        // when
        var result = validator.validate(personForm);

        // then
        assertThat(result).hasNotNullViolationOnPath("age");
    }
}
