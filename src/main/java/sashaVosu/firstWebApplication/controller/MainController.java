package sashaVosu.firstWebApplication.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("main")
public class MainController {

    private List<String> messageList = new ArrayList<String>(){{
        add("First application message");
        add("Second application message");
    }};

    @GetMapping
    private List<String> main() {

        return messageList;
    }

    @PostMapping
    public String createMessage(@RequestBody String messageText) throws IndexOutOfBoundsException{
        if (messageText.length() > 1 && messageText.length() <= 140) {
            messageList.add(messageText);
            return messageText;
        }  else {
            throw new IndexOutOfBoundsException();
        }
    }
}
