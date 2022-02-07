package com.example.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/api/item", produces = "application/json")
public class MainController {
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getItem (@PathVariable(value = "id") Integer id) {
        Item i = itemRepository.findById(id).orElse(null);
        if (i == null) {
            return new ResponseEntity<>("ID does not exist", HttpStatus.NOT_FOUND);
        }
        //return (itemRepository.findById(id));
        return new ResponseEntity<>(i, HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Item> getAllItems () {
        return itemRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> addNewItem (
    @RequestParam String name,
    @RequestParam String category,
    @RequestParam Float price) {

        // Form Validation
        if (name == "" || name == null || name.isEmpty()) {
            return new ResponseEntity<>("Name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (category == "" || category == null || category.isEmpty()) {
            return new ResponseEntity<>("Category cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (price == null) {
            return new ResponseEntity<>("Price cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (price < 0 || (price*100) % 10 != 0 ) {
            return new ResponseEntity<>("Invalid Price", HttpStatus.BAD_REQUEST);
        }

        Item i = new Item();
        i.setName(name);
        i.setCategory(category);
        i.setPrice(price);

        itemRepository.save(i);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem (@PathVariable(value = "id") Integer id) {
        itemRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateItem (
    @PathVariable(value = "id") Integer id,
    @RequestParam String name,
    @RequestParam String category,
    @RequestParam Float price) {

        // Form Validation
        if (name == "" || name == null || name.isEmpty()) {
            return new ResponseEntity<>("Name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (category == "" || category == null || category.isEmpty()) {
            return new ResponseEntity<>("Category cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (price == null) {
            return new ResponseEntity<>("Price cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (price < 0 || (price*100) % 10 != 0 ) {
            return new ResponseEntity<>("Invalid Price", HttpStatus.BAD_REQUEST);
        }

        //itemRepository.updateItem(id, name, category, price);
        Item i = itemRepository.findById(id).orElse(null);

        if (i == null) {
            return new ResponseEntity<>("Item does not exist", HttpStatus.BAD_REQUEST);
        } else {
            i.setName(name);
            i.setCategory(category);
            i.setPrice(price);
            itemRepository.save(i);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
