package org.dev.krishna.controller;

import org.dev.krishna.model.ResponseEntity;
import org.dev.krishna.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public abstract class CrudController<T> {

    protected abstract CrudService<T> getService();

    @GetMapping("/list")
    public @ResponseBody
    List<T> list(){
        return getService().findAll();
    }

    @GetMapping("/find/{id}")
    public @ResponseBody
    T find(@PathVariable String id) throws Exception {
        try {
            Optional<T> elem = getService().find(id);
            if(elem.isPresent()) return elem.get();
            else throw new Exception("Element not found");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PostMapping("/add")
    public @ResponseBody ResponseEntity add(@RequestBody T t){
        try {
            getService().add(t);
            return success("Added succesfully");
        } catch (Exception e) {
            e.printStackTrace();
            return fail("Error : "+e.getMessage());
        }
    }

    @PostMapping("/update")
    public @ResponseBody
    ResponseEntity update(@RequestBody T t){
        try {
            getService().update(t);
            return success("Updated succesfully");
        } catch (Exception e) {
            e.printStackTrace();
            return fail("Error : "+e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public @ResponseBody
    ResponseEntity delete(@PathVariable String id){
        try {
            getService().delete(id);
            return success("Deleted succesfully");
        } catch (Exception e) {
            e.printStackTrace();
            return fail("Error : "+e.getMessage());
        }
    }

    protected static ResponseEntity success(String msg){
        return new ResponseEntity(msg, HttpStatus.OK);
    }

    protected static ResponseEntity fail(String msg){
        return new ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
