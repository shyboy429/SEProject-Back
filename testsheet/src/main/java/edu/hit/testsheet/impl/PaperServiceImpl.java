package edu.hit.testsheet.impl;

import edu.hit.testsheet.Dto.PaperReturnDto;
import edu.hit.testsheet.Dto.PaperUpdateDto;
import edu.hit.testsheet.Exception.PaperNotFoundException;
import edu.hit.testsheet.Exception.QuestionNotFoundException;
import edu.hit.testsheet.bean.Paper;
import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.repository.PaperRepository;
import edu.hit.testsheet.service.PaperService;
import edu.hit.testsheet.service.QuestionService;
import edu.hit.testsheet.util.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private QuestionService questionService;

    @Override
    public List<PaperReturnDto> getAllPapers() {
        List<Paper> allPapers = paperRepository.findAll();
        List<PaperReturnDto> ret = new ArrayList<>();
        for(Paper p : allPapers){
            PaperReturnDto pret = convertPaperToDto(p);
            ret.add(pret);
        }
        return ret;
    }

    private PaperReturnDto convertPaperToDto(Paper paper) {
        PaperReturnDto pret = new PaperReturnDto();
        pret.setId(paper.getId());
        pret.setTitle(paper.getTitle());
        pret.setIntroduction(paper.getIntroduction());
        pret.setCreateTime(paper.getCreateTime());
        pret.setUpdateTime(paper.getUpdateTime());
        pret.setCreatedBy(paper.getCreatedBy());
        String content = paper.getContent();
        String[] questionIds = content.split(" ");
        StringBuilder specificContent = new StringBuilder();
        for (int i = 0; i < questionIds.length; i++) {
            String idStr = questionIds[i];
            try {
                Long questionId = Long.parseLong(idStr);
                Question question = questionService.selectQuestionById(questionId);
                specificContent.append("第").append(i + 1).append("题: ").append(question.getDescription()).append("\n");
            } catch (NumberFormatException e) {
                System.err.println("Invalid question ID format: " + idStr);
            } catch (QuestionNotFoundException e) {
                System.err.println("Question not found for ID: " + idStr);
            }
        }
        pret.setSpecificContent(specificContent.toString().trim());
        return pret;
    }

    @Override
    public Paper addPaper(Paper paper) {
        paper.setCreateTime(DateFormatter.formatDate(LocalDateTime.now()));
        paper.setUpdateTime(paper.getCreateTime());
        return paperRepository.save(paper);
    }

    @Override
    public void deletePaperById(Long id) {
        if (paperRepository.existsById(id)) {
            paperRepository.deleteById(id);
        } else {
            throw new PaperNotFoundException(id);
        }
    }

    @Override
    public PaperReturnDto selectPaperById(Long id) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new PaperNotFoundException(id));
        return convertPaperToDto(paper);
    }

    @Override
    public Paper updatePaper(Long id, PaperUpdateDto paperUpdateDto) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new PaperNotFoundException(id));
        paper.setTitle(paperUpdateDto.getTitle());
        paper.setContent(paperUpdateDto.getContent());
        paper.setIntroduction(paperUpdateDto.getIntroduction());
        paper.setUpdateTime(DateFormatter.formatDate(LocalDateTime.now()));
        return paperRepository.save(paper);
    }
}

