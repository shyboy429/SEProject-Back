package edu.hit.testsheet.service;

import edu.hit.testsheet.dto.ExamPaperDto;
import edu.hit.testsheet.dto.PaperReturnDto;
import edu.hit.testsheet.dto.PaperUpdateDto;
import edu.hit.testsheet.bean.Paper;
import edu.hit.testsheet.bean.Question;

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
    List<PaperReturnDto> getAllPapers(int offset, int pageSize);

    Paper addPaper(Paper paper);

    void deletePaperById(Long id);

    PaperReturnDto selectPaperById(Long id);

    Paper updatePaper(Long id, PaperUpdateDto paperUpdateDto);

    List<Question> selectPaperByIdForAdmin(Long id);

    List<Question> deleteQuestionInPaper(Long id, Long dqId);

    long getPapersCount();
    List<ExamPaperDto> getExamPaper(Long id);
}
