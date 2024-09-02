package com.mysite.sbb;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PieDataController {
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/rest/pie-data")
    public List getPieData() {
        List pieData = new ArrayList<>();
        List dataElement = new ArrayList();
        List lableElement = new ArrayList();
        for (int i = 0; i < 3; i++) {
            lableElement.add("Region_" + i);
            dataElement.add((int)(Math.random() * 10 +1));
        }
        pieData.add(lableElement);
        pieData.add(dataElement);
        return pieData;
    }
}
