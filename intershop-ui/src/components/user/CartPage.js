import React, { FC, ReactElement, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
//import { ShoppingCartOutlined, ShoppingOutlined } from "@ant-design/icons";
//import { Button, Col, Row, Typography } from "antd";
import { Link } from "react-router-dom";
import { Button, Grid, Header, Form, Icon, Input, Container, Image, Item, Segment } from 'semantic-ui-react'

/*import { selectCartItems, selectIsCartLoading } from "../../redux-toolkit/cart/cart-selector";
import { fetchCart } from "../../redux-toolkit/cart/cart-thunks";
import {
    calculateCartPrice,
    removeProductById,
    resetCartState,
    setCartItemsCount
} from "../../redux-toolkit/cart/cart-slice";
*/
import { useAuth } from '../context/AuthContext'
import CartItem from './CartItem'
import { productApi } from '../misc/ProductApi'
import { handleLogError } from '../misc/Helpers'

function CartPage() {
  const Auth = useAuth()
  const user = Auth.getUser()
  const isAdmin = user.data.rol[0] === 'ADMIN'
  const useProductForm = false

  const [products, setProducts] = useState([])
  const [isCartLoading, setCartLoading] = useState(false)
  const [productInCart, setProductInCart] = useState(() => new Map());
  const [totalPrice, setTotalPrice] = useState(0)
  const [totalCount, setTotalCount] = useState(0)

    useEffect(() => {
        const productsFromLocalStorage: Map<number, number> = new Map(
            JSON.parse(localStorage.getItem("products"))
        );

        productsFromLocalStorage.forEach((value: number, key: number) => {
            setProductInCart(productInCart.set(key, value));
        });

        handleGetProducts([...productInCart.keys()])

    }, []);

    useEffect(() => {
        calculateCartPrice()
    });

    const handleDeleteFromCart = (productId): void => {

        let filteredArray = products.filter(item => item.id !== productId)
        setProducts(filteredArray);

        productInCart.delete(productId);

        if (productInCart.size === 0) {
            localStorage.removeItem("products");
            setProductInCart(new Map());
        } else {
            localStorage.setItem("products", JSON.stringify(Array.from(productInCart.entries())));
        }

        calculateCartPrice()
       // dispatch(removeProductById(productId));
       // dispatch(setCartItemsCount(productInCart.size));
    };

    const calculateCartPrice = () => {
        let total = 0, count = 0
        productInCart.forEach((value, key) => {
          if (products.length != 0) {
              total += products.filter(item => item.id == key)[0].price * value;
              count += 1;
          }
        });
        setTotalCount(count)
        setTotalPrice(total)
    };

    const onChangeProductItemCount = ( product, value ) => {
        setProductsInCart(product, value);
        calculateCartPrice()
       // dispatch(calculateCartPrice(products));
    };

     const setProductsInCart = (productId: number, productCount: number): void => {
        setProductInCart(productInCart.set(productId, productCount));
        localStorage.setItem("products", JSON.stringify(Array.from(productInCart.entries())));
    };


  const handleGetProducts = async (productIds) => {
    try {
      setCartLoading(true)
      const response = await productApi.getProductsCart(user, productIds)
      setProducts(response.data)

    } catch (error) {
      handleLogError(error)
    } finally {
      setCartLoading(false)
    }
  }

  const handleCheckout = async () => {
    try {
      setCartLoading(true)
      //const response = await productApi.getProductsCart(user, productIds)
      //setProducts(response.data)
    } catch (error) {
      handleLogError(error)
    } finally {
      setCartLoading(false)
    }
  }


  let productList

  if (products.length === 0) {
    productList = <Item key='no-product'>No Product</Item>
  } else {

    productList = products.map(product => {
      return (
        <CartItem key={product.id}
          productCount = {productInCart.get(product.id)}
          product = {product}
          onChangeProductItemCount = {onChangeProductItemCount}
          handleDeleteFromCart = {handleDeleteFromCart}
        />
      )
    })
  }

  return (
  <Segment loading={isCartLoading} color='purple'>
    <Grid stackable divided>
        <Grid.Row columns='3'>
          <Grid.Column width='3'>
            <Header as='h2'>
              <Icon name='cart' />
              <Header.Content>Cart</Header.Content>
            </Header>
          </Grid.Column>
          <Grid.Column width='2'>
            <Header as='h2'>
              <Header.Content>Price: {totalPrice.toLocaleString(undefined, {maximumFractionDigits:0})}$  </Header.Content>
              <Header.Content>Count: {totalCount} </Header.Content>
            </Header>
          </Grid.Column>
          <Grid.Column>
              <Button
                circular
                size='small'
                //icon='doc'
                inverted color='green'
                content='Checkout'
                onClick={() => handleCheckout}
            />
          </Grid.Column>
        </Grid.Row>
      </Grid>
    <Item.Group >
      {productList}
    </Item.Group>
   </Segment>
  )
}

export default CartPage