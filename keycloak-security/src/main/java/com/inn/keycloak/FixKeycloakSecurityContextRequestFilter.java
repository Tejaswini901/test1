package com.inn.keycloak;


import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.AdapterDeploymentContext;
import org.keycloak.adapters.AdapterTokenStore;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.facade.SimpleHttpFacade;
import org.keycloak.adapters.springsecurity.token.AdapterTokenStoreFactory;
import org.keycloak.adapters.springsecurity.token.SpringSecurityAdapterTokenStoreFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter to fix Keycloak {@link RefreshableKeycloakSecurityContext} instances that lack transient fields "deployment"
 * and "tokenStore" due to deserialization. This is a workaround for a bug in the Keycloak Spring Security adapter and
 * should be obsolete once the bug is fixed upstream.
 *
 */
@Component
public class FixKeycloakSecurityContextRequestFilter extends GenericFilterBean {

    private static final String FILTER_APPLIED = KeycloakSecurityContext.class.getPackage().getName() + ".token-refreshed";

    private final AdapterDeploymentContext adapterDeploymentContext;
    private final AdapterTokenStoreFactory adapterTokenStoreFactory = new SpringSecurityAdapterTokenStoreFactory();

    public FixKeycloakSecurityContextRequestFilter(AdapterDeploymentContext adapterDeploymentContext) {
        this.adapterDeploymentContext = adapterDeploymentContext;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (request.getAttribute(FILTER_APPLIED) == null) {
            KeycloakSecurityContext context = getKeycloakPrincipal();

            if (context instanceof RefreshableKeycloakSecurityContext) {
                RefreshableKeycloakSecurityContext rcontext = (RefreshableKeycloakSecurityContext) context;

                // check and fix non-serializable fields in context
                if (rcontext.getDeployment() == null) {
                    KeycloakDeployment deployment = resolveDeployment(request, response);
                    AdapterTokenStore adapterTokenStore = adapterTokenStoreFactory.createAdapterTokenStore(deployment, (HttpServletRequest) request);
                    rcontext.setCurrentRequestInfo(deployment, adapterTokenStore);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    // copied from KeycloakSecurityContextRequestFilter ////////////////////////////////////////////////////////////////

    private KeycloakSecurityContext getKeycloakPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof KeycloakPrincipal) {
                return KeycloakPrincipal.class.cast(principal).getKeycloakSecurityContext();
            }
        }
        return null;
    }

    private KeycloakDeployment resolveDeployment(ServletRequest servletRequest, ServletResponse servletResponse) {
        return adapterDeploymentContext.resolveDeployment(
                new SimpleHttpFacade(HttpServletRequest.class.cast(servletRequest), HttpServletResponse.class.cast(servletResponse)));
    }

}