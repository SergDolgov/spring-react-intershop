import React, { useEffect, useState } from 'react'
import { Form, Icon, Button, Dropdown} from 'semantic-ui-react'

function ProductForm(props){
  const { productId, productBrand, productTitle, productPoster, handleInputChange, handleAddProduct } = props
  const { isBrandsLoading, brands, useProductForm } = props
  const createBtnDisabled = productBrand === 0 || productTitle.trim() === ''

  return (
    <Form onSubmit={handleAddProduct}>
      <Form.Group>
        <Form.Field>
          <Dropdown
             name='productBrand'
             placeholder='Brand*'
             fluid
             search
             selection
             options={brands.map((brand) => ({
               key: brand.id,
               text: brand.name,
               value: brand.id,
             }))}
             onChange={handleInputChange}
             value={productBrand}
          />
        </Form.Field>
        <Form.Input
          name='productTitle'
          placeholder='Title *'
          value={productTitle}
          onChange={handleInputChange}
        />
        <Form.Input
          name='productPoster'
          placeholder='Poster'
          value={productPoster}
          onChange={handleInputChange}
        />
        <Button icon labelPosition='right' disabled={createBtnDisabled}>
          Create<Icon name='add' />
        </Button>
      </Form.Group>
    </Form>
  )
}

export default ProductForm
