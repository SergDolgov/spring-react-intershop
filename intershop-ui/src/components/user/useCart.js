import{ useState } from "react";
import { useNavigate } from "react-router-dom";

export interface UseCart {
    addToCart: () => void;
    countItemsCart: () => number;
    //cartItemsCount: number;
}

export const useCart = (): UseCart => {
    //const [cartItemsCount, setCartItemsCount] = useState(0)
    const navigate = useNavigate();

    const addToCart = (productId: number): void => {
        let data: string | null = localStorage.getItem("products");
        let cart: Map<number, any> = data ? new Map(JSON.parse(data)) : new Map();

        if (cart.has(productId)) {
            cart.set(productId, cart.get(productId) + 1);
        } else {
            cart.set(productId, 1);
        }
        localStorage.setItem("products", JSON.stringify(Array.from(cart.entries())));
        //setCartItemsCount(cart.size)
        //history.push(CART);
        navigate("/cartpage");
    };

    const countItemsCart = (): number => {
        let data: string | null = localStorage.getItem("products");
        let cart: Map<number, any> = data ? new Map(JSON.parse(data)) : new Map();
        //setCartItemsCount(cart.size)
        return cart.size
    };

    return { addToCart, countItemsCart}
};
