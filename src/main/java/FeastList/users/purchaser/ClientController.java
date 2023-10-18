package FeastList.users.purchaser;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    public ClientService clientService;
    public ClientController(@RequestBody ClientService clientService){
        this.clientService=clientServ
    }
    @PostMapping
    public Client newClient(@RequestBody Client client){
        return purchaserService.a
    }
}
