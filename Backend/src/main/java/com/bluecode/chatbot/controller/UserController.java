package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.dto.UserAddCallDto;
import com.bluecode.chatbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/user/exists/username/{username}")
    public ResponseEntity<Boolean> checkUserNameDuplicate(@PathVariable String username){
        return ResponseEntity.ok(userService.checkUserNameDuplicate(username));
    }
    @GetMapping("/user/exists/email/{email}")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email){
        return ResponseEntity.ok(userService.checkEmailDuplicate(email));
    }
    @GetMapping("/user/exists/id/{id}")
    public ResponseEntity<Boolean> checkIdDuplicate(@PathVariable String id){
        return ResponseEntity.ok(userService.checkIdDuplicate(id));
    }

    @PostMapping("/user/create")
    public ResponseEntity<Void> addUser(@RequestBody UserAddCallDto userAddCallDto){
        userService.addUser(userAddCallDto);
        return ResponseEntity.ok().build();
    }
}
