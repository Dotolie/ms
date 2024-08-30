package com.mysite.sbb.card;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CardForm {
    @NotEmpty(message = "제품명은 필수항목입니다.")
    private String partName;

    @NotEmpty(message = "제품코드는 필수항목입니다.")
    private String partCode;

    @NotEmpty(message = "일련번호는 필수항목입니다.")
    private String serialNumber;

    private String compCabinet;
    private Integer compLocation;
    private Integer compRack;
    private Integer compSlot;
}

