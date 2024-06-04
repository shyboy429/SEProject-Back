package edu.hit.testsheet.impl;

import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.repository.QuestionRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Question> searchQuestions(String keywords, String type, String difficultLevel,int pageIndex,int pageSize) {
        // 获取CriteriaBuilder实例，用于构建Criteria查询
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // 创建一个CriteriaQuery对象，指定返回结果类型为Question
        CriteriaQuery<Question> cq = cb.createQuery(Question.class);

        // 定义查询的根对象，表示查询的主体是Question
        Root<Question> question = cq.from(Question.class);

        // 创建一个Predicate列表，用于存放查询条件
        List<Predicate> predicates = new ArrayList<>();

        // 如果keywords不为空，添加模糊查询条件
        if (keywords != null && !keywords.isEmpty()) {
            // 去除首尾空格并将连续空格替换为单个空格
            String processedKeywords = keywords.trim().replaceAll("\\s+", " ");
            // 使用 cb.lower 将字段和关键词转换为小写，以便忽略大小写
            String lowerKeywords = "%" + processedKeywords.toLowerCase() + "%";
            predicates.add(cb.like(cb.lower(question.get("description")), lowerKeywords));
            System.out.println("Keywords Predicate: " + lowerKeywords); // 添加日志信息
        }

        // 如果type不为空，添加type相等查询条件
        if (type != null && !type.isEmpty()) {
            predicates.add(cb.equal(question.get("type"), type));
            System.out.println("Type Predicate: " + type); // 添加日志信息
        }

        // 如果difficultLevel不为空，添加difficultLevel相等查询条件
        if (difficultLevel != null && !difficultLevel.isEmpty()) {
            predicates.add(cb.equal(question.get("difficultLevel"), difficultLevel));
            System.out.println("Difficult Level Predicate: " + difficultLevel); // 添加日志信息
        }

        // 将所有条件合并到CriteriaQuery中
        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        } else {
            System.out.println("No predicates applied"); // 添加日志信息
        }

        // 创建查询对象
        TypedQuery<Question> query = entityManager.createQuery(cq);

        // 设置分页参数
        query.setFirstResult(pageIndex * pageSize); // 设置起始位置
        query.setMaxResults(pageSize); // 设置每页的大小

        // 执行查询并返回结果
        List<Question> results = query.getResultList();
        System.out.println("Results: " + results); // 添加日志信息
        return results;
    }
}




