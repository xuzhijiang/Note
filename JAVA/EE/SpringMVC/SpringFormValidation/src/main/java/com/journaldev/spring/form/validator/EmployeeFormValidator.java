package com.journaldev.spring.form.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.journaldev.spring.form.model.Employee;

// EmployeeFormValidator是特定于Spring Framework的验证器实现。 Spring Framework的supports（）方法实现，以了解可以使用此验证的对象。
//
//我们实现validate（）方法并在任何字段验证失败时添加错误。 Spring为基本验证提供了org.springframework.validation.ValidationUtils实用程序类，例如null或empty。 一旦这个方法返回，spring框架就会将Errors对象绑定到我们在控制器处理程序方法中使用的BindingResult对象。
//
//请注意，ValidationUtils.rejectIfEmptyOrWhitespace（）最后一个参数采用消息资源的密钥名称。 这样我们就可以向用户提供本地化的错误消息。 有关Spring中i18n的更多信息，请阅读Spring i18n示例。
public class EmployeeFormValidator implements Validator {

	//which objects can be validated by this validator
	@Override
	public boolean supports(Class<?> paramClass) {
		return Employee.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.required");
		
		Employee emp = (Employee) obj;
		if(emp.getId() <=0){
			errors.rejectValue("id", "negativeValue", new Object[]{"'id'"}, "id can't be negative");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "role", "role.required");
	}
}
