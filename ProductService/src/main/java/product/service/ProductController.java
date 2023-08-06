package product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    List<String> products = List.of("Beans", "Oat cereals", "Bread", "Milk");
    @Autowired
    UserServiceFeignClient userServiceFeignClient;

    @PostMapping("/all")
    public ResponseEntity<List<String>> getProducts(@RequestBody User userBody){
        User user = new User("ion", "ion");
        ResponseEntity<Boolean> responseIsUserAuthenticated = userServiceFeignClient.isUserAuthenticated(user);
        if(responseIsUserAuthenticated != null && responseIsUserAuthenticated.getBody() != null) {
            boolean isUserAuthenticated = responseIsUserAuthenticated.getBody();
            if(isUserAuthenticated){
                return ResponseEntity.ok(products);
            }
        }
        return new ResponseEntity<>(List.of("Unauthorized"), HttpStatus.UNAUTHORIZED);
    }

}
