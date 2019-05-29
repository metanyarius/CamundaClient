package main.api;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.Command;
import main.ui.MainView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
public final class NotificationController {

    @PostMapping("")
    public void notify(@RequestBody String notification) {
        MainView.currUI.access((Command) () -> {
            Notification.show("Уведомление: " + notification);
        });
    }

}