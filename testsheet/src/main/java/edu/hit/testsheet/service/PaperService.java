package edu.hit.testsheet.service;

import edu.hit.testsheet.Dto.PaperUpdateDto;
import edu.hit.testsheet.bean.Paper;

import java.util.List;

/**
 * ClassName:PaperRepository
 * Package:edu.hit.testsheet.service
 * Description:
 *
 * @date:2024/6/3 11:44
 * @author:shyboy
 */
public interface PaperService {
    List<Paper> getAllPapers();

    Paper addPaper(Paper paper);

    void deletePaperById(Long id);

    Paper selectPaperById(Long id);

    Paper updatePaper(Long id, PaperUpdateDto paperUpdateDto);
}
