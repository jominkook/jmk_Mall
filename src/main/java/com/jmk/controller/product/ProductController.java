package com.jmk.controller.product;

import com.jmk.controller.HomeController;
import com.jmk.service.category.CategoryService;
import com.jmk.service.product.ProductService;
import com.jmk.vo.category.Category;
import com.jmk.vo.member.Member;
import com.jmk.vo.product.Product;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    // 상품 등록 폼 (GET)
    @RequestMapping(value = "/admin/productRegister", method = RequestMethod.GET)
    public String productRegister(HttpSession session, Model model) throws Exception {
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("contentPage", "product/productRegister.jsp");
        return "main";
    }

    @RequestMapping(value = "/admin/productRegister", method = RequestMethod.POST)
    public String productRegisterPost(
            @ModelAttribute Product product,
            @RequestParam("uploadFile") MultipartFile productImage,
            HttpSession session,
            Model model) throws Exception {
        //System.out.println(product);
        // 이미지 저장
        String imageUrl = null;
        if (productImage != null && !productImage.isEmpty()) {
            // 실제 웹 프로젝트 내부 경로로 저장
            String uploadDir = "C:/upload/";

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + productImage.getOriginalFilename();
            File dest = new File(uploadDir, fileName);
            productImage.transferTo(dest);
            imageUrl = "/upload/" + fileName; // DB에는 이 경로 저장
        }

        product.setProductImage(imageUrl);
        productService.insertProduct(product);
        model.addAttribute("contentPage", "product/selectProduct.jsp");
        return "main";
    }

    @RequestMapping(value = "/productSelect", method = RequestMethod.GET)
    public String selectProduct(
            @RequestParam(value = "q", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model, HttpSession session) throws Exception {

        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        List<Product> productList;
        int totalCount;
        System.out.println("키워드" + keyword);
        if (keyword != null && !keyword.trim().isEmpty()) {
            productList = productService.searchProductPage(keyword, pageSize, offset);
            totalCount = productService.countProductByKeyword(keyword);
        } else {
            productList = productService.selectProductPage(pageSize, offset);
            totalCount = productService.countProduct();
        }

        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        Member member = (Member) session.getAttribute("member");

        model.addAttribute("member", member);
        model.addAttribute("productList", productList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("contentPage", "product/selectProduct.jsp");
        return "main";
    }

    // 수정 폼 이동
    @RequestMapping(value = "/admin/productUpdate", method = RequestMethod.GET)
    public String productUpdate(@RequestParam("productId") int productId, Model model) throws Exception {
        System.out.println("productId" + productId);
        Product product = productService.getProductById(productId);

        System.out.println("조회된 productId: " + product.getProductId());

        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("contentPage", "product/productUpdate.jsp");
        return "main";
    }

    // 수정 처리
    @RequestMapping(value = "/admin/productUpdate" ,method = RequestMethod.POST)
    public String productUpdatePost(
            @ModelAttribute Product product,
            @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
            HttpSession session,
            Model model
    ) throws Exception {

        System.out.println("수정 productId: " + product.getProductId());
        if (uploadFile != null && !uploadFile.isEmpty()) {
            String uploadDir = "C:/upload/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
            File dest = new File(uploadDir, fileName);
            uploadFile.transferTo(dest);

            product.setProductImage("/upload/" + fileName);
        }
        productService.updateProduct(product);
        return "redirect:/productSelect";
    }


    @RequestMapping(value = "/admin/productDelete" ,method = RequestMethod.POST)
    public String productDelete(@RequestParam("productId") int productId) throws Exception {
        productService.deleteProduct(productId);
        return "redirect:/productSelect";
    }
}