package ru.aicontest.form.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CompetitionRequestCredentials {
    @NotNull
    @NotEmpty
    @Size(max = 200)
    private String theme;

    @NotNull
    @NotEmpty
    @Size(max = 2000)
    private String description;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
