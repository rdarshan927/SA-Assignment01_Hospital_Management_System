package finance_producer;

public class FinanceTransaction {
    private int id;
    private String employeeName;
    private double amount;

    public FinanceTransaction(int id, String employeeName, double amount) {
        this.id = id;
        this.employeeName = employeeName;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "💵 Transaction ID: " + id + ", Employee: " + employeeName + ", Amount: $" + amount;
    }
}
