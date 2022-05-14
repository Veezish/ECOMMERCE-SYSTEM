//Veezish Ahmad
//501080184

public class ErrorHandling
{
class CustomerNF extends RuntimeException
{
    public CustomerNF() //Cust not found error handling
    {
        super("Customer was not found!");
    }
    public CustomerNF(String error)
    {
    super(error);
    }
}
class CustomerNameNF extends RuntimeException
{
    public CustomerNameNF()
    {
        super ("Customer name was not found!");
    }
}
class CustomerAddressNF extends RuntimeException // address not found error handling
{
    public CustomerAddressNF()
    {
        super ("Customer address was not found!");
    }
}
class ProductNF extends RuntimeException
{
    public ProductNF()
    {
        super("Product was not found!");
    } // product not found error handling
    public ProductNF(String error)
    {
    super(error);
    }
}
class ProductOptionsNF extends RuntimeException
{
    public ProductOptionsNF()
    {
        super("Invalid product options entered!");
    } // product options not found error handling
    public ProductOptionsNF(String error)
    {
    super(error);
    }
}
class ProductOutOfStock extends RuntimeException
{
    public ProductOutOfStock()
    {
        super ("Product is currently out of stock!");
    } // product out of stock error handling
}
class OrderNF extends RuntimeException
{
    public OrderNF()
    {
        super ("Order number was not found!");
    } // Order not found error handling
}

}
class cartItemNotFoundMessage extends RuntimeException
{
    public cartItemNotFoundMessage()
    {
        super ("Cart Item Not Found!");
    }
}