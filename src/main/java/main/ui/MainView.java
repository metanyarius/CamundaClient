package main.ui;

import com.vaadin.flow.component.UI;
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

import java.util.ArrayList;

@Route
@PWA(name = "Camunda Test Project — Client", shortName = "Camunda Test Project")
public class MainView extends VerticalLayout {

    public static UI currUI = null;

    public static final String TASK_URL = "http://localhost:8080/rest/engine/default/task?processDefinitionKey=Process_1prwej3";
    public static final String TASK_COMPLETE_URL = "http://localhost:8080/rest/engine/default/task/%s/complete";

    public void loadTasks(Grid<Task> grid) {
        ResponseEntity<Task[]> responseEntity = new RestTemplate().getForEntity(TASK_URL, Task[].class);
        Task[] tasks = responseEntity.getBody();
        grid.setItems(tasks);
    }

    public TaskCompleteResponse completeTask(Task task) {
        String fullRequest = String.format(TASK_COMPLETE_URL, task.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>("{\"variables\":{}}", headers);
        return new RestTemplate().postForObject(fullRequest, entity, TaskCompleteResponse.class);
    }

    public MainView() {
        Grid<Task> grid = new Grid<>(Task.class);
        loadTasks(grid);
        NativeButtonRenderer button = new NativeButtonRenderer<>("Complete", clickedItem -> {
            Task task = (Task) clickedItem;
            String message = null;
            try {
                completeTask(task);
                message = "Задача " + task.getName() + " помечена, как выполненная";
                grid.setItems(new ArrayList<>());
                loadTasks(grid);
            } catch (HttpServerErrorException e) {
                message = "Завершить задачу " + task.getName() + " не удалось";
                e.printStackTrace();
            }
            Notification.show(message);
        });
        grid.addColumn(button);
        add(grid);
        currUI = UI.getCurrent();
    }

}
