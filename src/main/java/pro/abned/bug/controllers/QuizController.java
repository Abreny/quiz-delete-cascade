package pro.abned.bug.controllers;

import org.springframework.web.bind.annotation.*;
import pro.abned.bug.entities.Quiz;
import pro.abned.bug.services.QuizCreate;
import pro.abned.bug.services.QuizDelete;

@RestController
@RequestMapping("api/v1/quizs")
public class QuizController {
    private final QuizCreate quizCreate;
    private final QuizDelete quizDelete;

    public QuizController(QuizCreate quizCreate, QuizDelete quizDelete) {
        this.quizCreate = quizCreate;
        this.quizDelete = quizDelete;
    }

    @PostMapping
    public Quiz createQuiz() {
        return quizCreate.create();
    }

    @DeleteMapping("{id:\\d+}")
    public String deleteQuiz(@PathVariable("id") Long quizId) {
        quizDelete.delete(quizId);
        return "Quiz deleted successfully.";
    }
}
