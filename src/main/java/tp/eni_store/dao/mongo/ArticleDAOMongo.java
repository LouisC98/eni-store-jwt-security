package tp.eni_store.dao.mongo;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.ArticleDAO;

import java.util.List;

@Profile("!test")
@Primary
@Repository
public class ArticleDAOMongo implements ArticleDAO {
    private final ArticleMongoRepository articleMongoRepository;

    public ArticleDAOMongo(ArticleMongoRepository articleMongoRepository) {
        this.articleMongoRepository = articleMongoRepository;
    }

    @Override
    public List<Article> selectAll() {
        return articleMongoRepository.findAll();
    }

    @Override
    public Article selectById(String id) {
        return articleMongoRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        articleMongoRepository.deleteById(id);
    }

    @Override
    public void save(Article article) {
        articleMongoRepository.save(article);
    }
}
