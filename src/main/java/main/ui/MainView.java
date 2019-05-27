package main.ui;

import com.vaadin.flow.component.grid.Grid;
import main.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;

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
        add(grid);
    }
}
