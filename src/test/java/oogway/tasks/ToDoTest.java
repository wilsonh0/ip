package oogway.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ToDoTest {
    @Test
    public void testToString() {
        ToDo todo = new ToDo("test", false);
        assertEquals("[T][ ] test", todo.toString());
    }

    @Test
    public void testMarkAsDone() {
        ToDo todo = new ToDo("test", false);
        todo.setDone(true);
        assertEquals("[T][X] test", todo.toString());
    }
}
