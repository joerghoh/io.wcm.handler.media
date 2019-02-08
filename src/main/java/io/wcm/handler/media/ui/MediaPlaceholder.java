/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2019 wcm.io
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
package io.wcm.handler.media.ui;

import static io.wcm.handler.media.impl.MediaFormatValidateServlet.MEDIA_INVALID_REASON_I18N_PREFIX;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.wcm.handler.commons.dom.Image;
import io.wcm.handler.media.Media;
import io.wcm.handler.media.MediaInvalidReason;

/**
 * Model for media replacement placeholder.
 * <p>
 * Mandatory use parameters:
 * </p>
 * <ul>
 * <li><code>media</code> (io.wcm.handler.media.Media):
 * The result object of the media handler (usually in invalid state)</li>
 * </ul>
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class MediaPlaceholder {

  @RequestAttribute(injectionStrategy = InjectionStrategy.OPTIONAL)
  private Object media;

  private String classAppend;
  private String mediaInvalidReason;

  private static final Logger log = LoggerFactory.getLogger(MediaPlaceholder.class);

  @PostConstruct
  private void activate() {
    Media mediaMetadata = getMediaMetadata();
    if (mediaMetadata != null) {
      this.classAppend = getMediaDropCssClass(mediaMetadata);
      this.mediaInvalidReason = getMediaInvalidReasonText(mediaMetadata);
    }
  }

  private Media getMediaMetadata() {
    if (media == null) {
      log.warn("No 'media' parameter passed to MediaPlaceholder model.");
      return null;
    }
    if (media instanceof Media) {
      return (Media)media;
    }
    else if (media instanceof ResourceMedia) {
      return ((ResourceMedia)media).getMetadata();
    }
    log.warn("Invalid 'media' parameter passed to MediaPlaceholder model. "
        + "Expected: " + Media.class.getName() + ", actual: " + media.getClass().getName());
    return null;
  }

  private String getMediaDropCssClass(Media mediaMetadata) {
    Image dummyImage = new Image();
    mediaMetadata.getMediaSource().enableMediaDrop(dummyImage, mediaMetadata.getMediaRequest());
    return dummyImage.getCssClass();
  }

  private String getMediaInvalidReasonText(Media mediaMetadata) {
    if (mediaMetadata.getMediaInvalidReason() != null
        && mediaMetadata.getMediaInvalidReason() != MediaInvalidReason.MEDIA_REFERENCE_MISSING) {
      // build i18n key
      return MEDIA_INVALID_REASON_I18N_PREFIX + mediaMetadata.getMediaInvalidReason().name();
    }
    else {
      return null;
    }
  }

  /**
   * Gets additional CSS classes for the replacement placeholder to
   * allow drag&amp;drop of assets into an empty component.
   * @return CSS class or null
   */
  public @Nullable String getClassAppend() {
    return this.classAppend;
  }

  /**
   * Additional text to append to empty placeholder message.
   * @return Empty text
   */
  public @Nullable String getMediaInvalidReason() {
    return this.mediaInvalidReason;
  }

}
