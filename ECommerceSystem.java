//Veezish Ahmad
//501080184
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem extends ErrorHandling {
    private LinkedHashMap<String, Product> products = new LinkedHashMap<>();
    private ArrayList<Product>productsArrayList = new ArrayList<>(products.values());
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<ProductOrder> orders = new ArrayList<>();
    private ArrayList<ProductOrder> shippedOrders = new ArrayList<>();
//    private ArrayList<Book> books = new ArrayList<>();

    private Map<Product, Integer> statsMap = new LinkedHashMap<>();

    // These variables are used to generate order numbers, customer id's, product id's 
    private int orderNumber = 500;
    private int customerId = 900;
    private int productId = 700;

    // General variable used to store an error message when something is invalid (e.g. customer id does not exist)  
    String errMsg = null;
    String error;

    // Random number generator
    Random random = new Random();

    public ECommerceSystem() {
        // NOTE: do not modify or add to these objects!! - the TAs will use for testing
        // If you do the class Shoes bonus, you may add shoe products
        // Create some products. Notice how generateProductId() method is used
        String productid = generateProductId();
        products.put(productid, new Shoes("Nike", productid, 90.0, 38, 53, 63, 29, 56, 34, 35, 65, 56, 5, 0));
        productid = generateProductId();
        products.put(productid, new Shoes("Reebok", productid, 64.0, 43, 76, 6356, 47, 59, 56, 55, 98, 54, 6, 0));
        productid = generateProductId();
        products.put(productid, new Shoes("Heels", productid, 38.0, 54, 678, 7, 39, 543, 322, 43, 24, 76, 7, 0));
        productid = generateProductId();
        products.put(productid, new Shoes("Gucci Slides", productid, 89.0, 95, 56, 76, 56, 6, 4, 65, 48, 34, 82, 0));

        try {
            productsFileReader();
        } catch (IOException e) {
            e.printStackTrace(); System.exit(1);
        }


//    	 Create some customers. Notice how generateCustomerId() method is used
        customers.add(new Customer(generateCustomerId(), "Inigo Montoya", "1 SwordMaker Lane, Florin"));
        customers.add(new Customer(generateCustomerId(), "Prince Humperdinck", "The Castle, Florin"));
        customers.add(new Customer(generateCustomerId(), "Andy Dufresne", "Shawshank Prison, Maine"));
        customers.add(new Customer(generateCustomerId(), "Ferris Bueller", "4160 Country Club Drive, Long Beach"));


    }

    private HashMap<String, Product> productsFileReader() throws FileNotFoundException
    {
        FileReader productsReader = new FileReader("products.txt");
        Scanner in = new Scanner(productsReader);
        while (in.hasNextLine())
        {
            String line = in.nextLine();
            if (line.equals("BOOKS"))
            {
                String name = in.nextLine();
                double price = Double.parseDouble(in.nextLine());
                String stock = in.nextLine();
                Scanner old = new Scanner(stock);
                old.useDelimiter(" ");
                int hardcoverStock = Integer.parseInt(old.next());
                int paperbackStock = Integer.parseInt(old.next());
                String BookDetails = in.nextLine();
                Scanner oldNew = new Scanner(BookDetails);
                oldNew.useDelimiter(":");
                String title = oldNew.next();
                String authorName = oldNew.next();
                int year = Integer.parseInt(oldNew.next());
                String productId = generateProductId();
                products.put(productId, new Book(name, productId, price, paperbackStock, hardcoverStock, title, authorName, year, 0));
            }
            else if (line.equals("CLOTHING"))
            {
                String name = in.nextLine();
                Double price = Double.parseDouble(in.nextLine());
                int stock = Integer.parseInt(in.nextLine());
                String productId = generateProductId();
                products.put(productId, new Product(name, productId, price, stock, Product.Category.CLOTHING, 0));
            }
            else if (line.equals("COMPUTERS"))
            {
                String name = in.nextLine();
                Double price = Double.parseDouble(in.nextLine());
                int stock = Integer.parseInt(in.nextLine());
                String productId = generateProductId();
                products.put(productId, new Product(name, productId, price, stock, Product.Category.COMPUTERS, 0));
            }
            else if (line.equals("FURNITURE"))
            {
                String name = in.nextLine();
                Double price = Double.parseDouble(in.nextLine());
                int stock = Integer.parseInt(in.nextLine());
                String productId = generateProductId();
                products.put(productId, new Product(name, productId, price, stock, Product.Category.FURNITURE, 0));
            }
            else if (line.equals("GENERAL"))
            {
                String name = in.nextLine();
                Double price = Double.parseDouble(in.nextLine());
                int stock = Integer.parseInt(in.nextLine());
                String productId = generateProductId();
                products.put(productId, new Product(name, productId, price, stock, Product.Category.GENERAL, 0));
            }
        }
        return products;
    }

    private String generateOrderNumber() {
        return "" + orderNumber++;
    }

    private String generateCustomerId() {
        return "" + customerId++;
    }

    private String generateProductId() {
        return "" + productId++;
    }

    public String getErrorMessage() {
        return errMsg;
    }

    public String getErr(){
        return error;
    }

    public void printAllProducts()
    {
        for (Map.Entry<String, Product> entry : products.entrySet())
        {
            entry.getValue().print();
        }
    }

    // Print all products that are books. See getCategory() method in class Product
    public void printAllBooks()
    {
        for (Map.Entry<String, Product> entry : products.entrySet())
        {
            if (entry.getValue().getCategory().equals(Product.Category.BOOKS))
            {
                entry.getValue().print();
            }
        }
    }

    // Print all current orders
    public void printAllOrders()
    {
        for (ProductOrder o : orders)
            o.print();
    }

    // Print all shipped orders
    public void printAllShippedOrders()
    {
        for (ProductOrder s : shippedOrders)
            s.print();
    }

    // Print all customers
    public void printCustomers()
    {
        for (Customer c : customers)
        {
            c.print();
        }
    }

    /*
     * Given a customer id, print all the current orders and shipped orders for them (if any)
     */
    public boolean printOrderHistory(String customerId)
    {
        // Make sure customer exists - check using customerId
        // If customer does not exist, set errMsg String and return false
        // see video for an appropriate error message string
        // ... code here
        Customer cust = null;
        for (Customer c : customers)
        {
            if (customerId.equals(c.getId()))
            {  // compares the ID taken from scanner with all the IDs taken from the customer objects
                cust = c;
            }
            else if (cust == null)
            {
                throw new CustomerNF();
            }
        }
        if (cust == null)
        {
            return false;
        }
        // Print current orders of this customer
        System.out.println("Current Orders of Customer " + customerId);
        // enter code here
        for (ProductOrder o : orders)
        {
            if (o.getCustomer().equals(cust))
                o.print();
        }

        // Print shipped orders of this customer
        System.out.println("\nShipped Orders of Customer " + customerId);
        //enter code here
        for (ProductOrder s : shippedOrders)
        {
            if (s.getCustomer().equals(cust))
                s.print();
        }
        return true;
    }

    public void printAuthorBooks(String authorName)
    {
        ArrayList<Product> product = new ArrayList<Product>();
        for (Map.Entry<String, Product> entry : products.entrySet())
        {
            product.add(entry.getValue());
        }
        ArrayList<Book> Bks = new ArrayList<Book>();
        Book bks = null;
        for (Product b : product)
        {
            if (b.getCategory().equals(Product.Category.BOOKS))
            { // checking to find all the products of category BOOKS
                bks = (Book) b;
                if (bks.getAuthor().equals(authorName))
                    Bks.add(bks);
            }
        }
        Comparator<Book> ByAuthorNAME = new Comparator<Book>()
        { // This is the code for sorting the year of the books in ascending order
            @Override
            public int compare(Book o1, Book o2)
            {
                if (o1.getYear() > o2.getYear())
                    return 1;
                else
                    return -1;
            }
        };
        Bks.sort(ByAuthorNAME);
        for (Book b : Bks)
            b.print();
    }


    public void AddtoCart(String productId, String customerId, String productOptions)
    {
        Customer prevCustomer = null;
        for (Customer customer: customers)
        {
            if (customer.getId().equals(customerId))
            {
                prevCustomer = customer;
            }
        }
        if (prevCustomer == null)
        {
            throw new CustomerNF();
        }
        Product prevProduct = null;
        for (Map.Entry<String, Product> entry : products.entrySet()) { // iterating through the products list
            if (entry.getValue().getId().equals(productId)) {
                prevProduct = entry.getValue();
            }
        }
        if (prevProduct == null)
        {
            throw new ProductOptionsNF();
        }
        CartItem item = new CartItem(prevProduct, productOptions);
        prevCustomer.getCart().cartItemAdd(item);
    }

    public void RemCartItem(String customerId, String productId, String productOptions)
    {
        Customer prevCustomer = null;
        for (Customer customer : customers)
        {
            if (customer.getId().equals(customerId))
            {
                prevCustomer = customer;
            }
        }
        if (prevCustomer == null)
        {
            throw new CustomerNF();
        }
        Product prevProduct = null;
        for (Product product : productsArrayList)
        {
            if (product.getId().equals(productId))
            {
                prevProduct = product;
            }
        }
        if (prevProduct == null)
        {
            throw new ProductNF();
        }
        CartItem item = new CartItem(prevProduct, productOptions);
        if (prevCustomer.getCart().cartCheck(item))
        {
            prevCustomer.getCart().cartItemRemove(item);
        }
        else if (!prevCustomer.getCart().cartCheck(item))
        {
            throw new ProductNF();
        }
    }

    public void printCart(String customerId)
    {
        Customer prevCustomer = null;
        for (Customer customer: customers)
        {
            if (customer.getId().equals(customerId))
            {
                prevCustomer = customer;
            }
        }
        if (prevCustomer == null)
        {
            throw new CustomerNF();
        }
        prevCustomer.getCart().cartPrint();
    }

    public void orderItems(String customerId)
    {
        {
            Customer prevCustomer = null;
            for (Customer customer : customers)
            {
                if (customer.getId().equals(customerId))
                {
                    prevCustomer = customer;
                }
            }
            if (prevCustomer == null)
            {
                throw new CustomerNF();
            }
            Cart cart = prevCustomer.getCart();
            for (CartItem c : cart.getCart())
            {
                String ID = c.getProduct().getId();
                int q = cart.getCartItem().get(ID);
                for (int x = 0; x < q; ++x)
                {
                    orderProduct(c.getProduct().getId(), customerId, c.getProductOptions());
                }
            }
        }
    }

    public String orderProduct (String productId, String customerId, String productOptions)
    {
        // First check to see if customer object with customerId exists in array list customers
        // if it does not, set errMsg and return null
        // else get the Customer object
        Customer cust = null;
        for (Customer c : customers)
        { // iterating throughout the customers list
            if (c.getId().equals(customerId))
            {
                cust = c;
            }
        }
        if (cust == null)
        {
            throw new CustomerNF();
        }
        if (cust == null)
            return null;
        // Check to see if product object with productId exists in array list of products
        // if it does not, set errMsg and return null
        // else get the Product object
        Product prod = null;
        for (Map.Entry<String, Product> entry : products.entrySet())
        { // iterating through the products list
            if (entry.getValue().getId().equals(productId))
            {
                prod = entry.getValue();
            }
        }
        if (prod == null)
        {
            throw new ProductNF();
        }
        if (prod == null)
            return null;
        // Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
        // See class Product and class Book for the method validOptions()
        // If options are not valid, set errMsg string and return null;
        if (!productOptions.equals(""))
        {
            if (!prod.validOptions(productOptions))
            {
                if (prod.getCategory().equals(Product.Category.BOOKS))
                {
                    throw new ProductOptionsNF();
                }
                else if (prod.getCategory().equals(Product.Category.CLOTHING))
                {
                    throw new ProductOptionsNF();
                }
                else
                {
                    throw new ProductNF();
                }
            }
        }
        // Check if the product has stock available (i.e. not 0)
        // See class Product and class Book for the method getStockCount()
        // If no stock available, set errMsg string and return null
        if (prod.getStockCount(productOptions) == 0)
        {
            throw new ProductOutOfStock();
        }
        // Create a ProductOrder, (make use of generateOrderNumber() method above)
        // reduce stock count of product by 1 (see class Product and class Book)
        // Add to orders list and return order number string
        ProductOrder ord = new ProductOrder(generateOrderNumber(), prod, cust, productOptions);
        orders.add(ord);
        prod.reduceStockCount(productOptions);
        for (ProductOrder o : orders)
        {
            if(orders.size() == 1)
            {
                statsMap.put(prod, prod.getStats());
                prod.increaseStatsCount(prod.getId());
            }
            else if(prod.getId().equals(o.getProduct().getId()))
            {
                prod.increaseStatsCount(prod.getId());
                break;
            }
            else{
                statsMap.put(prod, prod.getStats());
            }
        }
        return "Order #" + (orderNumber - 1);
    }

    /*
     * Create a new Customer object and add it to the list of customers
     */

    public boolean createCustomer(String name, String address)
    {
        // Check name parameter to make sure it is not null or ""
        // If it is not a valid name, set errMsg (see video) and return false
        // Repeat this check for address parameter
            if (name.equals("") || name == null)
            {
                throw new CustomerNameNF();
            } else if (address.equals("") || address == null)
            {
                throw new CustomerAddressNF();
            }
            else{
                Customer newCust = new Customer(generateCustomerId());
                customers.add(newCust);
                return true;
            }
        // Create a Customer object and add to array list

    }

    public boolean shipOrder(String orderNumber)
    {
        // Check if order number exists first. If it doesn't, set errMsg to a message (see video)
        // and return false
        ArrayList<ProductOrder> newShippedOrders = new ArrayList<ProductOrder>(); // creating a new arraylist to hold all the shipped products to be removed from the orders list
        ProductOrder shipOrd = null;
        for (ProductOrder o : orders)
        {
            if (o.getOrderNumber().equals(orderNumber))
            {
                newShippedOrders.add(o);
                shippedOrders.add(o); // adding the ordered products into the shippedOrders list
                o.print();
                shipOrd = o;
                break;
            }
            // Retrieve the order from the orders array list, remove it, then add it to the shippedOrders array list
            else
            {
//                throw new InvalidOrderNumberException("Order " + orderNumber + " not found");
                errMsg = "Order " + orderNumber + " Not Found";
                continue;
            }
        }
        orders.removeAll(newShippedOrders); // using the new arraylist to remove the shipped products from the orders list
        if (shipOrd == null)
        {
            return false;
        }
        return true;
        // return a reference to the order
    }

    /*
     * Cancel a specific order based on order number
     */
    public boolean cancelOrder(String orderNumber)
    {
        // Check if order number exists first. If it doesn't, set errMsg to a message (see video)
        // and return false
        ArrayList<ProductOrder> newCancelledOrders = new ArrayList<ProductOrder>();
        ProductOrder shipOrd = null;
        for (ProductOrder o : orders)
        {
            if (o.getOrderNumber().equals(orderNumber))
                {
                    newCancelledOrders.add(o);
                    shipOrd = o;
                }
            else
                {
                    errMsg = "Order " + orderNumber + " Not Found";
                    continue;
                }
        }

        orders.removeAll(newCancelledOrders);
        if (shipOrd == null)
            return false;
        return true;
    }

    // Sort products by increasing price
    public void printByPrice()
    {
        List<Map.Entry<String, Product>> list = new LinkedList<Map.Entry<String, Product>>(products.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Product>>()
        {
            @Override
            public int compare(Map.Entry<String, Product> o1, Map.Entry<String, Product> o2)
            {
                if (o1.getValue().getPrice() > o2.getValue().getPrice())
                    return 1;
                else
                    return -1;
            }
        });
        Map<String, Product> sortedByPrice = new LinkedHashMap<String, Product>();
        for (Map.Entry<String, Product> entry : list)
        {
            sortedByPrice.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Product> entry : sortedByPrice.entrySet())
        {
            entry.getValue().print();
        }
    }

    // Sort products alphabetically by product name
    public void printByName()
    {
        List<Map.Entry<String, Product>> list = new LinkedList<Map.Entry<String, Product>>(products.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Product>>()
        {
            @Override
            public int compare(Map.Entry<String, Product> o1, Map.Entry<String, Product> o2)
            {
                return o1.getValue().getName().compareTo(o2.getValue().getName());
            }
        });
        Map<String, Product> sortedByName = new LinkedHashMap<String, Product>();
        for (Map.Entry<String, Product> entry : list)
        {
            sortedByName.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Product> entry : sortedByName.entrySet())
        {
            entry.getValue().print();
        }
    }

    // Sort products alphabetically by product name
    public void sortCustomersByName()
    {
        Comparator<Customer> ByCustNAME = new Comparator<Customer>()
        {
            @Override
            public int compare(Customer o1, Customer o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        Collections.sort(customers, ByCustNAME);
    }

    public void printStats()
    {
        ArrayList<Product> list = new ArrayList<Product>(statsMap.keySet());
        Comparator<Product> comByPrice = new Comparator<Product>()
        {
            public int compare(Product prod1, Product prod2)
            {
                if(prod1.getStats()>prod2.getStats())
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            }
        };
        Collections.sort(list, comByPrice);
        for (Product p : list)
        {
            p.statPrint();
        }
    }
}