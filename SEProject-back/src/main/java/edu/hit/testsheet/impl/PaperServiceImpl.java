package edu.hit.testsheet.impl;

import edu.hit.testsheet.dto.ExamPaperDto;
import edu.hit.testsheet.dto.PaperReturnDto;
import edu.hit.testsheet.dto.PaperUpdateDto;
import edu.hit.testsheet.exception.*;
import edu.hit.testsheet.bean.Exam;
import edu.hit.testsheet.bean.Paper;
import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.repository.ExamRepository;
import edu.hit.testsheet.repository.PaperRepository;
import edu.hit.testsheet.repository.QuestionRepository;
import edu.hit.testsheet.service.PaperService;
import edu.hit.testsheet.util.DateFormatterUtil;
import edu.hit.testsheet.util.PaperToDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:PaperServiceImpl
 * Package:edu.hit.testsheet.impl
 * Description:
 *
 * @date:2024/6/3 11:48
 * @author:shyboy
 */

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private PaperToDtoUtil paperToDtoUtil;
    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired 
    private ExamRepository examRepository;

    @Override
    public List<PaperReturnDto> getAllPapers(int pageIndex, int pageSize) {
        List<PaperReturnDto> ret = new ArrayList<>();
        if(pageIndex == -2){
            List<Paper> allPapers = paperRepository.findAll();
            for (Paper p : allPapers) {
                PaperReturnDto pret = paperToDtoUtil.convertPaperToDto(p);
                ret.add(pret);
            }
            return ret;
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Paper> paperPage = paperRepository.findAll(pageable);
        List<Paper> allPapers = paperPage.getContent();
        
        for (Paper p : allPapers) {
            PaperReturnDto pret = paperToDtoUtil.convertPaperToDto(p);
            ret.add(pret);
        }
        return ret;
    }

    @Override
    public Paper addPaper(Paper paper) {
        if (paper.getContent() == null || paper.getContent().isEmpty()) {
            throw new InvalidPaperException("试题篮中没有题目！");
        }
        if (paper.getTitle() == null || paper.getTitle().isEmpty()) {
            throw new InvalidPaperException("试卷名称不可为空！");
        }
        paper.setCreateTime(DateFormatterUtil.formatDate(LocalDateTime.now()));
        paper.setUpdateTime(paper.getCreateTime());
        return paperRepository.save(paper);
    }

    @Override
    public void deletePaperById(Long id) {
        if (paperRepository.existsById(id)) {
            if (examRepository.existsByPaperId(id)) {
                List<Exam> exams = examRepository.findByPaperId(id);
                String examNames = exams.stream()
                        .map(Exam::getName)
                        .collect(Collectors.joining(", "));
                throw new PaperCanNotBeDeletedException("考试：\"" + examNames + "\" 使用当前试卷，不可删除！");
            }
            paperRepository.deleteById(id);
        } else {
            throw new PaperNotFoundException(id);
        }
    }

    @Override
    public PaperReturnDto selectPaperById(Long id) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new PaperNotFoundException(id));
        return paperToDtoUtil.convertPaperToDto(paper);
    }

    @Override
    public Paper updatePaper(Long id, PaperUpdateDto paperUpdateDto) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new PaperNotFoundException(id));
        paper.setTitle(paperUpdateDto.getTitle());
        paper.setContent(paperUpdateDto.getContent());
        paper.setIntroduction(paperUpdateDto.getIntroduction());
        paper.setUpdateTime(DateFormatterUtil.formatDate(LocalDateTime.now()));
        return paperRepository.save(paper);
    }

    @Override
    public List<Question> selectPaperByIdForAdmin(Long id) {
        List<Question> ret = new ArrayList<>();
        Paper paper = paperRepository.findById(id).orElseThrow(() -> new PaperNotFoundException(id));
        String content = paper.getContent();
        if ("".equals(content)) {
            throw new QuestionNotFoundException(-1L);
        }
        String[] questionIds = content.split(" ");
        for (String qid : questionIds) {
            long lqid = Long.parseLong(qid);
            Question question = questionRepository.findById(lqid).orElse(null);
            if (question != null) {
                ret.add(question);
            }
        }
        return ret;
    }

    @Override
    public List<Question> deleteQuestionInPaper(Long id, Long dqId) {
        Paper paper = paperRepository.findById(id).orElseThrow(() -> new PaperNotFoundException(id));
        String content = paper.getContent();

        // 拆分字符串并找到等于 dqId 的元素
        String[] questionIds = content.split(" ");
        if(questionIds.length == 1){
            throw new DeleteLastQuestionInPaperException("该题是当前试卷的最后一题。如果需要，请直接删除试卷。");
        }
        StringBuilder updatedContent = new StringBuilder();

        for (String questionId : questionIds) {
            if (!questionId.equals(dqId.toString())) {
                if (updatedContent.length() > 0) {
                    updatedContent.append(" ");
                }
                updatedContent.append(questionId);
            }
        }
        // 更新 oldPaper 的 content
        paper.setContent(updatedContent.toString());
        paper.setUpdateTime(DateFormatterUtil.formatDate(LocalDateTime.now()));
        paperRepository.save(paper);
        return selectPaperByIdForAdmin(id);
    }

    @Override
    public long getPapersCount() {
        return paperRepository.count();
    }

    @Override
    public List<ExamPaperDto> getExamPaper(Long id) {
        List<Question> questions = selectPaperByIdForAdmin(id);
        List<ExamPaperDto> ret = new ArrayList<>();
        // 排序顺序：选择题 -> 判断题 -> 填空题 -> 问答题
        List<Question> sortedQuestions = questions.stream()
                .sorted(Comparator.comparingInt(q -> switch (q.getType()) {
                    case "选择题" -> 1;case "判断题" -> 2;
                    case "填空题" -> 3;case "问答题" -> 4;default -> 5;
                })).toList();
        for (Question question : sortedQuestions) {
            ExamPaperDto dto = new ExamPaperDto();
            dto.setDescription(question.getDescription());
            dto.setId(question.getId());
            dto.setType(question.getType());
            ret.add(dto);
        }
        return ret;
    }
}
