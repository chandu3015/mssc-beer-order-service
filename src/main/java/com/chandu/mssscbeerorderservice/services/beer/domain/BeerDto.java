package com.chandu.mssscbeerorderservice.services.beer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto  implements Serializable {

    static final long serialVersionUID = -3505713437697913527L;

    @JsonProperty("beerId")
    // @Null//as we don't want client to provide beer-id
    private UUID id;
    @Null
    private Integer version;

    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ",shape=JsonFormat.Shape.STRING)
    @Null
    private OffsetDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ",shape=JsonFormat.Shape.STRING)
    @Null
    private OffsetDateTime lastModifiedDate;
    @NotBlank
    private String beerName;
    //@NotBlank
    private String beerStyle;
    @Positive
    @NotNull
    private Long upc;

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @Positive
    @NotNull
    private BigDecimal price;
    private Integer quantityOnHand;
}
