package edu.hit.testsheet.impl;


import edu.hit.testsheet.dto.QuestionUpdateDto;
import edu.hit.testsheet.exception.InvalidQuestionException;
import edu.hit.testsheet.exception.QuestionCanNotBeDeletedException;
import edu.hit.testsheet.exception.QuestionNotFoundException;
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

    public QuestionServiceImpl(QuestionRepository questionRepository, PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
        this.questionRepository = questionRepository;
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
        if (question.getType() == null || question.getType().isEmpty()) {
            throw new InvalidQuestionException("类型不可为空！");
        }
        if (question.getTag() == null || question.getTag().isEmpty()) {
            throw new InvalidQuestionException("标签不可为空！");
        }
        if (question.getDifficultLevel() == null || question.getDifficultLevel().isEmpty()) {
            throw new InvalidQuestionException("难度等级不可为空！");
        }
        if (question.getAnswer() == null || question.getAnswer().isEmpty()) {
            if (question.getType().equals("选择题") || question.getType().equals("判断题")) {
                throw new InvalidQuestionException("正确选项不可为空！");
            }
            throw new InvalidQuestionException("答案不可为空！");
        }
        if (question.getDescription() == null || question.getDescription().isEmpty()) {
            if (question.getType().equals("选择题")) {
                throw new InvalidQuestionException("题目和选项不可有空！");
            }
            throw new InvalidQuestionException("题目不可为空！");
        }
        if (question.getCreatedBy() == null || question.getCreatedBy().isEmpty()) {
            throw new InvalidQuestionException("创建人不可为空！");
        }
        return questionRepository.save(question);
    }


    @Override
    public void deleteQuestionById(Long id) {
        if (questionRepository.existsById(id)) {
            List<Paper> allPapers = paperRepository.findAll();
            for (Paper p : allPapers) {
                String content = p.getContent();
                if (!content.isEmpty()) {
                    String[] questionIds = content.split(" ");
                    if (questionIds.length != 0) {
                        for (String qid : questionIds) {
                            if (id == Long.parseLong(qid)) {
                                throw new QuestionCanNotBeDeletedException(id, p.getTitle());
                            }
                        }
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
    public Question selectQuestionByIdInPaper(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public Question updateQuestion(Long id, QuestionUpdateDto updateRequest) {
        if (updateRequest.getType() == null || updateRequest.getType().isEmpty()) {
            throw new InvalidQuestionException("题型不可为空！");
        }
        if (updateRequest.getTag() == null || updateRequest.getTag().isEmpty()) {
            throw new InvalidQuestionException("标签不可为空！");
        }
        if (updateRequest.getDifficultLevel() == null || updateRequest.getDifficultLevel().isEmpty()) {
            throw new InvalidQuestionException("难度不可为空！");
        }
        if (updateRequest.getAnswer() == null || updateRequest.getAnswer().isEmpty()) {
            throw new InvalidQuestionException("答案不可为空！");
        }
        if (updateRequest.getDescription() == null || updateRequest.getDescription().isEmpty()) {
            throw new InvalidQuestionException("问题不可为空！");
        }
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
                                         int pageIndex, int pageSize, String orderAttribute, String order) {
        return questionRepository.searchQuestions(keywords, type, difficultLevel, username, pageIndex, pageSize,
                orderAttribute, order);
    }

    @Override
    public long getQuestionsCount() {
        return questionRepository.count();
    }
}
