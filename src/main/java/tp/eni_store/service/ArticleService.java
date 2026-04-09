package tp.eni_store.service;

import org.springframework.stereotype.Service;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.ArticleDAO;
import tp.eni_store.helper.LocaleHelper;
import tp.eni_store.response.ApiResponse;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleDAO articleDAO;
    private final LocaleHelper localeHelper;

    public ArticleService(ArticleDAO articleDAO, LocaleHelper localeHelper) {
        this.articleDAO = articleDAO;
        this.localeHelper = localeHelper;
    }

    public ApiResponse<List<Article>> getAll() {
        return new ApiResponse<>(202, localeHelper.getMessage("article.getall.success"), articleDAO.selectAll());
    }

    public ApiResponse<Article> getById(String id) {
        Article article = articleDAO.selectById(id);

        if (article != null) {
            return new ApiResponse<>(202, localeHelper.getMessage("article.getbyid.success"), article);
        } else {
            return new ApiResponse<>(703, localeHelper.getMessage("article.notfound"), null);
        }
    }

    public ApiResponse<Void> deleteById(String id) {
        Article article = articleDAO.selectById(id);
        if (article != null) {
            articleDAO.deleteById(article.getId());
            return new ApiResponse<>(202, localeHelper.getMessage("article.delete.success"), null);
        }
        return new ApiResponse<>(703, localeHelper.getMessage("article.notfound"), null);
    }

    public ApiResponse<Article> save(Article article) {
        Article foundArticle = articleDAO.selectById(article.getId());

        if (foundArticle != null) {
            foundArticle.setTitle(article.getTitle());
            articleDAO.save(foundArticle);
            return new ApiResponse<>(203, localeHelper.getMessage("article.save.updated"), foundArticle);
        }
        article.setId(null);
        articleDAO.save(article);
        return new ApiResponse<>(202, localeHelper.getMessage("article.save.created"), article);
    }
}
