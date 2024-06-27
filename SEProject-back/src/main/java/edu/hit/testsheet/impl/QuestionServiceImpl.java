package edu.hit.testsheet.impl;


import edu.hit.testsheet.Dto.QuestionUpdateDto;
import edu.hit.testsheet.Exception.QuestionCanNotBeDeleteException;
import edu.hit.testsheet.Exception.QuestionNotFoundException;
import edu.hit.testsheet.bean.Paper;
import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.repository.PaperRepository;
import edu.hit.testsheet.repository.QuestionRepository;
import edu.hit.testsheet.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private PaperRepository paperRepository;

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getQuestionsByPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Question> questionPage = questionRepository.findAll(pageable);
        List<Question> questions = questionPage.getContent();

        // 增加日志输出
        System.out.println("Page Index: " + pageIndex);
        System.out.println("Page Size: " + pageSize);
        System.out.println("Total Elements: " + questionPage.getTotalElements());
        System.out.println("Total Pages: " + questionPage.getTotalPages());
        System.out.println("Current Page Elements: " + questions.size());
        return questions;
    }

    @Override
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestionById(Long id) {
        if (questionRepository.existsById(id)) {
            List<Paper> allPapers = paperRepository.findAll();
            for(Paper p : allPapers){
                String content = p.getContent();
                String[] questionIds = content.split(" ");
                for(String qid : questionIds){
                    if(id == Long.parseLong(qid)){
                        throw new QuestionCanNotBeDeleteException(id,p.getTitle());
                    }
                }
            }
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
    public Question selectQuestionByIdInPaper(Long id){
        return questionRepository.findById(id).orElse(null);
    }
    @Override
    public Question updateQuestion(Long id, QuestionUpdateDto updateRequest) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        question.setDescription(updateRequest.getDescription());
        question.setType(updateRequest.getType());
        question.setAnswer(updateRequest.getAnswer());
        question.setAnalysis(updateRequest.getAnalysis());
        question.setTag(updateRequest.getTag());
        question.setDifficultLevel(updateRequest.getDifficultLevel());
        return questionRepository.save(question);
    }
    @Override
    public List<Question> selectQuestion(String keywords, String type, String difficultLevel, String username,
                                         int pageIndex, int pageSize) {
        return questionRepository.searchQuestions(keywords, type, difficultLevel, username, pageIndex, pageSize);
    }

    @Override
    public long getQuestionsCount() {
        return questionRepository.count();
    }
}
