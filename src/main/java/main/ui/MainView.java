package main.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import main.entities.Task;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Route
@PWA(name = "Camunda Test Project — Client", shortName = "Camunda Test Project")
public class MainView extends VerticalLayout {
    public MainView() {
        String taskUrl = "http://localhost:8080/rest/engine/default/task?processDefinitionKey=Process_1prwej3";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Task[]> responseEntity = restTemplate.getForEntity(taskUrl, Task[].class);
        Task[] tasks = responseEntity.getBody();

        Grid<Task> grid = new Grid<>(Task.class);
        grid.setItems(tasks);
        NativeButtonRenderer button = new NativeButtonRenderer<>("Complete", clickedItem -> {
            Task task = (Task) clickedItem;
            String taskName = task.getName();

            Notification.show("Задача " + taskName + " помечена, как выполненная");
        });
        grid.addColumn(button);

        add(grid);
    }
}
