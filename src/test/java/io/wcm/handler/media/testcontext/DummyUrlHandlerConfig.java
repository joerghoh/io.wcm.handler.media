/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2014 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.handler.media.testcontext;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.jetbrains.annotations.NotNull;

import io.wcm.handler.url.integrator.IntegratorMode;
import io.wcm.handler.url.integrator.IntegratorModes;
import io.wcm.handler.url.spi.UrlHandlerConfig;

/**
 * Dummy url configuration
 */
public class DummyUrlHandlerConfig extends UrlHandlerConfig {

  public static final int SITE_ROOT_LEVEL = 4;

  private static final List<IntegratorMode> INTEGRATOR_MODES = List.of(
      IntegratorModes.SIMPLE,
      IntegratorModes.EXTENDED);

  @Override
  public int getSiteRootLevel(Resource resource) {
    return SITE_ROOT_LEVEL;
  }

  @Override
  public @NotNull List<IntegratorMode> getIntegratorModes() {
    return INTEGRATOR_MODES;
  }

}
