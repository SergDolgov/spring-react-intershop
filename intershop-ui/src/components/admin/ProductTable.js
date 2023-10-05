import React, { Fragment } from 'react'
import { Button, Form, Grid, Image, Input, Table } from 'semantic-ui-react'
import ProductForm from './ProductForm'

function ProductTable(props) {
  const { isBrandsLoading, brands, handleInputChange, useProductForm } = props
  const { isProductsLoading, products, productId, productBrand, productTitle, productPoster, productTextSearch, handleAddProduct, handleDeleteProduct, handleSearchProduct } = props

  let productList
  if (products.length === 0) {
    productList = (
      <Table.Row key='no-product'>
        <Table.Cell collapsing textAlign='center' colSpan='5'>No product</Table.Cell>
      </Table.Row>
    )
  } else {
    productList = products.map(product => {
      return (
        <Table.Row key={product.id}>
          <Table.Cell collapsing>
            <Button
              circular
              color='red'
              size='small'
              icon='trash'
              onClick={() => handleDeleteProduct(product.id)}
            />
          </Table.Cell>
          <Table.Cell>
            { product.poster ?
            <Image src={product.poster} size='tiny' bordered rounded /> :
            <Image src='/images/product-poster.jpg' size='tiny' bordered rounded />
            }
          </Table.Cell>
          <Table.Cell>{product.id}</Table.Cell>
          <Table.Cell>{product.brand}</Table.Cell>
          <Table.Cell>{product.title}</Table.Cell>
          <Table.Cell>{product.createdAt}</Table.Cell>
        </Table.Row>
      )
    })
  }

  return (
    <Fragment>
      <Grid stackable divided>
        <Grid.Row columns='2'>
          <Grid.Column width='5'>
            <Form onSubmit={handleSearchProduct}>
              <Input
                action={{ icon: 'search' }}
                name='productTextSearch'
                placeholder='Search by Id or Title'
                value={productTextSearch}
                onChange={handleInputChange}
              />
            </Form>
          </Grid.Column>
          <Grid.Column>
            <ProductForm
              brands={brands}
              productId={productId}
              productTitle={productTitle}
              productBrand={productBrand}
              productPoster={productPoster}
              handleInputChange={handleInputChange}
              handleAddProduct={handleAddProduct}
            />
          </Grid.Column>
        </Grid.Row>
      </Grid>
      <Table compact striped selectable>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell width={1}/>
            <Table.HeaderCell width={4}>Poster</Table.HeaderCell>
            <Table.HeaderCell width={3}>ID</Table.HeaderCell>
            <Table.HeaderCell width={4}>Brand</Table.HeaderCell>
            <Table.HeaderCell width={4}>Title</Table.HeaderCell>
            <Table.HeaderCell width={4}>CreatedAt</Table.HeaderCell>
          </Table.Row>
        </Table.Header>
        <Table.Body>
          {productList}
        </Table.Body>
      </Table>
    </Fragment>
  )
}

export default ProductTable