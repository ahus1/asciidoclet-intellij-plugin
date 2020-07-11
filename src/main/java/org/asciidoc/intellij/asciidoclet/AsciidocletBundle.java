package org.asciidoc.intellij.asciidoclet;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.util.ResourceBundle;

public class AsciidocletBundle {
  @NotNull
  private static final String BUNDLE_NAME = "AsciidocletBundle";
  @NotNull
  private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  @NotNull
  public static String message(@NotNull @PropertyKey(resourceBundle = BUNDLE_NAME) String key, Object... params) {
    return AbstractBundle.message(BUNDLE, key, params);
  }

}
