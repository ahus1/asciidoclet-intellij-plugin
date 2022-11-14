package org.asciidoc.intellij.asciidoclet.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
  name = "AsciidocletApplicationSettings",
  storages = @Storage("asciidoclet.xml")
)
public class AsciidocletApplicationSettings implements PersistentStateComponent<org.asciidoc.intellij.asciidoclet.settings.AsciidocletApplicationSettings.State>,
  AsciidocletPreviewSettings.Holder {

  private final State myState = new State();

  @NotNull
  public static org.asciidoc.intellij.asciidoclet.settings.AsciidocletApplicationSettings getInstance() {
    return ApplicationManager.getApplication().getService(org.asciidoc.intellij.asciidoclet.settings.AsciidocletApplicationSettings.class);
  }

  @Nullable
  @Override
  public State getState() {
    return myState;
  }

  @Override
  public void loadState(@NotNull State state) {
    XmlSerializerUtil.copyBean(state, myState);
  }

  @Override
  public void setAsciidocletPreviewSettings(@NotNull AsciidocletPreviewSettings settings) {
    myState.myPreviewSettings = settings;
  }

  @NotNull
  @Override
  public AsciidocletPreviewSettings getAsciidocletPreviewSettings() {
    return myState.myPreviewSettings;
  }

  public static class State {
    @Property(surroundWithTag = false)
    @NotNull
    private AsciidocletPreviewSettings myPreviewSettings = AsciidocletPreviewSettings.DEFAULT;
  }

}
