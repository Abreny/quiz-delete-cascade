package pro.abned.bug.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class QuizQuestionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuizQuestionItemType type;

    @ManyToOne
    private QuizQuestion question;

    @OneToMany(mappedBy = "question")
    private List<QuizQuestionItemAnswer> answers;
}
