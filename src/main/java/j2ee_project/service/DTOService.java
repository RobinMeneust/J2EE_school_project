package j2ee_project.service;

import j2ee_project.dto.AddressDTO;
import j2ee_project.dto.ContactDTO;
import j2ee_project.dto.UserDTO;
import j2ee_project.dto.catalog.CategoryDTO;
import j2ee_project.dto.catalog.ProductDTO;
import j2ee_project.dto.discount.DiscountDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Manages DTO classes
 */
public class DTOService {
	/**
	 * Validate a user data transfer object
	 *
	 * @param userDTO the dto to validate
	 * @return a map with error message
	 */
	public static Map<String, String> userDataValidation(UserDTO userDTO){
		Map<String, String> violationsMap = dataValidation(userDTO);
		if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
			violationsMap.put("confirmPassword", "Password and Confirm Password must match.");
		}
		return violationsMap;
	}


	/**
	 * Validate a contact data transfer object
	 *
	 * @param contactDTO the dto to validate
	 * @return a map with error message
	 */
	public static Map<String, String> contactDataValidation(ContactDTO contactDTO){
		return dataValidation(contactDTO);
	}

	/**
	 * Validate an address data transfer object
	 *
	 * @param addressDTO the address dto
	 * @return a map with error message
	 */
	public static Map<String, String> addressDataValidation(AddressDTO addressDTO){
		return dataValidation(addressDTO);
	}

	/**
	 * Validate a discount data transfer object
	 *
	 * @param discountDTO the address dto
	 * @return a map with error message
	 */
	public static Map<String, String> discountDataValidation(DiscountDTO discountDTO){
		return dataValidation(discountDTO);
	}

	/**
	 * Validate a product data transfer object
	 *
	 * @param productDTO the address dto
	 * @return a map with error message
	 */
	public static Map<String, String> productDataValidation(ProductDTO productDTO){
		return dataValidation(productDTO);
	}

	/**
	 * Validate a category data transfer object
	 *
	 * @param categoryDTO the address dto
	 * @return a map with error message
	 */
	public static Map<String, String> categoryDataValidation(CategoryDTO categoryDTO){
		return dataValidation(categoryDTO);
	}

	/**
	 * Validate an address data transfer object whose type is unknown (E)
	 *
	 * @param objectTested the dto
	 * @return a map with error message
	 * @param <E> Type of the DTO
	 */
	private static<E> Map<String, String> dataValidation(E objectTested) {
		Map<String, String> violationsMap = new HashMap<>();
		try {
			ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
			Validator validator = validatorFactory.getValidator();
			Set<ConstraintViolation<E>> violations = validator.validate(objectTested);
			for (ConstraintViolation<E> violation : violations) {
				violationsMap.put(violation.getPropertyPath().toString(), violation.getMessage());
			}
			validatorFactory.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return violationsMap;
	}
}
