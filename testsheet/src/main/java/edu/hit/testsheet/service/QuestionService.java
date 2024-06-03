package edu.hit.testsheet.service;

import edu.hit.testsheet.Dto.QuestionUpdateDto;
import edu.hit.testsheet.bean.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAllQuestions();

    Question addQuestion(Question question);

    void deleteQuestionById(Long id);

    Question selectQuestionById(Long id);

    Question updateQuestion(Long id, QuestionUpdateDto questionUpdateDto);
}
