package edu.hit.testsheet.exception;

/**
 * ClassName:AnswerRecordNotFoundException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/22 21:37
 * @author:shyboy
 */
public class AnswerRecordNotFoundException extends RuntimeException {
    public AnswerRecordNotFoundException(String studentName, Long examId, Long questionId) {
        super("Answer record not found for student: " + studentName + ", examId: " + examId + ", questionId: " + questionId);
    }

    public AnswerRecordNotFoundException(String studentName, Long examId) {
        super("Answer record not found for student: " + studentName + ", examId: " + examId);
    }
}

