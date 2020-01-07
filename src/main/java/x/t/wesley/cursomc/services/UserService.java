package x.t.wesley.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import x.t.wesley.cursomc.security.UserSS;

public class UserService {

	public static UserSS authenticated() {

		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
