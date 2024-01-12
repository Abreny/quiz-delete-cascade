package pro.abned.bug.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pro.abned.bug.entities.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class QuizDeleteTest {
    @Autowired
    EntityManager entityManager;

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        this.quiz = new Quiz();
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

        entityManager.flush();
    }

    @Test
    void testDelete() {
        assertThat(quiz.getId()).isNotNull();
        QuizDelete quizDelete = new QuizDelete(entityManager);
        quizDelete.delete(quiz.getId());

        Query query = entityManager.createQuery("SELECT q FROM Quiz q WHERE q.id = ?1");
        query.setParameter(1, quiz.getId());
        assertThat(query.getResultList()).isEmpty();

        query = entityManager.createQuery("SELECT q FROM QuizQuestion q WHERE q.quiz.id = ?1");
        query.setParameter(1, quiz.getId());
        assertThat(query.getResultList()).isEmpty();

        query = entityManager.createQuery("SELECT q FROM QuizQuestionItem q WHERE q.question.quiz.id = ?1");
        query.setParameter(1, quiz.getId());
        assertThat(query.getResultList()).isEmpty();

        query = entityManager.createQuery("SELECT q FROM QuizQuestionItemAnswer q WHERE q.question.question.quiz.id = ?1");
        query.setParameter(1, quiz.getId());
        assertThat(query.getResultList()).isEmpty();
    }
}
