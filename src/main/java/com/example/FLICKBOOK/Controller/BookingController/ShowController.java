package com.example.FLICKBOOK.Controller.BookingController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FLICKBOOK.Model.Show;
import com.example.FLICKBOOK.Service.ServiceInter.ShowService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Shows")
public class ShowController {

    @Autowired
    private ShowService showservice;


    //add movies to show
    @PostMapping("/addshows")
    public String AddShows(@RequestBody Show show , 
                               @RequestParam String tname,
                               @RequestParam String mname,
                               @RequestParam Integer tid )throws Exception {
            return showservice.AddShows(show, tname, mname,tid);

    }

  //get shows
    @GetMapping("getshows/{movieid}/shows")
    public List<Map<String,Object>> getTheatresAndShows(@PathVariable Integer movieid, @RequestParam LocalDate date) throws  Exception {
        return showservice.getTheatresAndShows(movieid,date);
    }
    


    

}
