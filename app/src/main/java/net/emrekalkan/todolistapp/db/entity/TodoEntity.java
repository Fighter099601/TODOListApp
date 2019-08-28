package net.emrekalkan.todolistapp.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import net.emrekalkan.todolistapp.utils.Constants;

@Entity(tableName = "Todo")
public class TodoEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int todolistId;
    private String name;
    private String description;
    private String deadline;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTodolistId() {
        return todolistId;
    }

    public void setTodolistId(int todolistId) {
        this.todolistId = todolistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getStatus() {
        return status;
    }

    public String getStatuss() {
        final int completed = Constants.TodoStatus.TODO_COMPLETED;
        final int active = Constants.TodoStatus.TODO_ACTIVE;
        final int expired = Constants.TodoStatus.TODO_EXPIRED;

        switch (status) {
            case completed: return "Completed";
            case active: return "Active";
            case expired: return "Expired";
            default:
                return "";
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
