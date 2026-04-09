package tp.eni_store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tp.eni_store.bo.Article;
import tp.eni_store.response.ApiResponse;
import tp.eni_store.service.ArticleService;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    void getAll_shouldReturnSuccess() {
        ApiResponse<List<Article>> response = articleService.getAll();

        Assertions.assertEquals(202, response.getCode());
        Assertions.assertNotNull(response.getData());
    }

    @Test
    void getById_shouldReturnArticle_whenExists() {
        ApiResponse<Article> response = articleService.getById("1");

        Assertions.assertEquals(202, response.getCode());
        Assertions.assertNotNull(response.getData());
    }

    @Test
    void getById_shouldReturnNotFound_whenNotExists() {
        ApiResponse<Article> response = articleService.getById("999");

        Assertions.assertEquals(703, response.getCode());
        Assertions.assertNull(response.getData());
    }

    @Test
    void deleteById_shouldReturnSuccess_whenExists() {
        ApiResponse<Article> response = articleService.getById("1");

        Assertions.assertEquals(202, response.getCode());
    }

    @Test
    void deleteById_shouldReturnSuccess_whenNotExists() {
        ApiResponse<Article> response = articleService.getById("999");

        Assertions.assertEquals(703, response.getCode());
    }

    @Test
    void save_shouldReturnCreated_whenNewArticle() {
        Article article = new Article(null, "Nouvel article");
        ApiResponse<Article> response = articleService.save(article);

        Assertions.assertEquals(202, response.getCode());
        Assertions.assertNotNull(response.getData());
    }

    @Test
    void  save_shouldReturnUpdated_whenExistingArticle() {
        Article article = new Article("1", "Article modifié");
        ApiResponse<Article> response = articleService.save(article);

        Assertions.assertEquals(203, response.getCode());
        Assertions.assertNotNull(response.getData());
    }
}
