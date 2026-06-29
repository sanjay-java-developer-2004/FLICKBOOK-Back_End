package com.example.FLICKBOOK.Controller.BookingController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FLICKBOOK.Model.Theatre;
import com.example.FLICKBOOK.Service.ServiceInter.TheatreService;

@RestController 
@CrossOrigin(origins="*")
@RequestMapping("/theatre")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    //Add Theatre  
    @PostMapping("/addTheatre")
    public Object AddTheatre(@RequestBody Theatre theatre ,@RequestParam Integer userId) throws Exception{
      return theatreService.AddTheatre(theatre,userId);

    }


    //get Theatre By Name
    @GetMapping("/getTheatreByName")
    public Optional<Theatre> GetTheatre(@RequestParam String tname) throws Exception{
      return theatreService.GetTheatre(tname);

    }

    //delete Theatre
    @DeleteMapping("/deleteTheatre")
    
    public String DeleteTheatre (@RequestParam String tname) throws Exception {
      return theatreService.DeleteTheatre(tname);
    }

}
