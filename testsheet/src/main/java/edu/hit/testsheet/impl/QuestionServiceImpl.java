package edu.hit.testsheet.impl;


import edu.hit.testsheet.Dto.QuestionUpdateDto;
import edu.hit.testsheet.Exception.QuestionNotFoundException;
import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.repository.QuestionRepository;
import edu.hit.testsheet.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestionById(Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
        } else {
            throw new QuestionNotFoundException(id);
        }
    }

    @Override
    public Question selectQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
    }

    @Override
    public Question updateQuestion(Long id, QuestionUpdateDto updateRequest) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        question.setDescription(updateRequest.getDescription());
        question.setType(updateRequest.getType());
        question.setAnswer(updateRequest.getAnswer());
        return questionRepository.save(question);
    }
}
