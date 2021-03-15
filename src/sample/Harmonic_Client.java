package sample;/*
The sample.Harmonic_Client object is created to store the characteristic of a Client that is in need of an estimate.
 */

public class Harmonic_Client {
    int client_id;
    String job_name;
    String customer;
    String representative;
    String project_manager;
    String estimator;
    String job_notes;
    String private_notes;
    String conflicts;

    public Harmonic_Client(int client_id,
                           String job_name,
                           String customer,
                           String representative,
                           String project_manager,
                           String estimator,
                           String job_notes,
                           String private_notes,
                           String conflicts) {

        this.client_id = client_id;
        this.job_name = job_name;
        this.customer = customer;
        this.representative = representative;
        this.project_manager = project_manager;
        this.estimator = estimator;
        this.job_notes = job_notes;
        this.private_notes = private_notes;
        this.conflicts = conflicts;
    }



    public int getClient_id() {
        return client_id;
    }

    public String getJob_name() {
        return job_name;
    }

    public String getCustomer() {
        return customer;
    }

    public String getRepresentative() {
        return representative;
    }

    public String getProject_manager() {
        return project_manager;
    }

    public String getEstimator() {
        return estimator;
    }

    public String getJob_notes() {
        return job_notes;
    }

    public String getPrivate_notes() {
        return private_notes;
    }

    public String getConflicts() {
        return conflicts;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public void setProject_manager(String project_manager) {
        this.project_manager = project_manager;
    }

    public void setEstimator(String estimator) {
        this.estimator = estimator;
    }

    public void setJob_notes(String job_notes) {
        this.job_notes = job_notes;
    }

    public void setPrivate_notes(String private_notes) {
        this.private_notes = private_notes;
    }

    public void setConflicts(String conflicts) {
        this.conflicts = conflicts;
    }

    @Override
    public String toString() {
        return "Harmonic_Client{" +
                "client_id=" + client_id +
                ", job_name='" + job_name + '\'' +
                ", customer='" + customer + '\'' +
                ", representative='" + representative + '\'' +
                ", project_manager='" + project_manager + '\'' +
                ", estimator='" + estimator + '\'' +
                ", job_notes='" + job_notes + '\'' +
                ", private_notes='" + private_notes + '\'' +
                ", conflicts='" + conflicts + '\'' +
                '}';
    }
}
