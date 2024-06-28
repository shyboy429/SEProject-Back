package edu.hit.testsheet.impl;

/**
 * ClassName:QuestionRepositoryImplTest
 * Package:edu.hit.testsheet.impl
 * Description:
 *
 * @date:2024/6/28 15:11
 * @author:shyboy
 */

import edu.hit.testsheet.TestsheetApplication;
import edu.hit.testsheet.bean.Question;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ActiveProfiles(value = "test")
@SpringBootTest(classes = TestsheetApplication.class)
@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
public class QuestionRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private QuestionRepositoryImpl questionRepository;

    private CriteriaBuilder cb;
    private CriteriaQuery<Question> cq;
    private Root<Question> root;
    private TypedQuery<Question> query;

    @BeforeEach
    public void setUp() {
        cb = mock(CriteriaBuilder.class);
        cq = mock(CriteriaQuery.class);
        root = mock(Root.class);
        query = mock(TypedQuery.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Question.class)).thenReturn(cq);
        when(cq.from(Question.class)).thenReturn(root);
        when(entityManager.createQuery(cq)).thenReturn(query);
    }

    @Test
    public void testSearchQuestionsWithAllParameters() {
        List<Question> expectedQuestions = Arrays.asList(new Question(), new Question());
        when(query.getResultList()).thenReturn(expectedQuestions);

        List<Question> questions = questionRepository.searchQuestions("test", "选择题", "easy", "user", 0, 10, "type", "ASC");

        verify(query).setFirstResult(0);
        verify(query).setMaxResults(10);
        assertEquals(expectedQuestions, questions);
    }

    @Test
    public void testSearchQuestionsWithNoKeywords() {
        List<Question> expectedQuestions = Arrays.asList(new Question(), new Question());
        when(query.getResultList()).thenReturn(expectedQuestions);

        List<Question> questions = questionRepository.searchQuestions(null, "选择题", "easy", "user", 0, 10, "type", "ASC");

        verify(query).setFirstResult(0);
        verify(query).setMaxResults(10);
        assertEquals(expectedQuestions, questions);
    }

    @Test
    public void testSearchQuestionsWithNoType() {
        List<Question> expectedQuestions = Arrays.asList(new Question(), new Question());
        when(query.getResultList()).thenReturn(expectedQuestions);

        List<Question> questions = questionRepository.searchQuestions("test", null, "easy", "user", 0, 10, "type", "ASC");

        verify(query).setFirstResult(0);
        verify(query).setMaxResults(10);
        assertEquals(expectedQuestions, questions);
    }

    @Test
    public void testSearchQuestionsWithNoDifficultLevel() {
        List<Question> expectedQuestions = Arrays.asList(new Question(), new Question());
        when(query.getResultList()).thenReturn(expectedQuestions);

        List<Question> questions = questionRepository.searchQuestions("test", "选择题", null, "user", 0, 10, "type", "ASC");

        verify(query).setFirstResult(0);
        verify(query).setMaxResults(10);
        assertEquals(expectedQuestions, questions);
    }

    @Test
    public void testSearchQuestionsWithNoUsername() {
        List<Question> expectedQuestions = Arrays.asList(new Question(), new Question());
        when(query.getResultList()).thenReturn(expectedQuestions);

        List<Question> questions = questionRepository.searchQuestions("test", "选择题", "easy", null, 0, 10, "type", "ASC");

        verify(query).setFirstResult(0);
        verify(query).setMaxResults(10);
        assertEquals(expectedQuestions, questions);
    }

    @Test
    public void testSearchQuestionsWithNoPagination() {
        List<Question> expectedQuestions = Arrays.asList(new Question(), new Question());
        when(query.getResultList()).thenReturn(expectedQuestions);

        List<Question> questions = questionRepository.searchQuestions("test", "选择题", "easy", "user", -1, 10, "type", "ASC");

        verify(query, never()).setFirstResult(anyInt());
        verify(query, never()).setMaxResults(anyInt());
        assertEquals(expectedQuestions, questions);
    }

    @Test
    public void testSearchQuestionsWithNoSorting() {
        List<Question> expectedQuestions = Arrays.asList(new Question(), new Question());
        when(query.getResultList()).thenReturn(expectedQuestions);

        List<Question> questions = questionRepository.searchQuestions("test", "选择题", "easy", "user", 0, 10, null, "ASC");

        verify(query).setFirstResult(0);
        verify(query).setMaxResults(10);
        assertEquals(expectedQuestions, questions);
    }

    @Test
    public void testSearchQuestionsWithDescendingOrder() {
        List<Question> expectedQuestions = Arrays.asList(new Question(), new Question());
        when(query.getResultList()).thenReturn(expectedQuestions);

        List<Question> questions = questionRepository.searchQuestions("test", "选择题", "easy", "user", 0, 10, "type", "DESC");

        verify(query).setFirstResult(0);
        verify(query).setMaxResults(10);
        assertEquals(expectedQuestions, questions);
    }

    @Test
    public void testSearchQuestionsWithNoPredicates() {
        List<Question> expectedQuestions = Arrays.asList(new Question(), new Question());
        when(query.getResultList()).thenReturn(expectedQuestions);

        List<Question> questions = questionRepository.searchQuestions(null, null, null, null, 0, 10, "type", "ASC");

        verify(query).setFirstResult(0);
        verify(query).setMaxResults(10);
        assertEquals(expectedQuestions, questions);
    }
}

