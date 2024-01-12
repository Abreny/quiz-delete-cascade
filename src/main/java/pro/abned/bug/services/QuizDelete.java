package pro.abned.bug.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class QuizDelete {
    private final EntityManager entityManager;

    public QuizDelete(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void delete(Long quizId) {
        Query query = entityManager.createQuery("DELETE FROM QuizQuestionItemAnswer q WHERE q.question.question.quiz.id = ?1");
        query.setParameter(1, quizId);
        query.executeUpdate();

        query = entityManager.createQuery("DELETE FROM QuizQuestionItem q WHERE q.question.quiz.id = ?1");
        query.setParameter(1, quizId);
        query.executeUpdate();

        query = entityManager.createQuery("DELETE FROM QuizQuestion q WHERE q.quiz.id = ?1");
        query.setParameter(1, quizId);
        query.executeUpdate();

        query = entityManager.createQuery("DELETE FROM Quiz q WHERE q.id = ?1");
        query.setParameter(1, quizId);
        query.executeUpdate();
    }
}
