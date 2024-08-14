package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.dto.*;
import com.bluecode.chatbot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
@Slf4j
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
    public ResponseEntity<Long> addUser(@RequestBody UserAddCallDto userAddCallDto){
        Long userid=userService.addUser(userAddCallDto);
        return ResponseEntity.ok(userid);
    }

    @PostMapping("/user/findId")
    public ResponseEntity<String> findId(@RequestBody UserAddCallDto userAddCallDto){
        try {
            return ResponseEntity.ok(userService.findLoginId(userAddCallDto));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/checkAuth/getUserInfo")
    public ResponseEntity getUserInfo(@RequestBody UserIdDto userIdDto){
        try {
            UserInfoResponseDto responseDto=userService.getUserInfo(userIdDto.getUserId());
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/checkAuth/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordCallDto updatePasswordCallDto){
        try {
            userService.updatePassword(updatePasswordCallDto);
            return ResponseEntity.ok("비밀번호 수정되었습니다");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/checkAuth/updateEmail")
    public ResponseEntity<String> updateEmail(@RequestBody UpdateEmailCallDto updateEmailCallDto){
        try {
            userService.updateEmail(updateEmailCallDto);
            return ResponseEntity.ok("이메일 수정되었습니다");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
