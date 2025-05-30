package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;
import ru.job4j.todo.utils.TransactionUtility;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TaskRepositoryTest {
    private static TaskStore repository;
    private static SessionFactory sf;

    @AfterAll
    static void tearDown() {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createQuery("delete from Task").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void setUp() {
        sf = new Configuration().configure().buildSessionFactory();
        TransactionUtility tx = new TransactionUtility(sf);
        repository = new TaskStore(tx);
    }

    @BeforeEach
    void setUpBeforeClass() {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createQuery("delete from Task").executeUpdate();
            List<Task> taskList = List.of(new Task("Test task", false),
                    new Task("Test task2", true),
                    new Task("Test task3", false),
                    new Task("Test task4", true),
                    new Task("Test task5", false));
            taskList.forEach(session::save);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenAddNewTaskIsSuccessful() {
        Task newTask = new Task("Test task", false);

        Optional<Task> result = repository.addNewTask(newTask);

        assertThat(result).isPresent();
    }

    @Test
    void whenAddNewTaskIsNotSuccessful() {
        Optional<Task> result = repository.addNewTask(null);

        assertThat(result).isNotPresent();
    }

    @Test
    void whenGetTaskByIdIsSuccessful() {
        Task newTask = new Task("Test task", false);

        Optional<Task> expected = repository.addNewTask(newTask);
        Optional<Task> result = repository.getTaskById(expected.get().getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expected.get());
    }

    @Test
    void whenUpdateTaskIsSuccessful() {
        Task newTask = new Task("Test task", false);
        Task savedTask = repository.addNewTask(newTask).get();

        savedTask.setDescription("Update task description");
        savedTask.setCompleted(true);

        Optional<Task> result = repository.updateTask(savedTask);
        assertThat(result).isPresent();
        assertThat(result.get().getDescription()).isEqualTo("Update task description");
        assertThat(result.get().getCompleted()).isTrue();
    }

    @Test
    void whenUpdateTaskIsFailedBecauseOfWrongId() {
        Task newTask = new Task("Test task", false);
        Task savedTask = new Task(newTask.getDescription(), false);

        Optional<Task> result = repository.updateTask(savedTask);

        assertThat(result).isNotPresent();
    }

    @Test
    void whenDeleteTaskIsSuccessful() {
        Task newTask = new Task("Test task", false);
        Task created = repository.addNewTask(newTask).get();

        boolean res = repository.deleteTask(created);
        Optional<Task> result = repository.getTaskById(created.getId());

        assertThat(res).isTrue();
        assertThat(result).isNotPresent();
    }

    @Test
    void whenDeleteTaskIsFailedBecauseOfWrongId() {
        Task newTask = new Task("Test task", false);
        Task created = repository.addNewTask(newTask).get();

        boolean res = repository.deleteTask(new Task());
        Optional<Task> result = repository.getTaskById(created.getId());

        assertThat(res).isFalse();
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(created.getId());
    }

    @Test
    void whenGetAllTasksIsSuccessful() {
        assertThat(repository.getAllTasks()).isNotEmpty().hasSize(5);
    }

    @Test
    void whenGetAllTasksIsSuccessfulWhenAddNewTaskIsSuccessful() {
        Task newTask = new Task("New task for test in whenGetAllTasksIsSuccessfulWhenAddNewTaskIsSuccessful", false);
        Task savedTask = repository.addNewTask(newTask).get();
        List<Task> result = repository.getAllTasks();
        boolean isFound = false;
        for (Task task : result) {
            if (task.equals(savedTask)) {
                isFound = true;
            }
        }
        assertThat(result).isNotEmpty().hasSize(6);
        assertThat(isFound).isTrue();
    }

    @Test
    void whenGetAllTasksIsSuccessfulWhenTaskIsEmpty() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> result = repository.getAllTasks();
        result.forEach(session::delete);
        session.getTransaction().commit();
        session.close();
        assertThat(repository.getAllTasks()).isEmpty();
    }

    @Test
    void whenGetAllTaskByCompletableIsSuccessful() {
        List<Task> tasksIsCompleted = repository.getAllTaskByCompletable(true);
        List<Task> tasksIsNotCompleted = repository.getAllTaskByCompletable(false);

        assertThat(tasksIsCompleted).hasSize(2);
        assertThat(tasksIsNotCompleted).hasSize(3);
    }

    @Test
    void whenGetAllTaskByCompletableIsSuccessfulPlusAddNewTask() {
        Task newTask = new Task("New task for test", false);
        Task newTask2 = new Task("New task for test", true);
        repository.addNewTask(newTask);
        repository.addNewTask(newTask2);
        List<Task> tasksIsCompleted = repository.getAllTaskByCompletable(true);
        List<Task> tasksIsNotCompleted = repository.getAllTaskByCompletable(false);

        assertThat(tasksIsCompleted).hasSize(3);
        assertThat(tasksIsNotCompleted).hasSize(4);
    }
}