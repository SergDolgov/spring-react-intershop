import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Button, Input, Image, Item, Container } from 'semantic-ui-react'

const CartItem = ({productCount, product, handleDeleteFromCart, onChangeProductItemCount}) => {
  const [value, setValue] = useState(productCount);

  const handleIncrement = (e) => {
      setValue(value + 1);
      {onChangeProductItemCount(product.id, value + 1)}
  };
  const handleDecrement = (e) => {
    if (value > 0) {
      setValue(value - 1);
      onChangeProductItemCount(product.id, value - 1)
    }
  };

  return (
    <Item key={product.id}>
      <Image as={Link} to={`/product/${product.id}`}
      src={product.poster} size='small' bordered rounded />
      <Item.Content >
        <Item.Header as={Link} to={`/product/${product.id}`}>{product.title}</Item.Header>
        <Item.Meta>{product.brand}</Item.Meta>
        <Item.Meta>{product.id}</Item.Meta>
        <Item.Description as={Link} to={`/product/${product.id}`}>
        </Item.Description>
        <Item.Meta> {product.price}$  </Item.Meta>
          <Input labelPosition='right' type='text' placeholder='Amount'>
            <input value={value} readOnly style={{ width: '50px' }}/>
            <Button icon='plus' onClick={handleIncrement} />
            <Button icon='minus'onClick={handleDecrement} />
            <Button circular size='small' icon='cart' inverted color='green' content='Delete'
              onClick={() => handleDeleteFromCart(product.id)}
            />
          </Input>



      </Item.Content>
    </Item>
  );
};

export default CartItem;
