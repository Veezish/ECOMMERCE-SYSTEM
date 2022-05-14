//Veezish Ahmad
//501080184

import java.util.ArrayList;
import java.util.HashMap;

public class Cart
{
    private ArrayList <CartItem> cart;
    private HashMap <String, Integer> cartItem;
    Cart (String customerId)
    {
        this.cart = new ArrayList<CartItem>();
        this.cartItem = new HashMap<>();
    }
    public ArrayList<CartItem> getCart()
    {
        return cart;
    }
    public HashMap <String, Integer> getCartItem()
    {
        return cartItem;
    }
    private boolean cartItemCurrent (CartItem item)
    {
        boolean cartItemCurrent = false;
        for (CartItem itemNext: cart)
        {
            if (item.equals(itemNext))
            {
                cartItemCurrent = true;
            }
        }
        return cartItemCurrent;
    }
    public void cartItemAdd (CartItem item)
    {
        String ID = item.getProduct().getId();
        if (cartItemCurrent(item))
        {
            cartItem.put(ID, cartItem.get(ID) + 1);
        }
        else
        {
            cart.add(item);
            cartItem.put(ID, 1);
        }
    }
    public void cartItemRemove (CartItem item)
    {
        String ID = item.getProduct().getId();
        if (cartItem.get(ID) > 1)
        {
            cartItem.put(ID, cartItem.get(ID) - 1);
        }
        else
        {
            for (CartItem itemNext: cart)
            {
                if (item.equals(itemNext))
                {
                    item = itemNext;
                }
            }
            cart.remove(item);
            cartItem.remove(ID);
        }
    }
    public void cartPrint()
    {
        if (cart.isEmpty())
        {
            System.out.println("The cart is empty!");
        }
        else
        {
            for (CartItem item : cart)
            {
                int quantity = cartItem.get(item.getProduct().getId());
                System.out.print(quantity + " ");
                item.print();
                System.out.println();
            }
        }
    }
    public boolean cartCheck(CartItem item)
    {
        boolean cartCheck = false;
        for (CartItem itemNext : cart)
        {
            if (item.equals(itemNext))
            {
                cartCheck = true;
            }
        }
        return cartCheck;
    }

}