package com.example.FLICKBOOK.Service.ServiceImplements;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Model.Theatre;
import com.example.FLICKBOOK.Model.User;
import com.example.FLICKBOOK.Repository.TheatreRepository;
import com.example.FLICKBOOK.Repository.UserRepository;
import com.example.FLICKBOOK.Service.ServiceInter.TheatreService;

@Service
public class TheatreServiceImplement implements TheatreService {

   @Autowired
   private TheatreRepository theatreRepository;

   @Autowired
   private UserRepository userrepository;

   // register or add theatre

   @Override
   public Object AddTheatre(Theatre theatre, Integer userId) throws Exception {

      if (theatre.getTheatername() != null &&
            theatre.getTheaterlocation() != null &&
            theatre.getTotalseats() != null) {

         Optional<User> tempuser = userrepository.findById(userId);

         if (tempuser.isPresent()) {
            User user = tempuser.get();
            Theatre register = new Theatre();

            register.setTheatername(theatre.getTheatername());
            register.setTheaterlocation(theatre.getTheaterlocation());
            register.setTotalseats(theatre.getTotalseats());
            register.setUser(user);

            Theatre saved = theatreRepository.save(register);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "Theatre Added Successfully");
            response.put("theatreid", saved.getTheaterid());
            response.put("theatrename", saved.getTheatername());

            return response;
         } else {
            throw new Exception("User Not Founded Pls SignUp First");
         }

      } else {
         throw new Exception("The Details Contain null Pls Enter Valid Details");
      }
   }

   // delete

   @Override
   public String DeleteTheatre(String tname) throws Exception {

      Optional<Theatre> check = theatreRepository.findByTheaternameIgnoreCase(tname.trim());

      if (check.get().getTheaterid() != null) {
         theatreRepository.deleteById(check.get().getTheaterid());
         return "Theatre Deleted Successfully";
      } else {
         throw new Exception("Theatre Not Found");
      }

   }

   // find by name
   @Override
   public Optional<Theatre> GetTheatre(String tname) throws Exception {
      Optional<Theatre> temp = theatreRepository.findByTheaternameIgnoreCase(tname.trim());

      if (temp.get().getTheatername() == null) {
         throw new Exception("Theatre Not Available");
      } else {
         return temp;
      }
   }

}
