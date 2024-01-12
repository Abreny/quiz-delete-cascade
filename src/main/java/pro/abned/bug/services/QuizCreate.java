package pro.abned.bug.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pro.abned.bug.entities.*;

@Service
public class QuizCreate {
    private final EntityManager entityManager;

    public QuizCreate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Quiz create() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Choose the right answer in the sentence below?");
        entityManager.persist(quiz);

        QuizQuestion question = new QuizQuestion();
        question.setQuestion("They [0] students.");
        question.setQuiz(quiz);
        entityManager.persist(question);

        QuizQuestionItem questionItem = new QuizQuestionItem();
        questionItem.setType(QuizQuestionItemType.SELECT);
        questionItem.setQuestion(question);
        entityManager.persist(questionItem);

        QuizQuestionItemAnswer answer = new QuizQuestionItemAnswer();
        answer.setAnswer("are");
        answer.setCorrect(true);
        answer.setQuestion(questionItem);
        entityManager.persist(answer);

        answer = new QuizQuestionItemAnswer();
        answer.setAnswer("is");
        answer.setCorrect(false);
        answer.setQuestion(questionItem);
        entityManager.persist(answer);

        answer = new QuizQuestionItemAnswer();
        answer.setAnswer("am");
        answer.setCorrect(false);
        answer.setQuestion(questionItem);
        entityManager.persist(answer);

        return quiz;
    }
}
