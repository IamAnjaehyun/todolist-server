package org.example.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.TodoModel;
import org.example.model.TodoRequest;
import org.example.model.TodoResponse;
import org.example.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/")
public class TodoController {

    private final TodoService service;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request){
        log.info("CREATE");

        //값 확인해서 타이틀 없으면 잘못된 요청이라고 응답 내려줌
        if(ObjectUtils.isEmpty(request.getTitle()))
            return ResponseEntity.badRequest().build();
        //오더값이 없으면 디폴트값 0L
        if(ObjectUtils.isEmpty(request.getOrder()))
            request.setOrder(0L);
        //컴플리티드 값 없으면 디폴트값 false
        if(ObjectUtils.isEmpty(request.getComplected()))
            request.setComplected(false);

        TodoModel result = this.service.add(request);

        return ResponseEntity.ok(new TodoResponse(result));
    }

    //아이템 하나만 조회
    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id){
        log.info("READ ONE");
        TodoModel result = this.service.searchById(id);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    //전체 조회
    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll(){
        log.info("READ ALL");
        List<TodoModel> list = this.service.searchAll();
        List<TodoResponse> response = list.stream().map(TodoResponse::new)
                                                    .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    //수정
    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request){
        log.info("UPDATE");
        TodoModel result =  this.service.updateById(id, request);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){
        log.info("DELETE");
        this.service.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(){
        log.info("DELETE ALL");
        this.service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
