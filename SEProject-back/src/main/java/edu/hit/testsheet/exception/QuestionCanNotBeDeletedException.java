package edu.hit.testsheet.exception;

/**
 * ClassName:QuestionCanNotBeDeleteException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/27 20:18
 * @author:shyboy
 */
public class QuestionCanNotBeDeletedException extends RuntimeException{
    public QuestionCanNotBeDeletedException(Long id,String title) {
        super("该问题不可被删除, 它包含在在试卷: \"" + title + "\" 中！");
    }
}
