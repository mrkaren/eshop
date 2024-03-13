package am.itspace.eshop.controller;

import am.itspace.eshop.entity.Product;
import am.itspace.eshop.security.SpringUser;
import am.itspace.eshop.service.CategoryService;
import am.itspace.eshop.service.ProductPictureService;
import am.itspace.eshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ProductPictureService productPictureService;

    @GetMapping("/admin/home")
    public String adminHome(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "3", required = false) int size,
            ModelMap modelMap) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Product> productsPage = productService.findAll(pageable);
        modelMap.addAttribute("products", productsPage);

        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/home";
    }

    @GetMapping("/admin/product/add")
    public String addProductPage(ModelMap modelMap) {
        modelMap.addAttribute("categories", categoryService.findAll());
        return "admin/addProduct";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@AuthenticationPrincipal SpringUser springUser,
                             @ModelAttribute Product product,
                             @RequestParam("pics") List<MultipartFile> pics) {
        product.setUser(springUser.getUser());
        Product saved = productService.save(product);
        productPictureService.saveAll(saved, pics);
        return "redirect:/admin/home";
    }


}
