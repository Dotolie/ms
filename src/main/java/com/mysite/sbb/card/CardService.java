package com.mysite.sbb.card;

import com.mysite.sbb.question.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardService {
    private final CardRepository cardRepository;

    public Integer create(Card card) {
        this.cardRepository.save(card);

        return card.getId();
    }

    public Optional<Card> findOne(Integer cardId) {
        return cardRepository.findById(cardId);
    }
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    public Page<Card> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.cardRepository.findAllByKeyword(kw, pageable);
    }

    public Long getCountByPartName(String partName) {
        return this.cardRepository.countByPartName(partName);
    }
    public List<String> getPartNameDistinctList() {

        return this.cardRepository.findDistinct();
    }
}
