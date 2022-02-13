package com.example.validating;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record PersonForm(@NotNull @Size(min = 2, max = 30) String name,
                         @NotNull @Min(18) Integer age) {

}
