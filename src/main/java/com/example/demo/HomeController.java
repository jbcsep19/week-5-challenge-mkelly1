package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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

    @GetMapping("/search")
    public String getSearch(Model model){
        model.addAttribute("jobs", jobRepository.findAll());
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
    public String processForm(@ModelAttribute Job job, @RequestParam(name = "date")
            String date){
        String pattern = "yyyy-MM-dd";
        System.out.println(date);
        try {
//            String formattedDate = date.substring(date.length()-1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date realDate = simpleDateFormat.parse(date);
            job.setPostedDate(realDate);
        }
        catch (java.text.ParseException e){
            e.printStackTrace();
        }

        jobRepository.save(job);
        return "redirect:/";
    }


}
