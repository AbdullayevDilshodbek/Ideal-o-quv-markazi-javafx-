package sample.teacher.davomad;

public class StatusModul {
    private String id;
    private String statuses;

    public StatusModul(String id, String statuses) {
        this.id = id;
        this.statuses = statuses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatuses() {
        return statuses;
    }

    public void setStatuses(String statuses) {
        this.statuses = statuses;
    }
}
