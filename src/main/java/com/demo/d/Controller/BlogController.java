package com.demo.d.Controller;


import com.demo.d.Model.Blog;
import com.demo.d.Model.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {


    @Autowired
    BlogRepository blogRepository;


    @GetMapping
    public String index(Model model){
        List<Blog> blogs =  blogRepository.findAll();
        System.out.println(blogs);
        model.addAttribute("blogs",blogs);
        return "index";
    }


    @PostMapping("/blog/new")
    public  String newPostpost(@RequestParam String title,@RequestParam String img,@RequestParam String full_text,@RequestParam String anons,  Model model){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        if(img!=null){
            Blog blog = new Blog(title,img,full_text,timestamp,anons);
        }
        else{
            Blog blog = new Blog(title,"https://www.truesupreme.com/wp-content/uploads/2017/04/default-image.jpg",full_text,timestamp,anons);
        }
        blogRepository.save(blog);
        return "redirect:/";
    }

    @GetMapping("/blog/new")
    public String newPost(){


        return "newpost";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogEditPost(@PathVariable(value = "id") long id, @RequestParam String title,@RequestParam String img,@RequestParam String full_text,@RequestParam String anons){
        Blog blog = blogRepository.findById(id).orElseThrow(RuntimeException::new);
        blog.setTitle(title);
        blog.setFull_text(full_text);
        blog.setAnons(anons);
        blog.setImg(img);
        blogRepository.save(blog);
        return "redirect:/";

    }


    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model){
        Blog blog = blogRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("blog",blog);
        return "blog-edit";

    }

    @GetMapping("/blog/{id}/remove")
    public String blogRemove(@PathVariable(value = "id") long id, Model model){
        Blog blog = blogRepository.findById(id).orElseThrow(RuntimeException::new);
        blogRepository.delete(blog);

        return "redirect:/";

    }


    @GetMapping("/blog/{id}")
    public String blodDetails(@PathVariable(value = "id") Long id, Model model){
        Optional<Blog> blog = blogRepository.findById(id);

        ArrayList<Blog> res = new ArrayList<>();
        blog.ifPresent(res::add);
        model.addAttribute("blog",res);
        System.out.println(blog);
        model.addAttribute("id",id);
        return "blogdetails";
    }
}
