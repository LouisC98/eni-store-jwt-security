package tp.eni_store.dao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import tp.eni_store.bo.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Profile("test")
@Repository
public class ArticleDAOMock implements ArticleDAO {
    private List<Article> articles;

    public ArticleDAOMock() {
        this.articles = new ArrayList<>(List.of(
                new Article("1", "Article 1"),
                new Article("2", "Article 2")
        ));
    }

    public List<Article> selectAll() {
        return articles;
    }

    public Article selectById(String id) {
        return articles.stream().filter(a -> Objects.equals(a.getId(), id)).findFirst().orElse(null);
    }

    public void deleteById(String id) {
        articles.stream().filter(a -> Objects.equals(a.getId(), id)).findFirst().ifPresent(article -> articles.remove(article));
    }

    public void save(Article article) {
        Article foundArticle = articles.stream().filter(a -> Objects.equals(a.getId(), article.getId())).findFirst().orElse(null);

        if (foundArticle != null) {
            foundArticle.setTitle(article.getTitle());
        } else {
            articles.add(article);
        }
    }
}
