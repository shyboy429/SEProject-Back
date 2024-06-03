package edu.hit.testsheet.impl;

import edu.hit.testsheet.Dto.PaperUpdateDto;
import edu.hit.testsheet.Exception.PaperNotFoundException;
import edu.hit.testsheet.bean.Paper;
import edu.hit.testsheet.repository.PaperRepository;
import edu.hit.testsheet.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Paper> getAllPapers() {
        return paperRepository.findAll();
    }

    @Override
    public Paper addPaper(Paper paper) {
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
    public Paper selectPaperById(Long id) {
        return paperRepository.findById(id)
                .orElseThrow(() -> new PaperNotFoundException(id));
    }

    @Override
    public Paper updatePaper(Long id, PaperUpdateDto paperUpdateDTO) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new PaperNotFoundException(id));
        paper.setTitle(paperUpdateDTO.getTitle());
        paper.setQ1(paperUpdateDTO.getQ1());
        paper.setQ2(paperUpdateDTO.getQ2());
        paper.setQ3(paperUpdateDTO.getQ3());
        paper.setQ4(paperUpdateDTO.getQ4());
        paper.setQ5(paperUpdateDTO.getQ5());
        return paperRepository.save(paper);
    }
}

