package io.phasetwo.keycloak.themes.resource;

import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.spi.HttpRequest;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

/** */
@JBossLog
public abstract class BaseRealmResourceProvider implements RealmResourceProvider {

  protected final KeycloakSession session;

  public BaseRealmResourceProvider(KeycloakSession session) {
    this.session = session;
  }

  @Override
  public void close() {}

  protected abstract Object getRealmResource();

  @Override
  public Object getResource() {
    HttpRequest request = session.getContext().getContextObject(HttpRequest.class);
    log.debugf("request method %s", request.getHttpMethod());
    if (request != null && "OPTIONS".equals(request.getHttpMethod())) {
      return new CorsResource(session, request);
    } else {
      return getRealmResource();
    }
  }
}
