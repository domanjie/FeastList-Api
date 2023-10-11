package FeastList.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/v1/users",produces="application/json")
public class UserController {
	private  UserService userService;
	public UserController(UserService userService){
		this.userService=userService;
	}
	
}
