package com.springboot.mvc.validator;

import com.springboot.mvc.domain.Employee;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

// 自定义Validator,用于Employee的验证
@Component
public class EmployeeFormValidator implements Validator {

	// which objects can be validated by this validator
	@Override
	public boolean supports(Class<?> paramClass) {
		return Employee.class.equals(paramClass);
	}

	/**
	 * 实现validate(）方法并在任何字段验证失败时添加错误
	 *
	 * 一旦这个方法返回，spring框架就会将Errors对象绑定到我们在控制器处理程序方法中使用的BindingResult对象。
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		System.out.println("start to validate employee...");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.required");
		
		Employee emp = (Employee) obj;
		if(emp.getId() <=0){
			errors.rejectValue("id", "negativeValue", new Object[]{"'id'"}, "id can't be negative");
		}
		// Spring提供了org.springframework.validation.ValidationUtils实用程序类用于基本的验证，例如null或empty。
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
		//请注意，ValidationUtils.rejectIfEmptyOrWhitespace(）最后一个参数采用消息资源的key name。这样我们就可以向用户提供localized的错误消息。
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "role", "role.required");
	}
}
