package sample;

public class Quote {
    int quote_id;
    String quote_type;
    String status;
    String status_reason;
    double estimated_cost;

    public Quote(int quote_id, String quote_type, String status, String status_reason, double estimated_cost) {
        this.quote_id = quote_id;
        this.quote_type = quote_type;
        this.status = status;
        this.status_reason = status_reason;
        this.estimated_cost = estimated_cost;
    }

    public int getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(int quote_id) {
        this.quote_id = quote_id;
    }

    public String getQuote_type() {
        return quote_type;
    }

    public void setQuote_type(String quote_type) {
        this.quote_type = quote_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_reason() {
        return status_reason;
    }

    public void setStatus_reason(String status_reason) {
        this.status_reason = status_reason;
    }

    public double getEstimated_cost() {
        return estimated_cost;
    }

    public void setEstimated_cost(double estimated_cost) {
        this.estimated_cost = estimated_cost;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quote_id=" + quote_id +
                ", quote_type='" + quote_type + '\'' +
                ", status='" + status + '\'' +
                ", status_reason='" + status_reason + '\'' +
                ", estimated_cost=" + estimated_cost +
                '}';
    }
}
