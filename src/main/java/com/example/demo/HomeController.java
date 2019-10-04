package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Controller
public class HomeController {
    @Autowired
    JobRepository jobRepository;

    @RequestMapping("/")
    public String getList(Model model){
        model.addAttribute("jobs", jobRepository.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String addJob(Model model){
        model.addAttribute("job", new Job());
        return "jobform";
    }

    @PostMapping("/search")
    public String getSearch(Model model, @RequestParam(name="search") String search ){
        model.addAttribute("jobs", jobRepository.findByCity(search));
        return "index";
    }

    @RequestMapping("/update/{id}")
    public String updateJob(@PathVariable("id") long id, Model model){
        model.addAttribute("job", jobRepository.findById(id).get());
        return "jobform";
    }

    @RequestMapping("/delete/{id}")
    public String deleteJob(@PathVariable("id") long id){
        jobRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showJob(@PathVariable("id") long id, Model model){
        model.addAttribute("job", jobRepository.findById(id).get());
        return "showjob";
    }

    @PostMapping("/processjob")
    public String processForm(@ModelAttribute Job job){
        String pattern = "yyyy-MM-dd";


            //String formattedDate = date.substring(date.length()-1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            LocalDate realDate = LocalDate.now();
            job.setPostedDate(realDate);
        System.out.println(realDate);



        jobRepository.save(job);
        return "redirect:/";
    }


}
