import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import { Button, Grid, Header, Form, Icon, Image, Input, Item, Segment } from 'semantic-ui-react'
import { useAuth } from '../context/AuthContext'
import { productApi } from '../misc/ProductApi'
import { handleLogError } from '../misc/Helpers'
import { useCart } from "./useCart";

function ProductPage() {
  const Auth = useAuth()
  const user = Auth.getUser()

  const { id } = useParams();
  const [product, setProduct] = useState([])
  const [isProductLoading, setIsProductLoading] = useState(false)

  const { addToCart } = useCart()

  const handleClickAddToCart = (event: any) => {
    addToCart(event)
  }

  useEffect(() => {
     handleGetProduct()
   }, [])

  const handleGetProduct = async () => {
    setIsProductLoading(true)
    try {
     const response = await productApi.getProduct(user, id)
     const product = response.data
     setProduct(product)
    } catch (error) {
     handleLogError(error)
     setProduct([])
     setIsProductLoading(false)
    }
  }

  return (
  <Item.Group loading={isProductLoading}>
    <Item key={product.id}>
      <Image src={product.poster} size='small' bordered rounded />
      <Item.Content>
        <Item.Header>{product.title}</Item.Header>
        <Item.Meta>Art: {product.id}</Item.Meta>
        <Item.Meta>Brand: {product.brand}</Item.Meta>
        <Item.Description>
          <Image src='https://react.semantic-ui.com/images/wireframe/short-paragraph.png' />
        </Item.Description>
        <Item.Meta></Item.Meta>
        <Button
          circular
          inverted color='green'
          size='small'
          icon='cart plus'
          content='Add to Cart'
          onClick={() => handleClickAddToCart(product.id)}
        />
      </Item.Content>

    </Item>
  </Item.Group>
  )
}

export default ProductPage