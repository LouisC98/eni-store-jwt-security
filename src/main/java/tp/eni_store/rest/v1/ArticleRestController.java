package tp.eni_store.rest.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.eni_store.bo.Article;
import tp.eni_store.response.ApiResponse;
import tp.eni_store.security.IsGranted;
import tp.eni_store.service.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {

    private final ArticleService articleService;

    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @IsGranted(roles = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    public ResponseEntity<ApiResponse<List<Article>>> getAll() {
        ApiResponse<List<Article>> response = articleService.getAll();
        return ResponseEntity.ok(response);
    }

    @IsGranted(roles = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Article>> getById(@PathVariable String id) {
        ApiResponse<Article> response = articleService.getById(id);
        if (response.getCode() == 703) {
            return ResponseEntity.status(404).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @IsGranted(roles = {"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable String id) {
        ApiResponse<Void> response = articleService.deleteById(id);
        if (response.getCode() == 703) {
            return ResponseEntity.status(404).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @IsGranted(roles = {"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Article>> save(@RequestBody Article article) {
        ApiResponse<Article> response = articleService.save(article);
        if (response.getCode() == 202) {
            return ResponseEntity.status(201).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
