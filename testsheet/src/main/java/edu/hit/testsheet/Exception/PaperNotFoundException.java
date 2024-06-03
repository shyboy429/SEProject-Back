package edu.hit.testsheet.Exception;

/**
 * ClassName:PaperNotFoundException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/3 11:50
 * @author:shyboy
 */
public class PaperNotFoundException extends RuntimeException {
    public PaperNotFoundException(Long id) {
        super("Paper not found with id: " + id);
    }
}

