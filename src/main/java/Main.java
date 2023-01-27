import model.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Objects;


public class Main {

    private final String url = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders headers = new HttpHeaders();
    static String result = "";

    public Main() {
        String sessionId = getAllUsers();
        headers.set("cookie", sessionId);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.createUser();
        main.updateUser();
        main.deleteUser(3L);
        if(result.length() != 18) {
            System.out.println("Ошибка");
        } else {
            System.out.println("Итоговый код - " + result);
        }
    }

    public String getAllUsers() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return String.join(";", Objects.requireNonNull(forEntity.getHeaders().get("set-cookie")));
    }

    public void createUser() {
        User user = new User("James","Brown", (byte) 41);
        user.setId(3);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        String request = restTemplate.postForEntity(url, entity, String.class).getBody();
        result = result + request;
        new ResponseEntity<>(request, HttpStatus.OK);
    }

    public void updateUser() {
        User user = new User("Thomas","Shelby", (byte) 27);
        user.setId(3);
        HttpEntity<User> entity = new HttpEntity<>(user,headers);
        String response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class).getBody();
        result = result + response;
        new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void deleteUser(@PathVariable Long id) {
        HttpEntity<User> entity = new HttpEntity<>(headers);
        String request = restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, entity, String.class).getBody();
        result = result + request;
        new ResponseEntity<>(request, HttpStatus.OK);
    }
}