package edu.hit.testsheet.repository;

import edu.hit.testsheet.bean.Question;

import java.util.List;

/**
 * ClassName:QuestionCustomRepository
 * Package:edu.hit.testsheet.repository
 * Description:
 *
 * @date:2024/6/4 10:31
 * @author:shyboy
 */

public interface QuestionRepositoryCustom {
    List<Question> searchQuestions(String keywords, String type, String difficultLevel,String username, int pageIndex,int pageSize);
}
