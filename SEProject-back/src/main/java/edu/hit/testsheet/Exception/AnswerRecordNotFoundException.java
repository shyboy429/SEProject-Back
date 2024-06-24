package edu.hit.testsheet.Exception;

/**
 * ClassName:AnswerRecordNotFoundException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/22 21:37
 * @author:shyboy
 */
public class AnswerRecordNotFoundException extends RuntimeException {
    public AnswerRecordNotFoundException(Long id) {
        super("Answer record not found with id: " + id);
    }

    public AnswerRecordNotFoundException(String studentName) {
        super("Answer record not found for student: " + studentName);
    }
    public AnswerRecordNotFoundException(String studentName, Long examId, Long questionId) {
        super("Answer record not found for student: " + studentName + ", examId: " + examId + ", questionId: " + questionId);
    }

    public AnswerRecordNotFoundException(String studentName, Long examId) {
        super("Answer record not found for student: " + studentName + ", examId: " + examId);
    }
}

