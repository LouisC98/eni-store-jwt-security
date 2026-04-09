package tp.eni_store.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import tp.eni_store.bo.Article;

public interface ArticleMongoRepository extends MongoRepository<Article, String> {}
