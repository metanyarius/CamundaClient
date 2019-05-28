package main.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import main.entities.Task;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import main.entities.TaskCompleteResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Route
@PWA(name = "Camunda Test Project — Client", shortName = "Camunda Test Project")
public class MainView extends VerticalLayout {
    public MainView() {
        String taskUrl = "http://localhost:8080/rest/engine/default/task?processDefinitionKey=Process_1prwej3";
        String taskCompleteUrl = "http://localhost:8080/rest/engine/default/task/%s/complete";
        RestTemplate restTemplateGet = new RestTemplate();
        ResponseEntity<Task[]> responseEntity = restTemplateGet.getForEntity(taskUrl, Task[].class);
        Task[] tasks = responseEntity.getBody();

        Grid<Task> grid = new Grid<>(Task.class);
        grid.setItems(tasks);
        NativeButtonRenderer button = new NativeButtonRenderer<>("Complete", clickedItem -> {
            Task task = (Task) clickedItem;
            String fullRequest = String.format(taskCompleteUrl, task.getId());
            String message = null;

            RestTemplate restTemplatePost = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            try {
                TaskCompleteResponse response = restTemplatePost.postForObject(fullRequest, new HttpEntity<>(headers), TaskCompleteResponse.class);
                message = "Задача " + task.getName() + " помечена, как выполненная";
            } catch (HttpServerErrorException e) {
                message = "Завершить задачу " + task.getName() + " не удалось";
            }

            Notification.show(message);
        });
        grid.addColumn(button);

        add(grid);
    }
}
