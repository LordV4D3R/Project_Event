package com.antran.projectevent.controller;

import com.antran.projectevent.constant.common.BusinessResult;
import com.antran.projectevent.model.Account;
import com.antran.projectevent.service.interfaceservice.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    //Get all users
    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam(required = false) String search) {
        try {
            BusinessResult<List<Account>> result = accountService.getAllAccounts(search);

            if (result.getStatusCode() > 0) {
                // Trả về danh sách User với mã trạng thái HTTP 200 (OK)
                return ResponseEntity.ok(result.getData());
            } else {
                // Trả về thông báo Not Found nếu không có dữ liệu
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getMessage());
            }
        } catch (Exception e) {
            // Trả về mã trạng thái HTTP 400 (Bad Request) nếu xảy ra lỗi
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable UUID id) {
        Optional<Account> account = accountService.getAccountById(id);
        return account.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Something went wrong"));
    }

    //Add new user
    @PostMapping
    public Account addAccount(@RequestBody Account account) {
        return accountService.addAccount(account);
    }

    //Update user by id
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAccountById(@PathVariable UUID id, @RequestBody Account updateAccount) {
        try
        {
            Account account = accountService.updateAccountById(id, updateAccount);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Something went wrong");
        }

    }

    //Delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccountById(@PathVariable UUID id) {
        try {
            accountService.deleteAccountById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Something went wrong");
        }
    }





}
