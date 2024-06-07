package edu.hit.testsheet.service;

import edu.hit.testsheet.Dto.QuestionUpdateDto;
import edu.hit.testsheet.bean.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAllQuestions();
    List<Question> getQuestionsByPage(int offset, int pageSize);

    Question addQuestion(Question question);

    void deleteQuestionById(Long id);

    Question selectQuestionById(Long id);

    Question selectQuestionByIdInPaper(Long id);

    Question updateQuestion(Long id, QuestionUpdateDto questionUpdateDto);

    List<Question> selectQuestion(String keywords, String type, String difficultLevel,String username, int pageIndex,int pageSize);

    long getQuestionsCount();
}
