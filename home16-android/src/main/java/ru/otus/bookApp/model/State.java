package ru.otus.bookApp.model;

/**
 * Состояние работы с сервером
 */
public class State {
    private String error = "";
    private boolean inProgress;

    public String getError() {
        return error;
    }

    void setError(String error) {
        this.error = error;
    }

    public boolean hasError() {
        return !error.isEmpty();
    }

    public String getAndResetError() {
        String t = error;
        error = "";
        return t;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }
}
