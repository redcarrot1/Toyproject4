package com.onchall.onchall.controller.comment;

import com.onchall.onchall.argumentResolver.Login;
import com.onchall.onchall.entity.Comment;
import com.onchall.onchall.entity.Item;
import com.onchall.onchall.entity.Member;
import com.onchall.onchall.form.CommentForm;
import com.onchall.onchall.service.CommentService;
import com.onchall.onchall.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final ItemService itemService;

    @PostMapping("/comment/add/{itemId}")
    //@ResponseBody
    public String addComment(@Login Member loginMember, @PathVariable Long itemId, @ModelAttribute CommentForm commentForm) {
        Item item = itemService.findById(itemId);
        Comment comment = new Comment(commentForm.getContent(), item, loginMember, commentForm.getRating());
        commentService.save(comment);
        //todo 성공실패 리턴
        return "redirect:/memberDetail/order";
    }

    @GetMapping("/comment/add/{itemId}")
    public String addCommentForm(@PathVariable Long itemId, Model model) {
        //todo 아이템이 아직도 존재하는가 검사
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("itemId", itemId);
        return "comment/commentAdd";
    }
}
