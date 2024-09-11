package com.mysite.sbb.scenario;

import com.mysite.sbb.card.Card;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ScenarioService {

    @Value("${file.dir}")
    private String fileDir;

    private final ScenarioRepository scenarioRepository;

    public void saveFile(MultipartFile files) throws IOException {
        if (files.isEmpty()) {
            return;
        }

        // 원래 파일 이름 추출
        String origName = files.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        assert origName != null;
        String extension = origName.substring(origName.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = fileDir + savedName;

        // 파일 엔티티 생성
        Scenario scenario = Scenario.builder()
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath(savedPath)
                .createDate(LocalDateTime.now())
                .build();

        // 실제로 로컬에 uuid를 파일명으로 저장
        files.transferTo(new File(savedPath));

        // 데이터베이스에 파일 정보 저장
        Scenario temp = this.scenarioRepository.save(scenario);

    }

    public Page<Scenario> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.scenarioRepository.findAllByKeyword(kw, pageable);
    }
}
