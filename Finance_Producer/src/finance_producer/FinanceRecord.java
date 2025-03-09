package finance_producer;

import java.time.LocalDate;

public class FinanceRecord {
    private int id;
    private String type;  // Income, Expense, etc.
    private double amount;
    private String name;
    private LocalDate date;

    public FinanceRecord(int id, String type, double amount, String name, LocalDate date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
    public String toString() {
        return id + " \t\t\t " + type + " \t" + amount + " \t\t " + name + " \t " + date;
    }
}

enum FinanceType {
    INCOME, EXPENSE, INVESTMENT, SAVINGS
}
