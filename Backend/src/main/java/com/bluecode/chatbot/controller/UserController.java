package com.bluecode.chatbot.controller;

import com.bluecode.chatbot.dto.UserAddCallDto;
import com.bluecode.chatbot.dto.UserInfoResponseDto;
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

    @GetMapping("/user/getUserInfo/{loginId}")
    public ResponseEntity getUserInfo(@PathVariable String loginId){
        try {
            UserInfoResponseDto responseDto=userService.getUserInfo(loginId);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/user/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody UserAddCallDto userAddCallDto){
        try {
            userService.updatePassword(userAddCallDto);
            return ResponseEntity.ok("비밀번호 수정되었습니다");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/user/updateEmail")
    public ResponseEntity<String> updateEmail(@RequestBody UserAddCallDto userAddCallDto){
        try {
            userService.updateEmail(userAddCallDto);
            return ResponseEntity.ok("이메일 수정되었습니다");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
