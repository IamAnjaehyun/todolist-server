package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    //추가
    public TodoEntity add(TodoRequest request){
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTitle(request.getTitle());
        todoEntity.setOrder(request.getOrder());
        todoEntity.setCompleted(request.getComplected());
        return this.todoRepository.save(todoEntity);
    }

    //조회
    public TodoEntity searchById(Long id){
        return this.todoRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //전체 조회
    public List<TodoEntity> searchAll(){
        return this.todoRepository.findAll();
    }

    //수정
    public TodoEntity updateById(Long id, TodoRequest request){
        TodoEntity todoEntity = this.searchById(id);
        //타이틀 수정
        if(request.getTitle()!=null){
            todoEntity.setTitle(request.getTitle());
        }
        //오더 수정
        if(request.getOrder()!=null){
            todoEntity.setOrder(request.getOrder());
        }
        //컴플리티드 수정
        if(request.getComplected()!=null){
            todoEntity.setCompleted(request.getComplected());
        }
        return this.todoRepository.save(todoEntity);
    }


    //삭제
     public void deleteById(Long id){
        this.todoRepository.deleteById(id);
     }

    //전체 목록 삭제
    public void deleteAll(){
        this.todoRepository.deleteAll();
    }

}
