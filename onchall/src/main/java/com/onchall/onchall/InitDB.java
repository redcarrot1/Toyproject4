package com.onchall.onchall;

import com.onchall.onchall.entity.*;
import com.onchall.onchall.repository.*;
import com.onchall.onchall.service.CartService;
import com.onchall.onchall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDB {
    private final MemberService memberService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final FileDataRepository fileDataRepository;
    private final CartService cartService;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final PointRepository pointRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initDB() {
        log.info("initialize database");
        initMember();
        initCategory();
        initFileData();
        //initItem();
        //initItemFor20();
        initItemFor70();
        initOrder();
        initPoint();
    }

    public void initMember() {
        memberService.signup("testMember1", "asdf@asdf.com", "asdfasdf@", MemberState.ADMIN);
        memberService.signup("testMember2", "asdf2@asdf.com", "asdfasdf@", MemberState.ADMIN);
        memberService.signup("testMember2", "asd3@asdf.com", "asdfasdf@", MemberState.ADMIN);
    }

    public void initCategory() {
        Category category1 = new Category();
        category1.setName("고등수학상");
        categoryRepository.save(category1);
        Category category2 = new Category();
        category2.setName("고등수학하");
        categoryRepository.save(category2);
    }

    public void initFileData() {
        FileData fileData1 = new FileData("고등수학상_통합파일", "asdfalskdjfiaes");
        fileDataRepository.save(fileData1);
        FileData fileData2 = new FileData("고등수학하_통합파일", "qweryuoizxcvasdf");
        fileDataRepository.save(fileData2);
    }

    public void initItem() {
        Item item1 = new Item("[온챌개념서]고등수학상_통합", 9000, 10000,
                "[온챌개념서]고등수학상_통합_이미지", "개념과 문제가 함께있는 좋은 책입니다!");
        Category category1 = categoryRepository.findByName("고등수학상").get();
        FileData fileData1 = fileDataRepository.findByUploadName("고등수학상_통합파일").get();
        item1.setCategory(category1);
        item1.setFileData(fileData1);
        itemRepository.save(item1);

        Item item2 = new Item("[온챌개념서]고등수학하_통합", 7000, 11000,
                "[온챌개념서]고등수학하_통합_이미지", "초보자들이 보기 좋은 책");
        Category category2 = categoryRepository.findByName("고등수학하").get();
        FileData fileData2 = fileDataRepository.findByUploadName("고등수학하_통합파일").get();
        item2.setCategory(category2);
        item2.setFileData(fileData2);
        itemRepository.save(item2);
    }

    public void initItemFor20() {
        for (int i = 1; i < 11; i++) {
            Item item = new Item("[온챌개념서]고등수학상" + i, 9000 + i * 50, 10000 + i * 100,
                    "[온챌개념서]고등수학상_통합_이미지" + i, "개념과 문제가 함께있는 책" + i);
            Category category = categoryRepository.findByName("고등수학상").get();
            FileData fileData = fileDataRepository.findByUploadName("고등수학상_통합파일").get();
            item.setCategory(category);
            item.setFileData(fileData);
            itemRepository.save(item);
        }
        for (int i = 11; i < 21; i++) {
            Item item = new Item("[온챌개념서]고등수학하" + i, 9000 + i * 50, 10000 + i * 100,
                    "[온챌개념서]고등수학하_통합_이미지" + i, "초보자가 보기 좋은 책" + i);
            Category category = categoryRepository.findByName("고등수학하").get();
            FileData fileData = fileDataRepository.findByUploadName("고등수학하_통합파일").get();
            item.setCategory(category);
            item.setFileData(fileData);
            itemRepository.save(item);
        }
    }

    public void initItemFor70() {
        for (int i = 1; i < 36; i++) {
            Item item = new Item("[온챌개념서]고등수학상" + i, 9000 + i * 50, 10000 + i * 100,
                    "testImage.png", "개념과 문제가 함께있는 책" + i);
            Category category = categoryRepository.findByName("고등수학상").get();
            FileData fileData = fileDataRepository.findByUploadName("고등수학상_통합파일").get();
            item.setCategory(category);
            item.setFileData(fileData);
            itemRepository.save(item);
        }
        Member testMember1 = memberService.getMemberByNameAndEmail("testMember1", "asdf@asdf.com");
        //5개는 장바구니에 넣기
        for (int i = 36; i < 41; i++) {
            Item item = new Item("[온챌개념서]고등수학하" + i, 9000 + i * 50, 10000 + i * 100,
                    "testImage.png", "초보자가 보기 좋은 책" + i);
            Category category = categoryRepository.findByName("고등수학하").get();
            FileData fileData = fileDataRepository.findByUploadName("고등수학하_통합파일").get();
            item.setCategory(category);
            item.setFileData(fileData);
            Item saveItem = itemRepository.save(item);
            cartService.addItem(testMember1.getId(), saveItem.getId());
        }
        for (int i = 41; i < 71; i++) {
            Item item = new Item("[온챌개념서]고등수학하" + i, 9000 + i * 50, 10000 + i * 100,
                    "testImage.png", "초보자가 보기 좋은 책" + i);
            Category category = categoryRepository.findByName("고등수학하").get();
            FileData fileData = fileDataRepository.findByUploadName("고등수학하_통합파일").get();
            item.setCategory(category);
            item.setFileData(fileData);
            itemRepository.save(item);
        }
    }

    @Transactional
    public void initOrder() {
        List<Item> items = itemRepository.findAll();
        List<OrderItem> orderItems = new ArrayList<>();
        Member testMember1 = memberService.getMemberByNameAndEmail("testMember1", "asdf@asdf.com");

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderItems(orderItems);
        order.setOrderItemCount(5);
        order.setRepreItemName(items.get(0).getName());
        order.setMember(testMember1);
        Integer totalPrice = 0;
        order.setTotalPrice(totalPrice);
        order.setUsePoint(3000);
        order.setPayMethod(PayMethod.Card);

        Order saveOrder = orderRepository.save(order);

        for (int i = 0; i < 5; i++) {
            Item e = items.get(i);

            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(e.getId());
            orderItem.setItemName(e.getName());
            orderItem.setComment(false);
            orderItem.setPrice(e.getPrice());
            orderItem.setOrder(saveOrder);
            totalPrice += e.getPrice();

            OrderItem saveOrderItem = orderItemRepository.save(orderItem);
            saveOrder.getOrderItems().add(saveOrderItem);
        }
        ;
        saveOrder.setTotalPrice(totalPrice);
    }

    public void initPoint() {
        Member member = memberService.getMemberByNameAndEmail("testMember1", "asdf@asdf.com");
        pointRepository.save(new Point("신규가입", 1000, member));
        pointRepository.save(new Point("관리자권한", 3000, member));
    }
}