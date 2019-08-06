package com.springboot.mvc.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// 实现JSR-303规范的javax.validation.ConstraintValidator接口。
public class PhoneValidator implements ConstraintValidator<PhoneNumberConstraint, String> {

	// 如果我们使用一些资源，比如DataSource，我们可以在initialize(）方法中初始化它们。
	@Override
	public void initialize(PhoneNumberConstraint paramA) {
	}

	// 验证方法是isValid，如果数据有效则返回true，否则返回false。
	@Override
	public boolean isValid(String phoneNo, ConstraintValidatorContext ctx) {
		if(phoneNo == null){
			return false;
		}
		System.out.println("check phone!!");
		//validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
        //validating phone number with -, . or spaces
        else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
        //validating phone number with extension length from 3 to 5
        else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
        //validating phone number where area code is in braces ()
        else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
        //return false if nothing matches the input
        else return false;
	}

}
