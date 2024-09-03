package com.mysite.sbb;

import com.mysite.sbb.card.Card;
import com.mysite.sbb.card.CardChart;
import com.mysite.sbb.card.CardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PieDataController {

    private final CardService cardService;
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/rest/partNameChart")
    public CardChart getPieData() {
        List<String> partNames = this.cardService.getPartNameDistinctList();
        List<Long> countValues = new ArrayList<>();

        for(String partName : partNames ) {
            countValues.add(this.cardService.getCountByPartName(partName));
        }

        log.info("name=" + partNames);
        log.info("count=" + countValues);

        CardChart cardChart = new CardChart();
        cardChart.setName("카드 분포");
        cardChart.setLabel(partNames);
        cardChart.setData(countValues);

        return cardChart;
    }
}
