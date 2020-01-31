package hello;

import java.util.ArrayList;


public class FakeService {

    private static final int MAX_ITEMS = 10000;

    private ArrayList<Customer> customerList;

    public FakeService(){
        this.customerList = new ArrayList<>();
    }

	public void start() {

        for(int i = 0; i < MAX_ITEMS; i++){
            Customer c = new Customer();
            c.setId(i);
            c.setName(String.format("Customer %d", i));
            c.setLastNames(String.format("Customer %d LastNames", i));
            customerList.add(c);
        }
        
        this.continue1();
	}

    private void continue1() {

        for(int i = 0; i < MAX_ITEMS * 2; i++){
            Customer c = new Customer();
            c.setId(i);
            c.setName(String.format("Customer %d", i));
            c.setLastNames(String.format("Customer %d LastNames", i));
            customerList.add(c);
        }
        this.continue2();
    }

    private void continue2() {
        for(int i = 0; i < MAX_ITEMS * 3; i++){
            Customer c = new Customer();
            c.setId(i);
            c.setName(String.format("Customer %d", i));
            c.setLastNames(String.format("Customer %d LastNames", i));
            customerList.add(c);
        }
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }
}