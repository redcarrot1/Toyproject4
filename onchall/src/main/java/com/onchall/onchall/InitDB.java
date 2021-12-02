package com.onchall.onchall;

import com.onchall.onchall.entity.Category;
import com.onchall.onchall.entity.FileData;
import com.onchall.onchall.repository.CategoryRepository;
import com.onchall.onchall.repository.FileDataRepository;
import com.onchall.onchall.repository.ItemRepository;
import com.onchall.onchall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDB {
    private final MemberService memberService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final FileDataRepository fileDataRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initDB() {
        log.info("initialize database");
        initMember();
        initCategory();
        initFileData();
    }

    public void initMember(){
        memberService.signup("testMember1", "asdf@asdf.com", "asdfasdf@");
        memberService.changeMemberState("asdf@asdf.com");
        memberService.signup("testMember2", "asdf2@asdf.com", "asdfasdf@");
        memberService.changeMemberState("asdf2@asdf.com");
        memberService.signup("testMember2", "asd3@asdf.com", "asdfasdf@");
        memberService.changeMemberState("asdf3@asdf.com");
    }

    public void initCategory() {
        Category category1 = new Category();
        category1.setName("고등수학상");
        categoryRepository.save(category1);
        Category category2 = new Category();
        category2.setName("고등수학하");
        categoryRepository.save(category2);
    }

    public void initFileData(){
        FileData fileData1 = new FileData("고등수학상_통합파일", "asdfalskdjfiaes");
        fileDataRepository.save(fileData1);
        FileData fileData2 = new FileData("고등수학하_통합파일", "qweryuoizxcvasdf");
        fileDataRepository.save(fileData2);
    }


}