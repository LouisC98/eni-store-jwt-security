package tp.eni_store.dao;

import tp.eni_store.bo.Article;

import java.util.List;

public interface ArticleDAO {
    List<Article> selectAll();
    Article selectById(String id);
    void deleteById(String id);
    void save(Article article);
}
