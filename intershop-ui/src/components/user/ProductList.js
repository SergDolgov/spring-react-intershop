import React from 'react'
import { Link } from 'react-router-dom';
import { Button, Grid, Header, Form, Icon, Image, Input, Item, Segment } from 'semantic-ui-react'
import ProductPage from './ProductPage'

function ProductList({ isProductsLoading, productTextSearch, products,
                    handleInputChange, handleSearchProduct, handleAddProductToCart, handleGetProduct }) {
  let productList
  if (products.length === 0) {
    productList = <Item key='no-product'>No Product</Item>
  } else {
    productList = products.map(product => {
      return (
        <Item key={product.id}>
          <Image as={Link} to={`/product/${product.id}`}
          src={product.poster} size='small' bordered rounded />
          <Item.Content >
            <Item.Header as={Link} to={`/product/${product.id}`}>{product.title}</Item.Header>
            <Item.Meta>{product.id}</Item.Meta>
            <Item.Description as={Link} to={`/product/${product.id}`}>
              <Image src='https://react.semantic-ui.com/images/wireframe/short-paragraph.png' />
            </Item.Description>
            <Item.Meta>   </Item.Meta>
            <Button
              circular
              size='small'
              icon='cart plus'
              inverted color='green'
              content='Add to Cart'
              onClick={() => handleAddProductToCart(product.id)}
            />
          </Item.Content>
        </Item>
      )
    })
  }

  return (
    <Segment loading={isProductsLoading} color='purple'>
      <Grid stackable divided>
        <Grid.Row columns='2'>
          <Grid.Column width='3'>
            <Header as='h2'>
              <Icon name='video camera' />
              <Header.Content>Products</Header.Content>
            </Header>
          </Grid.Column>
          <Grid.Column>
            <Form onSubmit={handleSearchProduct}>
              <Input
                action={{ icon: 'search' }}
                name='productTextSearch'
                placeholder='Search by ID or Title'
                value={productTextSearch}
                onChange={handleInputChange}
              />
            </Form>
          </Grid.Column>
        </Grid.Row>
      </Grid>
      <Item.Group >
        {productList}
      </Item.Group>
    </Segment>
  )
}

export default ProductList