package org.asciidoc.intellij.asciidoclet.settings;

import com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.annotations.NotNull;

public final class AsciidocletPreviewSettings {
  public static final AsciidocletPreviewSettings DEFAULT = new AsciidocletPreviewSettings();

  @Attribute("AsciidocletEnabled")
  private boolean myAsciidocletEnabled = true;

  public AsciidocletPreviewSettings() {
  }

  @SuppressWarnings("checkstyle:ParameterNumber")
  public AsciidocletPreviewSettings(boolean asciidocletEnabled) {
    myAsciidocletEnabled = asciidocletEnabled;
  }

  public boolean isAsciidocletEnabled() {
    return myAsciidocletEnabled;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AsciidocletPreviewSettings that = (AsciidocletPreviewSettings) o;

    if (myAsciidocletEnabled != that.myAsciidocletEnabled) {
      return false;
    }
    return true;

  }

  @Override
  public int hashCode() {
    int result = 0;
    result = 31 * result + (myAsciidocletEnabled ? 1 : 0);
    return result;
  }

  public interface Holder {
    void setAsciidocletPreviewSettings(@NotNull AsciidocletPreviewSettings settings);

    @NotNull
    AsciidocletPreviewSettings getAsciidocletPreviewSettings();
  }
}
