package storeManagementSystem;

import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	
	private LinkedHashMap<String, Product> inventory = new LinkedHashMap<>();
	
	public void showInventory() {
		System.out.println("-----------------------------------------------------------------------");
	    System.out.printf("%-2s %-38s %-12s %-10s%n", "Code", "Product Name", "Stock", "Price");
	    System.out.println("-----------------------------------------------------------------------");

	        // Loop through HashMap and print each product
	    for (Map.Entry<String, Product> products : inventory.entrySet()) {
	    	Product p = products.getValue();
	    	System.out.printf("%-4s %-40s %-10d ₱%-10.2f%n", products.getKey(), p.name, p.stock, p.price);
	    }

	    System.out.println("-----------------------------------------------------------------------");
	}
	
	public void restock(String code, int restock) {
		Product product = inventory.get(code);  
        if (product != null) {
        	int initial = product.stock;
            product.stock += restock;  
            System.out.println("Initial Stock: " + initial);
            System.out.println("Restocked " + restock + " units of " + product.name);
            System.out.println("Total Stock: "+ product.stock);
        } 
	}
	
	public void addProduct(String code, String name, double price, int stock) {
	    if (inventory.containsKey(code)) {
	        System.out.println("Product with code '" + code + "' already exists. Cannot add duplicate.");
	        return;
	    }
	    Product newProduct = new Product(name, price, stock);
	    inventory.put(code, newProduct);
	    System.out.println("Added new product: " + name + " (Code: " + code + ")");
	}
	
	public void updatePrice(String code, double newPrice) {
	    Product product = inventory.get(code);
	    if (product != null) {
	    	double initial = product.price;
	        product.price = newPrice;
	        System.out.printf("Initial Price: %.2f%n", initial);
	        System.out.printf("Updated price for %s to ₱%.2f%n", product.name, newPrice);
	    }
	}
	
	public void removeProduct(String code) {
	    Product product = inventory.remove(code);
	    if (product != null) {
	        System.out.println("Removed product: " + product.name + " (Code: " + code + ")");
	    } else {
	        System.out.println("Product with code '" + code + "' not found.");
	    }
	}
	
	public boolean searchProduct(String code) {

	    Product product = inventory.get(code);
	    if (product != null) {
	        System.out.println("---------------------------------------------------------------------");
	        System.out.printf("%-4s %-38s %-12s %-10s%n", "Code", "Product Name", "Stock", "Price");
	        System.out.println("---------------------------------------------------------------------");
	        System.out.printf("%-4s %-40s %-10d ₱%-10.2f%n", code, product.name, product.stock, product.price);
	        System.out.println("---------------------------------------------------------------------");
	        return true;
	    } else {
	        System.out.println("Product with code '" + code + "' not found.");
	        return false;
	    }
	}
	
	public boolean backToMenu() {
		Scanner s = new Scanner(System.in);
		System.out.print("(yes/no) Back to Main Menu? ");
		String decision = "";
		while (true) {  
            decision = s.nextLine().trim();  
            
            if (decision.equalsIgnoreCase("yes")) {
                return true;  
            } else if (decision.equalsIgnoreCase("no")) {
                return false;  
            } else {
                System.out.print("Input invalid. Please type yes or no: ");  
            }   
		}	
	}
	
	public boolean doesThisProductExists(String code) {
		Product product = inventory.get(code);
		
		if(product!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) {
	    Main m = new Main();
	    Scanner scanner = new Scanner(System.in);
	    
	    
	    m.inventory.put("G1", new Product("Asus ROG Phone 9 Pro", 50995.00, 5));
	    m.inventory.put("G2", new Product("Asus ROG Phone 9", 39995.00, 7));
	    m.inventory.put("G3", new Product("ZTE Nubia Neo 3", 11499.00, 12));
	    m.inventory.put("T1", new Product("Samsung Galaxy Tab S10 FE", 26990.00, 10));
	    m.inventory.put("T2", new Product("Samsung Galaxy Tab A9", 9390.00, 20));
	    m.inventory.put("T3", new Product("Lenovo Tab", 7752.26, 15));
	    m.inventory.put("A1", new Product("SL16 Clip-On Phone Cooler", 367.00, 25));
	    m.inventory.put("A2", new Product("Lenovo Digital Pen 2", 1478.34, 10));
	    m.inventory.put("A3", new Product("Xiaomi Pad Keyboard + Smart Pen 2 Case", 5099.00, 8));
	    m.inventory.put("A4", new Product("Universal Cooling Plate", 108.00, 30));
	    
	    
	    System.out.println("Welcome manager to your store (Paul's Store)");
	    System.out.println("Initial inventory loaded. Use the menu below to manage the store.\n");
	    
	    
	    int choice;
	    do {
	        System.out.println("=== Store Management Menu ===");
	        System.out.println("1. Show All Inventory");
	        System.out.println("2. Add New Product");
	        System.out.println("3. Restock Product");
	        System.out.println("4. Update Product Price");
	        System.out.println("5. Remove Product");
	        System.out.println("6. Search Product by Code");
	        System.out.println("7. Exit");
	        System.out.print("Enter your choice (1-7): ");
	        
	        
	        choice = -1;  
	        boolean validChoice = false;
	        while (!validChoice) {
	            try {
	                choice = scanner.nextInt();
	                scanner.nextLine();  
	                if (choice >= 1 && choice <= 7) {
	                    validChoice = true;
	                } else {
	                    System.out.print("Invalid choice. Please enter a number between 1-7: ");
	                }
	            } catch (InputMismatchException e) {
	                System.out.print("Invalid input! Please enter a number: ");
	                scanner.nextLine();  
	            }
	        }
	        
	        switch (choice) {
	            case 1:
	                m.showInventory();
	                break;
	                
	            case 2: 
	            	if(m.backToMenu()) {
	            		break;
	            	}
	            	
	            	String addCode = "";
	            	while (true) {
	                    System.out.print("Enter product code (e.g., G4): ");
	                    addCode = scanner.nextLine();
	                    
	                    if (m.doesThisProductExists(addCode)) {
	                        System.out.println("Product with code '" + addCode + "' already exists. Please try again.");
	                    } else {
	                        break; 
	                    }
	                }
	                
	                System.out.print("Enter product name: ");
	                String addName = scanner.nextLine();
	                
	                
	                double addPrice = 0;
	                boolean validPrice = false;
	                while (!validPrice) {
	                    try {
	                        System.out.print("Enter product price (₱): ");
	                        addPrice = scanner.nextDouble();
	                        scanner.nextLine();  
	                        if (addPrice >= 0) {
	                            validPrice = true;
	                        } else {
	                            System.out.print("Price cannot be negative. Please enter a valid price: ");
	                        }
	                    } catch (InputMismatchException e) {
	                        System.out.print("Invalid price! Please enter a number (e.g., 100.50): ");
	                        scanner.nextLine();  
	                    }
	                }
	                
	                
	                int addStock = 0;
	                boolean validStock = false;
	                while (!validStock) {
	                    try {
	                        System.out.print("Enter initial stock: ");
	                        addStock = scanner.nextInt();
	                        scanner.nextLine();  
	                        if (addStock >= 0) {
	                            validStock = true;
	                        } else {
	                            System.out.print("Stock cannot be negative. Please enter a valid number: ");
	                        }
	                    } catch (InputMismatchException e) {
	                        System.out.print("Invalid stock! Please enter a whole number: ");
	                        scanner.nextLine();  
	                    }
	                }
	                m.addProduct(addCode, addName, addPrice, addStock);
	                break;
	                
	            case 3:  
	            	if(m.backToMenu()) {
	            		break;
	            	}
	           
	            	String restockCode = "";
	            	while(true) {
	            		System.out.print("Enter product code to restock: ");
	            		restockCode = scanner.nextLine();
	                
	            		if(m.doesThisProductExists(restockCode)) {
	            			break;
	            		}else {
	            			System.out.println("Product with code " + restockCode + " doesn't exist. Unable to Restock");
	            		}
	            	}
	                
	                int restockQty = 0;
	                boolean validRestock = false;
	                while (!validRestock) {
	                    try {
	                        System.out.print("Enter quantity to restock: ");
	                        restockQty = scanner.nextInt();
	                        scanner.nextLine();  
	                        if (restockQty > 0) {  
	                            validRestock = true;
	                        } else {
	                            System.out.print("Quantity must be positive. Please enter a valid number: ");
	                        }
	                    } catch (InputMismatchException e) {
	                        System.out.print("Invalid quantity! Please enter a whole number: ");
	                        scanner.nextLine();  
	                    }
	                }
	                m.restock(restockCode, restockQty);
	                break;
	                
	            case 4:  
	            	if(m.backToMenu()) {
	            		break;
	            	}
	            	
	            	String priceCode = "";
	            	while(true) {
	            		System.out.print("Enter product code to update price: ");
	            		priceCode = scanner.nextLine();
	            		
	            		if(m.doesThisProductExists(priceCode)) {
	            			break;
	            		}else {
	            			System.out.println("Product with code " + priceCode + " doesn't exist. Unable to Update Price.");
	            		}
	            	}
	                
	                double newPrice = 0;
	                boolean validNewPrice = false;
	                while (!validNewPrice) {
	                    try {
	                        System.out.print("Enter new price (₱): ");
	                        newPrice = scanner.nextDouble();
	                        scanner.nextLine();  
	                        if (newPrice >= 0) {
	                            validNewPrice = true;
	                        } else {
	                            System.out.print("Price cannot be negative. Please enter a valid price: ");
	                        }
	                    } catch (InputMismatchException e) {
	                        System.out.print("Invalid price! Please enter a number (e.g., 100.50): ");
	                        scanner.nextLine();  
	                    }
	                }
	                m.updatePrice(priceCode, newPrice);
	                break;
	                
	            case 5: 
	            	if(m.backToMenu()) {
	            		break;
	            	}
	            	
	            	String removeCode = "";
	            	while(true) {
	            		System.out.print("Enter product code to remove: ");
	            		removeCode = scanner.nextLine();
	            		
	            		if(m.doesThisProductExists(removeCode)) {
	            			break;
	            		}else {
	            			System.out.println("Product with code " + removeCode + " doesn't exist. Unable to Remove.");
	            		}	
	            	}
	            	m.removeProduct(removeCode);
	                break;
	                
	                
	            case 6:  
	                System.out.print("Enter product code to search: ");
	                String searchCode = scanner.nextLine();
	                m.searchProduct(searchCode);
	                break;
	                
	            case 7:
	                System.out.println("Thank you for using Paul's Store Management System. Goodbye!");
	                break;
	        }
	        System.out.println();  
	    } while (choice != 7);
	    
	    scanner.close();
	}
	
}



