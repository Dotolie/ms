package com.mysite.sbb.card;



import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


import static org.assertj.core.api.Assertions.*;


@SpringBootTest
//@Transactional
class CardServiceTest {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @Test
    void create() {


        for(int i = 0;i < 100;i++ ) {
            Card card = new Card();

            card.setPartName("RIM-DC");
            card.setPartCode("RM200A");
            String sn = "107R" + String.format("%04d", i);
            card.setSerialNumber(sn);
            card.setCompCabinet("RK01");

            card.setCompLocation(5);
            card.setCompRack(i);
            card.setCompSlot(8);
            card.setCreateDate(LocalDateTime.now());

            SiteUser siteUser = this.userService.getUser("품질보증");
            card.setAuthor(siteUser);
            
            Integer saveId = this.cardService.create(card);
            
            
            System.out.println("svaeId="+saveId);
        }

//        Card findCard = cardService.findOne(saveId).get();
//        assertThat(card.getPartName()).isEqualTo(findCard.getPartName());
    }

    @Test
    void findOne() {
        Card card = new Card();

        card.setPartName("RIM-AC");
        card.setPartCode("RM0001A");
        card.setSerialNumber("100R0001");
        card.setCompCabinet("RK01");

        card.setCompLocation(1);
        card.setCompRack(2);
        card.setCompSlot(3);
        card.setCreateDate(LocalDateTime.now());

        SiteUser siteUser = userService.getUser("ywkim");
        card.setAuthor(siteUser);

        Integer id1 = cardService.create(card);

        Card findCard = cardService.findOne(id1).get();
        assertThat(findCard.getPartName()).isEqualTo(card.getPartName());
    }

    @Test
    void findAll() {
        Card card = new Card();


        card.setPartName("RIM-AC");
        card.setPartCode("RM0001A");
        card.setSerialNumber("100R0001");
        card.setCompCabinet("RK01");

        card.setCompLocation(1);
        card.setCompRack(2);
        card.setCompSlot(3);
        card.setCreateDate(LocalDateTime.now());

        SiteUser siteUser = userService.getUser("ywkim");
        card.setAuthor(siteUser);


        Integer id1 = cardService.create(card);



        Card card2 = new Card();

        card2.setPartName("RIM-DC");
        card2.setPartCode("RM0002D");
        card2.setSerialNumber("200R0002");
        card2.setCompCabinet("RK02");

        card2.setCompLocation(2);
        card2.setCompRack(2);
        card2.setCompSlot(2);
        card.setAuthor(siteUser);
        card.setCreateDate(LocalDateTime.now());

        Integer id2 = cardService.create(card2);

        List<Card> result = cardService.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}