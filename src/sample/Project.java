package sample;

public class Project {
    int project_id;
    int client_id;
    double room_sqft;
    double room_height;
    double room_volume;
    String due_date;
    String revision_date;
    int revision_number;


    public Project(int project_id,
                   int client_id,
                   double room_sqft,
                   double room_height,
                   double room_volume,
                   String due_date,
                   String revision_date,
                   int revision_number) {
        this.project_id = project_id;
        this.client_id = client_id;
        this.room_sqft = room_sqft;
        this.room_height = room_height;
        this.room_volume = room_volume;
        this.due_date = due_date;
        this.revision_date = revision_date;
        this.revision_number = revision_number;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }
    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public double getRoom_sqft() {
        return room_sqft;
    }

    public void setRoom_sqft(double room_sqft) {
        this.room_sqft = room_sqft;
    }

    public double getRoom_height() {
        return room_height;
    }

    public void setRoom_height(double room_height) {
        this.room_height = room_height;
    }

    public double getRoom_volume() {
        return room_volume;
    }

    public void setRoom_volume(double room_volume) {
        this.room_volume = room_volume;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getRevision_date() {
        return revision_date;
    }

    public void setRevision_date(String revision_date) {
        this.revision_date = revision_date;
    }

    public int getRevision_number() {
        return revision_number;
    }

    public void setRevision_number(int revision_number) {
        this.revision_number = revision_number;
    }

    @Override
    public String toString() {
        return "Project{" +
                "project_id=" + project_id +
                ", room_sqft=" + room_sqft +
                ", room_height=" + room_height +
                ", room_volume=" + room_volume +
                ", due_date='" + due_date + '\'' +
                ", revision_date='" + revision_date + '\'' +
                ", revision_number=" + revision_number +
                '}';
    }
}
