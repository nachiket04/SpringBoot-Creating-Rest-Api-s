package com.example.RestApiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class Controller {

    HashMap <Integer, User> allUsers = new HashMap<>();

    @Autowired
    public OtherController oc;

    @GetMapping("/get_other")
    public int getOther(){
        oc.setId(5);
        return oc.getId();
    }

    @GetMapping("/get_users")
    public List<User> getUsers(){
        List <User> usersList = new ArrayList<>();
        for(int x: allUsers.keySet()){
            usersList.add(allUsers.get(x));
        }
        Collections.sort(usersList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getId() - o2.getId();
            }
        });
        return usersList;
    }

    // request param
    @PostMapping("/add_user")
    public String addUser(@RequestParam ("id") int id, @RequestParam ("name") String name,
                        @RequestParam ("age") int age, @RequestParam ("country") String country){
        User newUser = new User(id, name, age, country);
        allUsers.put(id, newUser);
        return ("User " + name +" added Successfully..!");
    }

    // request body
    @PostMapping("/add_user_body")
    public String addUser(@RequestBody() User user){
        allUsers.put(user.getId(), user);
        return ("User " + user.getName() +" added Successfully..!");
    }

    // Path Variable
    @GetMapping("/get_user/{id}")
    public User get_user(@PathVariable ("id") int id){
        return allUsers.get(id);
    }

    @PutMapping("/edit_user/{id}")
    public String edit_user(@PathVariable ("id") int id, @RequestBody () User user){
        allUsers.put(user.getId(), user);
        return "Done..";
    }

    @DeleteMapping("/remove_user/{id}")
    public String delete_user(@PathVariable("id") int id){
        allUsers.remove(id);
        return "User " + allUsers.get(id).getName() + " removed from data Base Successfully..!";
    }
}
