package pe.gob.regioncusco.sipac.mss3.domain.dto;

public class ResponseUplaod {
    private boolean success;
    private String url;

    public ResponseUplaod(boolean success, String url) {
        this.success = success;
        this.url = url;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
