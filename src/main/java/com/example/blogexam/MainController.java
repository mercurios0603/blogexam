package com.example.blogexam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {


  private final PostRepository postRepository;

  @GetMapping("/main")
  public String list(Model model) {
    List<Post> postList = this.postRepository.findAll();
    model.addAttribute("postList", postList);
    model.addAttribute("targetPost", postList.get(0));
    return "main";
  }

  @PostMapping("/write")
  public String write() {
    Post p = new Post();
    p.setTitle("new title");
    p.setContent("");
    p.setCreateDate(LocalDateTime.now());
    this.postRepository.save(p);
    return "redirect:/main";
  }

  @GetMapping("/detail/{id}")
  public String detail(Model model, @PathVariable("id") Integer id) {
    Post post = this.postRepository.findById(id).get();
    model.addAttribute("postList", this.postRepository.findAll());
    model.addAttribute("targetPost", post);
    return "main";
  }

  @PostMapping("/update")
  public String update(Integer id, String title, String content) {
    Post p = this.postRepository.findById(id).get();
    p.setTitle(title);
    p.setContent(content);
    p.setCreateDate(LocalDateTime.now());
    this.postRepository.save(p);
    return "redirect:/detail/" + id;
  }
}
