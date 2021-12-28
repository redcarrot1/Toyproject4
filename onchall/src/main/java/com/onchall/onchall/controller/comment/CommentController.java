package com.onchall.onchall.controller.comment;

import com.onchall.onchall.SessionData;
import com.onchall.onchall.argumentResolver.Login;
import com.onchall.onchall.entity.Comment;
import com.onchall.onchall.entity.Item;
import com.onchall.onchall.entity.Member;
import com.onchall.onchall.form.CommentForm;
import com.onchall.onchall.service.CommentService;
import com.onchall.onchall.service.ItemService;
import com.onchall.onchall.service.MemberService;
import com.onchall.onchall.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final OrderItemService orderItemService;
    private final ItemService itemService;
    private final MemberService memberService;

    @GetMapping("/comment/add/{itemId}/{orderItemId}")
    public String addCommentForm(@PathVariable Long itemId, @PathVariable Long orderItemId, Model model) {
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("itemId", itemId);
        model.addAttribute("orderItemId", orderItemId);
        return "comment/commentAdd";
    }

    @PostMapping("/comment/add/{itemId}/{orderItemId}")
    @ResponseBody
    public String addComment(@Login SessionData loginMemberId, @PathVariable Long itemId,
                             @PathVariable Long orderItemId, @RequestBody CommentForm commentForm) {
        try {
            Member loginMember = memberService.getMemberByMemberId(loginMemberId.getMemberId());
            Item item = itemService.getItemById(itemId);
            Comment comment = new Comment(commentForm.getContent(), item, loginMember, commentForm.getRating());
            commentService.save(comment);
            orderItemService.setIsCommentTrue(orderItemId);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
}
