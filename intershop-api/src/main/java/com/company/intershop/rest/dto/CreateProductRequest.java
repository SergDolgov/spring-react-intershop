package com.company.intershop.rest.dto;

import com.company.intershop.model.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
public class CreateProductRequest {

    @Schema(example = "1")
    private Long brandId;

    @Schema(example = "Twister")
    @NotBlank
    private String title;

    @Schema(example = "https://m.media-amazon.com/images/M/MV5BODExYTM0MzEtZGY2Yy00N2ExLTkwZjItNGYzYTRmMWZlOGEzXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg")
    private String poster;

    @Schema(example = "999.99")
    private float price;
}
