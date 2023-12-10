package j2ee_project.controller.catalog.product;

import j2ee_project.dao.catalog.category.CategoryDAO;
import j2ee_project.dao.catalog.product.ProductDAO;
import j2ee_project.dto.catalog.ProductDTO;
import j2ee_project.model.catalog.Category;
import j2ee_project.model.catalog.Product;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import j2ee_project.service.DTOService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Map;

import static j2ee_project.dao.user.PermissionDAO.getPermission;

/**
 * This class is a servlet used to edit a product. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/edit-product")
public class EditProductController extends HttpServlet {
    /**
     * Get the page to edit a product
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object obj = session.getAttribute("user");
            if (obj instanceof Moderator moderator
                    && moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_PRODUCT))) {

                String productIdStr = request.getParameter("id");
                int productId = -1;

                if (productIdStr != null && !productIdStr.trim().isEmpty()) {
                    try {
                        productId = Integer.parseInt(productIdStr);
                    } catch (Exception ignore) {
                    }
                }

                if (productId <= 0) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID must be positive");
                }

                Product product = ProductDAO.getProduct(productId);
                request.setAttribute("product", product);

                request.setAttribute("categories", CategoryDAO.getCategories());

                RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/edit/editProduct.jsp");
                view.forward(request, response);
            } else {
                response.sendRedirect("dashboard");
            }
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Edit a product to the DB
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String productIdStr = request.getParameter("id");
        int productId = -1;

        if (productIdStr != null && !productIdStr.trim().isEmpty()) {
            try {
                productId = Integer.parseInt(productIdStr);
            } catch (Exception ignore) {
            }
        }

        if (productId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID must be positive");
        }

        Product product = ProductDAO.getProduct(productId);


        try {

            int categoryId = Integer.parseInt(request.getParameter("category"));
            Category category = CategoryDAO.getCategory(categoryId);

            String weightStr = request.getParameter("weight");
            Float weight = null;
            if (weightStr != null && !weightStr.isEmpty()) {
                weight = Float.valueOf(weightStr);
            }

            String imagePath = request.getParameter("imagePath");

            ProductDTO productDTO = new ProductDTO(
                    request.getParameter("name"),
                    Integer.parseInt(request.getParameter("stockQuantity")),
                    Float.parseFloat(request.getParameter("unitPrice")),
                    request.getParameter("description"),
                    imagePath,
                    weight,
                    category
            );



            Map<String, String> inputErrors = DTOService.productDataValidation(productDTO);

            String errorDestination = "WEB-INF/views/dashboard/add/addProduct.jsp";
            RequestDispatcher dispatcher = null;

            if (inputErrors.isEmpty()) {
                try {

                    if (productDTO.getName() != null && !productDTO.getName().isEmpty()) {
                        product.setName(productDTO.getName());
                    }
                    if (productDTO.getStockQuantity() != null) {
                        product.setStockQuantity(productDTO.getStockQuantity());
                    }
                    product.setUnitPrice(productDTO.getUnitPrice());
                    if (productDTO.getDescription() != null && !productDTO.getDescription().isEmpty()) {
                        product.setDescription(productDTO.getDescription());
                    }
                    if (productDTO.getWeight() != null) {
                        product.setWeight(productDTO.getWeight());
                    }
                    if (productDTO.getCategory() != null) {
                        product.setCategory(productDTO.getCategory());
                    }

                    ProductDAO.updateProduct(product);
                    ProductDAO.setProductImagePath(product.getId(), productDTO.getImagePath());
                    response.sendRedirect("dashboard?tab=products");
                } catch (Exception exception) {
                    System.err.println(exception.getMessage());
                    request.setAttribute("ModificationProcessError", "Error during modification process");
                    dispatcher = request.getRequestDispatcher(errorDestination);
                    dispatcher.include(request, response);
                }
            } else {
                request.setAttribute("InputError", inputErrors);
                dispatcher = request.getRequestDispatcher(errorDestination);
                dispatcher.include(request, response);
            }

            if (dispatcher != null) doGet(request, response);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}