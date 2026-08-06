package s01_exception.t01_tryCatch;

class NegativeAmountExceptions extends RuntimeException {
    public NegativeAmountExceptions() {
        super();
    }
    public NegativeAmountExceptions(String message) {
        super(message);
    }
}

class BAnk {
    private double balance = 1000.0;

    public void withdraw (double amount) {
        if (amount < 0) {
            try {
                throw new NegativeAmountException("amount не может быть отрицательным!");
            } catch (NegativeAmountException e) {
                throw new RuntimeException(e);
            }
        }

        balance -= amount;
        System.out.println("Успешное снятие: "+balance);
    }

    public static void main(String[] args) {

        BAnk bank = new BAnk();

        bank.withdraw(-100);
    }
}