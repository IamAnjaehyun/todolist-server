package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.TodoModel;
import org.example.model.TodoRequest;
import org.example.model.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    //추가
    public TodoModel add(TodoRequest request){
        TodoModel todoModel = new TodoModel();
        todoModel.setTitle(request.getTitle());
        todoModel.setOrder(request.getOrder());
        todoModel.setCompleted(request.getComplected());
        return this.todoRepository.save(todoModel);
    }

    //조회
    public TodoModel searchById(Long id){
        return this.todoRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //전체 조회
    public List<TodoModel> searchAll(){
        return this.todoRepository.findAll();
    }

    //수정
    public TodoModel updateById(Long id, TodoRequest request){
        TodoModel todoModel = this.searchById(id);
        //타이틀 수정
        if(request.getTitle()!=null){
            todoModel.setTitle(request.getTitle());
        }
        //오더 수정
        if(request.getOrder()!=null){
            todoModel.setOrder(request.getOrder());
        }
        //컴플리티드 수정
        if(request.getComplected()!=null){
            todoModel.setCompleted(request.getComplected());
        }
        return this.todoRepository.save(todoModel);
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
