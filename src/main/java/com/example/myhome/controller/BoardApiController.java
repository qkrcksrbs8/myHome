package com.example.myhome.controller;

import com.example.myhome.model.Board;
import com.example.myhome.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;

    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false) String title,
                    @RequestParam(required = false, defaultValue = "") String content ) {
        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            return repository.findAll();
        }
        return repository.findByTitleOrContent(title, content);
    }

    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {

        String email = newBoard.getContent();
        if (email.lastIndexOf("@") == -1) {
            System.out.println("@없음");
        }
        else {
            if (check(email.substring(0,email.lastIndexOf("@")))) {
                System.out.println("유효성 위반");
            }
            else {
                System.out.println("유효성 합격");
            }
        }
        System.out.println(email.substring(email.lastIndexOf("@")));

        return null;
//        return repository.save(newBoard);
    }

    public boolean check(String str){
        String[] arrs = {"[", "]", "<", ">", "@", "(", ")", ";", ":", "\\/", "\\`"};
        int cnt = Arrays.stream(arrs).mapToInt(str::indexOf).sum();
        if (cnt == (arrs.length * -1)) return false;
        return true;
    }


    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }


}