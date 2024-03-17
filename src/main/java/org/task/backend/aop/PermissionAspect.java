package org.task.backend.aop;

import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.task.backend.annotation.Permission;
import org.task.backend.config.LoginThreadLocal;
import org.task.backend.exception.ForbiddenTargetException;
import org.task.backend.exception.UnauthorizedException;
import org.task.backend.model.entity.LoginUser;
import org.task.backend.service.UserService;

import java.util.List;


@Aspect
@Component
public class PermissionAspect {


	@Pointcut("@annotation(org.task.backend.annotation.Permission)")
	private void permissionCheck() {
	}

	@Resource
	private UserService userService;

	@Around("@annotation(permission) && permissionCheck()")
	public Object permissionCheck(ProceedingJoinPoint point, Permission permission) throws Throwable {
		String[] permissions = permission.value();
		LoginUser loginUser = LoginThreadLocal.get();
		String token = loginUser.getToken();
		if (token == null) {
			throw new UnauthorizedException();
		}
		String role = userService.getRole(token);
		if (role.equals("管理员")) {
            return point.proceed();
        }
		boolean intercept = true;
		for (String s : permissions) {
			if (role.equals(s)) {
                intercept = false;
                break;
            }
		}
		if (intercept) {
            throw new ForbiddenTargetException();
        }
		return point.proceed();
	}

	@AfterThrowing(throwing = "throwable", pointcut = "permissionCheck()")
	public void afterThrowing(Throwable throwable) throws Throwable {
		throw throwable;
	}


}
