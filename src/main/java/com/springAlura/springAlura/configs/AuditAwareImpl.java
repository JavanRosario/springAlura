//import java.util.Optional;
//
//import org.apache.catalina.realm.JNDIRealm.User;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.data.domain.AuditorAware;
//
//public class AuditAwareImpl implements AuditorAware<String> {
//	@Override
//	public Optional<String> getCurrentAuditor() {
//		return Optional.ofNullable(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication)
//				.filter(Authentication::isAuthenticated).map(Authentication::getPrincipal).map(User.class::cast)
//				.map(User::getUsername);
//	}
//}